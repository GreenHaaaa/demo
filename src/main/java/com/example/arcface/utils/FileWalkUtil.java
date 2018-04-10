package com.example.arcface.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileWalkUtil {
    public static List<String> getFileList(String filePath){
        List<String> files = new ArrayList<>();
        try{
            Files.list(Paths.get(filePath)).forEach(p->{
                files.add(p.getFileName().toString());
            });
        }catch (IOException e)
        {
            return null;
        }
        for (String filename:
             files) {
           System.out.println(filename);
        }
       return files;
    }
}
