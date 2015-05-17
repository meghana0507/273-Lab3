package edu.sjsu.cmpe.cache.client;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;
import java.nio.charset.Charset;

/**
 * Created by Meghana on 5/7/2015.
 */

public class ConsistentHashing {

 private final SortedMap<Integer, CacheServiceInterface> circle = new TreeMap<Integer, CacheServiceInterface>();

    public void add(DistributedCacheService cache) {
        int key = getHash(cache.cacheServerUrl);
        circle.put(key, cache);
    }

    public CacheServiceInterface getBucket(String key) {
        if (circle.isEmpty()) {
            return null;
        }
        int hash = getHash(key);
        if (!circle.containsKey(hash)) {
            SortedMap<Integer, CacheServiceInterface> tailMap = circle.tailMap(hash);
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        return circle.get(hash);
    }

    public int getHash(String key) {
        int n = Hashing.murmur3_128().newHasher().putString(key).hash().asInt();
        return n;
    }
}
