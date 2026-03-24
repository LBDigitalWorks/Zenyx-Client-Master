package com.client;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class SoundDump {

    static class IndexEntry {
        int size;
        int sector;
    }

    public static void main(String[] args) throws Exception {

        File cacheDir = new File(System.getProperty("user.home") + "/elvargV2/cache/LIVE");
        File dat = new File(cacheDir, "main_file_cache.dat2");
        File idx = new File(cacheDir, "main_file_cache.idx4");

        if (!dat.exists() || !idx.exists()) {
            System.out.println("Cache files not found.");
            return;
        }

        RandomAccessFile data = new RandomAccessFile(dat, "r");
        RandomAccessFile index = new RandomAccessFile(idx, "r");

        int count = (int)(index.length() / 6);

        List<Integer> validSounds = new ArrayList<>();

        for (int id = 0; id < count; id++) {

            index.seek(id * 6);

            int size =
                    (index.readUnsignedByte() << 16) |
                            (index.readUnsignedByte() << 8) |
                            (index.readUnsignedByte());

            int sector =
                    (index.readUnsignedByte() << 16) |
                            (index.readUnsignedByte() << 8) |
                            (index.readUnsignedByte());

            if (size > 0 && sector > 0) {
                validSounds.add(id);
            }
        }

        index.close();
        data.close();

        writeJson(validSounds);

        System.out.println("Dumped " + validSounds.size() + " sound IDs.");
    }

    static void writeJson(List<Integer> ids) throws IOException {

        File out = new File("sounds.json");
        BufferedWriter writer = new BufferedWriter(new FileWriter(out));

        writer.write("[\n");

        for (int i = 0; i < ids.size(); i++) {
            writer.write("  { \"id\": " + ids.get(i) + " }");
            if (i < ids.size() - 1)
                writer.write(",");
            writer.write("\n");
        }

        writer.write("]");
        writer.close();
    }
}