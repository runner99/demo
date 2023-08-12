package com.runner.testworks.controller.cvs;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author weizhenqiang
 * @date 2023/7/9 18:43
 */
public class CsvReader {
    public static void main(String[] args) {

        String[] HEADERS = {"__time__", "MetaLog", "__FILE__", "__LEVEL__", "__LINE__", "__THREAD__", "__tag__:__hostname__","__tag__:__path__", "__topic__", "microtime"};

        try (Reader reader = Files.newBufferedReader(Paths.get("C:\\Users\\wei\\Desktop\\work\\绍兴大数据对接阿里sls\\绍兴大数据局sls对接信息\\表格里是下载的一些日志格式\\ali-odps-x_odps_tunnel_meta_log_20230707_164756.csv"))) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader(HEADERS).parse(reader);
            for (CSVRecord record : records) {
//                 第几行
                System.out.println("Record #: " + record.getRecordNumber());
                String comment = record.getComment();
                if (record.getRecordNumber()==1) {
                    continue;
                }

                for (int j=0;j<HEADERS.length;j++){
                    System.out.println(HEADERS[j]+":"+record.get(j));
                }

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
