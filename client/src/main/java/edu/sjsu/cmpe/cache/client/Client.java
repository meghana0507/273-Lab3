package edu.sjsu.cmpe.cache.client;

import java.util.ArrayList;

/**
 * Created by Meghana on 5/7/2015.
 */


public class Client {
	
	//----------------- CHRONICLE MAP USE FOR PERSISTENT STORE OF KEYS AND VALUES ------------------//
	static ConsistentHashing chObj = new ConsistentHashing();
    public static void main(String[] args) throws Exception {
		 
		String[] values = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j"};
		System.out.println("Starting Cache Client for ConsistentHashing...");
        
		 CacheServiceInterface bucket;

		DistributedCacheService cacheServerA = new DistributedCacheService("http://localhost:3000");
        DistributedCacheService cacheServerB = new DistributedCacheService("http://localhost:3001");
        DistributedCacheService cacheServerC = new DistributedCacheService("http://localhost:3002");
		
		chObj.add(cacheServerA);
		chObj.add(cacheServerB);
		chObj.add(cacheServerC);
      
		System.out.println("Values adding to cache servers...");
        for(int i = 1; i<11; i++){
			bucket = chObj.getBucket(Integer.toString(i));
            System.out.println(values[i - 1] + "=> Server:" + (bucket.getServer()));
            bucket.put(Integer.toUnsignedLong(i), values[i - 1]);
        }
    
        System.out.println("Cache retrieved from servers...");
        for(int i =1; i<11; i++){
          bucket = chObj.getBucket(Integer.toString(i));
          System.out.println("Server:" + (bucket.getServer()) + "=>" + values[i - 1]);
          }

        System.out.println("Exiting Cache Client...");
	}
}
