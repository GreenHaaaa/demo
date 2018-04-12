package com.example.arcface.utils;

import javax.xml.transform.Source;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.*;
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
    public static void copyFile(String targetPath,String sourcePath){
        Path source = Paths.get(sourcePath);
        Path target = Paths.get(targetPath);
        try{

            Files.move(source,target, StandardCopyOption.COPY_ATTRIBUTES,StandardCopyOption.REPLACE_EXISTING);

//        }catch (DirectoryNotEmptyException e){
//
        }catch (IOException e){
            e.printStackTrace();
        }


    }
    public static  void main (String[] args){
        copyFile("C:\\logs\\5\\fibo.py","C:\\logs\\4\\fibo.py");
    }
}
