package com.client.utilities;

import com.client.graphics.interfaces.RSInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public final class FileUtils {

    private static final Logger log = LoggerFactory.getLogger(FileUtils.class);

    public static void printFreeIdRange(int minimumFreeSlotsAvailable) {
        int start = 0;
        int free = 0;

        for(int i = 0; i < RSInterface.interfaceCache.length; i++) {
            if(RSInterface.interfaceCache[i] == null) {
                if(start == 0) {
                    start = i;
                }
                free++;
            } else {
                if(start > 0) {
                    if(free >= minimumFreeSlotsAvailable) {
                        log.info("Range [{}, {}] has {} free slots", start, (i-1), free);
                    }
                    free = 0;
                    start = 0;
                }
            }
        }
    }

    public static byte[] readFile(String name) {
        try {
            RandomAccessFile raf = new RandomAccessFile(name, "r");
            ByteBuffer buf =
                    raf.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, raf.length());
            try {
                if (buf.hasArray()) {
                    return buf.array();
                } else {
                    byte[] array = new byte[buf.remaining()];
                    buf.get(array);
                    return array;
                }
            } finally {
                raf.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
