package com.runner.testworks.controller.wangzhuan;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author weizhenqiang
 * @date 2024/1/19 15:05
 */
@RestController
@RequestMapping("/wangzhuan")
@Slf4j
public class WangzhuanController {

    @GetMapping("/sqlTemp")
    public void export(@RequestParam(value = "database") String database, @RequestParam(value = "table") String table) {
        String s = SqlDataUtil.exportMysqldump(database, table);
        log.info("file name :" + s);
    }


    @GetMapping("/delete")
    public void delete(@RequestParam(value = "database") String database, @RequestParam(value = "table") String table) {
        boolean b = SqlDataUtil.truncateTable(database, table);
        log.info(b ? "清空成功" : "清空失败");
    }

    @GetMapping("/execute")
    public void execute(@RequestParam(value = "database") String database, @RequestParam(value = "fileName") String fileName) {
        boolean b = SqlDataUtil.executeSqlFile(database, fileName);
        log.info(b ? "执行成功" : "执行失败");
    }


    @GetMapping("/export")
    public void export(@RequestParam("fileNames") String[] fileNames, HttpServletResponse response) {
        String zipName = "中文.zip";

        List<String> list = Arrays.asList(fileNames);
        SqlDataUtil.exportZip(list, response, zipName);
    }


    @PostMapping("/import")
    public List<String> handleFileUpload(@RequestParam("file") MultipartFile file) {

        return SqlDataUtil.importZip(file);

    }


    @PostMapping("/test")
    public void test(@RequestBody List<Integer> list) {
        list.stream().forEach(obj -> {
            System.out.println(obj);
        });

        System.out.println("aaaaaaa");

    }


    public static void main(String[] args) {
        String msg = "告警时间: %s" +
                "<br>详情: %s（ip：%s）设备触发%s运行异常告警，请进行排查。" +
                "<br>告警类型: 设备告警" +
                "<br>事件描述: %s（ip：%s）设备在%s触发告警类型为 '设备告警' 的告警,请进行排查。";

        List<String> strs = new ArrayList<String>(){{
            add("222");

            add("222");
            add("222");
            add("222");

            add("222");
            add("222");
            add("222");

        }};

        String format = String.format(msg, strs.stream().toArray(String[]::new));
        System.out.println(format);
    }

}
