package com.evanrjohnso.quickquiz.services;


import android.util.Log;

import com.evanrjohnso.quickquiz.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReadStringFromFileLineByLine {

    public static void readFile(String fileName) {
        try {
            File fileToRead = new File(fileName);
            FileReader fileReader = new FileReader(fileToRead);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            Map<String, Object> map = new HashMap<>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                map.put(line, line);
            }
            fileReader.close();
            for (String key: map.keySet()) {
                System.out.println(key);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
