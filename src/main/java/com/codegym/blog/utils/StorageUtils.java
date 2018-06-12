package com.codegym.blog.utils;

import java.io.File;
import java.util.UUID;

public class StorageUtils {
    public static final String IMAGE_LOCATION = "/Users/hoa/Pictures/temp/";

    public static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        return fileName.substring(dotIndex);
    }

    public static void removeImage(String fileName) {
        File file = new File(IMAGE_LOCATION + fileName);
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
