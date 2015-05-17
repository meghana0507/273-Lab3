package edu.sjsu.cmpe.cache.repository;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import net.openhft.chronicle.map.*;
import java.util.*;
import java.io.*;

import edu.sjsu.cmpe.cache.domain.Entry;

public class ChronicleMapCache implements CacheInterface {
    /** Chronicle map cache. (Key, Value) -> (Key, Entry) */
    private final Map<Long, Entry> ChronMap;
	
	public ChronicleMapCache() throws IOException{
		
		File file = new File("Persistentstore.dat");
		ChronicleMapBuilder<Long, Entry> builder = ChronicleMapBuilder.of(Long.class, Entry.class);
		ChronMap = builder.createPersistedTo(file);
	}

    @Override
    public Entry save(Entry newEntry) {
        checkNotNull(newEntry, "newEntry instance must not be null");
        ChronMap.putIfAbsent(newEntry.getKey(), newEntry);

        return newEntry;
    }

    @Override
    public Entry get(Long key) {
        checkArgument(key > 0,
                "Key was %s but expected greater than zero value", key);
        return ChronMap.get(key);
    }

    @Override
    public List<Entry> getAll() {
        return new ArrayList<Entry>(ChronMap.values());
    }

}
