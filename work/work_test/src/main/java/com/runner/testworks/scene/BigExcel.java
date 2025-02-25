package com.runner.testworks.scene;

import java.io.*;

//大文件读写，以下demo是逐行读取，
// 还可以进行分块读取，比如一次读取1mb的数据，但是数据的整合需要考虑每次读的块不一定完整，需要确立好边界值
public class BigExcel {


    private static final String FILE_NAME = "large_file.csv";

    public static void main(String[] args) {


        read(FILE_NAME);
        write(FILE_NAME);
    }


    //    读
    public static void read(String fileName) {

        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while ((line = br.readLine()) != null) {
                // 按行处理数据，例如用 split 分割
                String[] data = line.split(",");
                // 在这里处理每一行的数据
                System.out.println("Row data: " + String.join(", ", data));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void write(String outputFile) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            // 写入表头
            writer.write("ID,Name,Age,Score");
            writer.newLine();

            // 生成 100 万条数据
            for (int i = 1; i <= 1_000_000_000; i++) {
                String name = "User" + i;
                int age = 18 + (i % 60); // 年龄范围 18-77
                double score = 50 + (i % 51); // 分数范围 50-100
                String row = String.format("%d,%s,%d,%.1f", i, name, age, score);
                writer.write(row);
                writer.newLine();

                if (i % 100_000 == 0) {
                    System.out.println("已写入 " + i + " 条数据");
                }
            }
            System.out.println("CSV 文件生成完成：" + outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
