package com.client.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class FileOperations {

    private static final Logger log = LoggerFactory.getLogger(FileOperations.class);

    public static final byte[] readFile(String s) {
        try {
            File file = new File(s);
            if (!file.exists() || !file.isFile()) {
                return null;
            }

            int i = (int) file.length();
            byte[] abyte0 = new byte[i];
            try (DataInputStream datainputstream = new DataInputStream(new BufferedInputStream(new FileInputStream(file)))) {
                datainputstream.readFully(abyte0, 0, i);
                TotalRead++;
                return abyte0;
            }
        } catch (IOException exception) {
            log.error("Read Error: {}", s);
        }
        return null;
    }

    public static final void writeFile(String s, byte abyte0[]) {
        try {
            (new File((new File(s)).getParent())).mkdirs();
            try (FileOutputStream fileoutputstream = new FileOutputStream(s)) {
                fileoutputstream.write(abyte0, 0, abyte0.length);
                fileoutputstream.close();
                TotalWrite++;
                CompleteWrite++;
            }
        } catch (Throwable throwable) {
            log.error((new StringBuilder()).append("Write Error: ").append(s).toString());
        }
    }

    public static boolean FileExists(String file) {
        File f = new File(file);
        if (f.exists())
            return true;
        else
            return false;
    }

    public static int TotalRead = 0;
    public static int TotalWrite = 0;
    public static int CompleteWrite = 0;
}
