package com.runner.testworks.controller.quzhou;

import com.alibaba.excel.EasyExcel;
import com.runner.testworks.controller.quzhou.vo.RuleExport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author weizhenqiang
 * @date 2023/11/26 21:05
 */
@Slf4j
@RestController
@RequestMapping("test")
public class TestController {

    private static final String excelName = "test";

    private static final String password = "hzmc123";

    @GetMapping("/export3")
    public void exportZip2(HttpServletResponse response) {
        try {
            response.setContentType("application/zip");
            String fileName = URLEncoder.encode(excelName + ".zip", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);


            try (ServletOutputStream write = response.getOutputStream()) {
                write.write(getBytes());
            }
        }catch (Exception e){
            log.error(""+e);
        }

    }



    private byte[] getBytes(){
        List<RuleExport> ruleExportList = new ArrayList<>();
        ruleExportList.add(new RuleExport(1, "哈哈哈"));
        ruleExportList.add(new RuleExport(2, "Bob"));
        ruleExportList.add(new RuleExport(3, "Charlie"));
        try {
            byte[] bytesExcel=null;
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                EasyExcel.write(bos, RuleExport.class).sheet("自定义规则").doWrite(ruleExportList);
                bytesExcel= bos.toByteArray();
            }
            String tempDir = System.getProperty("java.io.tmpdir");
            File file = new File(tempDir, excelName+".xlsx");

            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(bytesExcel);
            } catch (IOException e) {
                e.printStackTrace();
            }
            QuZhouController.compress(file,password);

            File zip = new File(tempDir+File.separator + "zip", excelName+".zip");

            return fileToBytes(zip);
        }catch (Exception e){

        }
        return null;
    }


    private byte[] fileToBytes(File file){
        try {
            //获取输入流
            FileInputStream fis = new FileInputStream(file);

            //新的 byte 数组输出流，缓冲区容量1024byte
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
            //缓存
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            //改变为byte[]
            byte[] data = bos.toByteArray();
            //
            bos.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
