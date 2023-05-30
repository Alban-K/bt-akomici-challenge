package com.komici.challenge;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class FileUtility {

    public static String readFileAsString(String path) {

        String value = null;

        try {
            Resource resource = new ClassPathResource(path);
            InputStream inputStream = resource.getInputStream();
            byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
            value = new String(bdata, StandardCharsets.UTF_8);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }
}
