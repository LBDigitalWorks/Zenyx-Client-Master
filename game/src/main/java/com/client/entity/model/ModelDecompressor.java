package com.client.entity.model;

import com.client.sign.Signlink;

import java.io.DataInputStream;
import java.io.FileInputStream;

public class ModelDecompressor {

    public static void loadModels() {
        try {
            DataInputStream dataFile = new DataInputStream(new FileInputStream(Signlink.getCacheDirectory() + ("/models/models.dat"))); //Example C:/my client/models/1.dat
            DataInputStream indexFile = new DataInputStream(new FileInputStream(Signlink.getCacheDirectory() + ("/models/models.idx"))); //Example C:/my client/models/1.idx
            int length = indexFile.readInt();
            for(int i = 0; i < length; i++) {
                int id = indexFile.readInt();
                int invlength = indexFile.readInt();
                byte[] data = new byte[invlength];
                dataFile.readFully(data);
                new ModelData(data, id);
            }
            indexFile.close();
            dataFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}