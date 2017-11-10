package com.m14n.billib.data;

import com.m14n.billib.data.model.BBTrack;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class BBDataParser {
    public static void main(String... args) {
        final File theFolder = new File(BB.DATA_ROOT, "hot-100");
        final File[] files = theFolder.listFiles();
        final File theFile = files[files.length - 1];
        JsonReader theReader = null;
        try {
            theReader = new JsonReader(new FileReader(theFile));
            final GsonBuilder theBuilder = new GsonBuilder();
            final Gson theGson = theBuilder.create();
            theReader.beginArray();
            while (theReader.hasNext()) {
                BBTrack theTrack = theGson.fromJson(theReader, BBTrack.class);
                System.out.println(theTrack.toString());
            }
            theReader.endArray();
        } catch (IOException e) {
            System.err.println("Could not parse a file" + theFile.getName());
            e.printStackTrace();
        } finally {
            if (theReader != null) {
                try {
                    theReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
