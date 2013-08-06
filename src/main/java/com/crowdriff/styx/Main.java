package com.crowdriff.styx;

import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class Main {

    private static List<String> keys;
    private static Iterator<String> iterator;

    public static void main(String[] args) {
        Styx styx = new Styx(new String[] { "localhost:6700" });
        StyxQueue q = styx.getQueue("testQ");
        int size = Integer.parseInt(args[0]);
        keys = Lists.newArrayList();
        System.out.println("Generating UUIDs");
        for(int i = 0; i < size; i++) {
            keys.add(UUID.randomUUID().toString());
        }
        iterator = keys.iterator();
        System.out.println("Done generating UUIDs");

        long startTime = System.currentTimeMillis();
        while(iterator.hasNext()) {
            try {
                q.put(iterator.next());
            }
            catch(Exception e) {
                System.err.println(e.getMessage());
            }
        }
        long time = System.currentTimeMillis() - startTime;
        System.out.println("Time taken: " + String.valueOf(time));
    }
}
