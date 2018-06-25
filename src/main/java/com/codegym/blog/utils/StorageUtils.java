package com.codegym.blog.utils;

import java.io.File;
import java.util.UUID;

public class StorageUtils {

    public static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        return fileName.substring(dotIndex);
    }

    public static void removeImage(String filePath) {
        File file = new File(filePath);
        if(file.exists()) {
            file.delete();
        }
    }

    public static String generateRandomFileName(String fileName) {
        String randomStr = UUID.randomUUID().toString();
        String extension = getFileExtension(fileName);
        return randomStr + extension;
    }
}
