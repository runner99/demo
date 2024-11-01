package com.runner.testworks.controller.DyMenu;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;


public class ResourcesFileUtil {
    /**
     * 读取位于resources目录下的文件，并返回文件内容的字符串。
     *
     * @param fileName 文件名
     * @return 文件内容的字符串
     * @throws IOException 如果读取文件时发生错误
     */
    public static String readFileFromResources(String fileName) {
        URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);
        if (url == null) {
            throw new IllegalArgumentException("File not found in resources: " + fileName);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file from resources: " + fileName, e);
        }

    }

    public static void main(String[] args) {
        String s = readFileFromResources("DyMenuIcon.json");
        System.out.println(s);

        String s2 = readFileFromResources("DyMenuUrl.json");
        System.out.println(s2);

    }

}
