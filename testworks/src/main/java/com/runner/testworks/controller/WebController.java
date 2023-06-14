package com.runner.testworks.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.runner.testworks.config.RestTemplateConfig;
import com.runner.testworks.config.Result;

import com.runner.testworks.pojo.IndicatorUpLoad;
import com.runner.testworks.pojo.excel.Export01;
import com.runner.testworks.pojo.User;
import com.runner.testworks.pojo.vo.ReqVo;
import org.apache.tomcat.jni.OS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.events.Event;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author weizhenqiang
 * @date 2023/5/10 14:36
 */

@RestController
public class WebController {

    public static void main(String[] args) {
//        String msg=
//         "{"tag":"new_http_access_log","data":{"DateTime":"2023-06-12T17:30:10.001698202 08:00","DataInfos":{"1":{"rule_id":1,"have":true,"count":83,"rule_name":"èº«ä»½è¯"},"2":{"rule_id":2,"have":true,"count":93,"rule_name":"ææºå·"}},"Timestamp":"1686562210111","DataCount":4,"Host":"10.1.1.1:8080","ServerIp":"10.1.1.1","ServerPort":"8080","Method":"Post","Url":"/api/v1/waybillOrder","UrlDesp":"application/json","StatusCode":200,"human":0,"Appname":"è¿åç®¡çç³»ç»","User":"æªè¯å«ç¨æ·","SourceIP":"192.168.1.12","Header":"GET /mock_api/q5.json HTTP/1.1\r\nHost: 82.156.31.122:8080\r\nConnection: keep-alive\r\nPragma: no-cache\r\nCache-Control: no-cache\r\nUpgrade-Insecure-Requests: 1\r\nUser-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36\r\nAccept: text/html,application/xhtml xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7\r\nAccept-Encoding: gzip, defl";
//        JSON.parseObject(msg, IMap.class);

    }

    static RestTemplate httpsRestTemplate;

    static {
        try {
            httpsRestTemplate = new RestTemplate(RestTemplateConfig.generateHttpRequestFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * https调用，也可以http调用，因为配置的https调用跳过了ssl证书，里面还是使用了HttpClient
     *
     * @param reqVo
     * @return
     */
    @PostMapping("/test01")
    public Result test01(@RequestBody ReqVo reqVo) {

        /**
         * 请求头
         */
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", reqVo.getCookie());

        /**
         * 请求体+请求头
         */
        HttpEntity fromEntity = new HttpEntity<>(new JSONObject(reqVo.getMap()), httpHeaders);

        HttpMethod method = HttpMethod.resolve(reqVo.getRequstMethod());
        if (method == null) {
            method = HttpMethod.GET;
        }

        ResponseEntity<JSONObject> result;
        try {
            result = httpsRestTemplate.exchange(reqVo.getUrl(), method, fromEntity, JSONObject.class);

        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }

        // 网络状态码
        int statusCodeValue = result.getStatusCodeValue();
        // 响应头
        HttpHeaders headers = result.getHeaders();
//        响应体
        JSONObject body = result.getBody();


        return Result.success(result);

    }


    /**
     * 测试远程调用下载文件
     *
     * @param reqVo
     * @return
     */
    @PostMapping("/test02")
    public Result test02(@RequestBody ReqVo reqVo) {
        /**
         * 请求头
         */
        HttpHeaders httpHeaders = new HttpHeaders();

        /**
         * 请求体+请求头
         */
        HttpEntity fromEntity = new HttpEntity<>(new JSONObject(reqVo.getMap()), httpHeaders);

        HttpMethod method = HttpMethod.resolve(reqVo.getRequstMethod());
        if (method == null) {
            method = HttpMethod.GET;
        }

        ResponseEntity<Byte> exchange = null;
        try {
            exchange = httpsRestTemplate.exchange(reqVo.getUrl(), method, fromEntity, byte.class);

        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }


        return Result.success(null);
    }

    /**
     * 测试远程调用上传文件
     */
    @PostMapping("/test03")
    public void test03(MultipartFile file, String name) {

        String url = "http://localhost:8080/test03/test";

        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("multipart/form-data");
        headers.setContentType(type);
        System.out.println(System.getProperty("java.io.tmpdir"));

        String tempFilePath = null;
        try {
            tempFilePath = System.getProperty("java.io.tmpdir") + file.getOriginalFilename();
            File tempFile = new File(tempFilePath);
            file.transferTo(tempFile);//生成临时文件
            System.out.println("创建临时文件"+tempFilePath);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        //设置请求体

        FileSystemResource fileResource = new FileSystemResource(tempFilePath);

        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
        form.add("file", fileResource);
        form.add("name", name);

//　　　用HttpEntity封装整个请求报文
        HttpEntity<MultiValueMap<String, Object>> files = new HttpEntity<>(form, headers);
        try {
            httpsRestTemplate.exchange(url, HttpMethod.POST, files, String.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
//            删除临时文件
            delfile(tempFilePath);
        } finally {
//            删除临时文件
            delfile(tempFilePath);

        }

    }

    /**
     * 删除文件
     *
     * @param file
     */
    public static void delfile(String file) {
        Boolean flag = false;
        File tempfile = new File(file);
        // 路径为文件且不为空则进行删除
        if (tempfile.isFile() && tempfile.exists()) {
            tempfile.delete();
            flag = true;
            System.out.println("删除文件" + tempfile);
        }

    }

    @PostMapping("/test03/test")
    public void test03test(MultipartFile file, String name) {
        try {
            String tempFilePath = "C:\\test\\" + file.getOriginalFilename();
            File tempFile = new File(tempFilePath);
            file.transferTo(tempFile);//生成临时文件
            System.out.println("haha");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @PostMapping("/test06")
    public Result test06(@RequestBody Integer[] ids) {
//        httpsRestTemplate.exchange()
        for (Integer id : ids) {
            System.out.println(id);
        }
        System.out.println();

        return Result.success("haha");

    }


    /**
     * EXCEL导出功能
     *
     * @param response
     */
    @GetMapping("/excel01")
    public void excel01(HttpServletResponse response) {

        List<Export01> list = readFile("excel01.txt");

        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("合并前风险类型不为空", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), Export01.class).sheet("数据统计").doWrite(list);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @GetMapping("/excel03")
    public void excel03(HttpServletResponse response) {

        List<Export01> list = readFile("excel03.txt");

        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("合并后风险类型不为空", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), Export01.class).sheet("数据统计").doWrite(list);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


    /**
     * 逐行读取文件
     *
     * @param excel
     * @return
     */
    public static List<Export01> readFile(String excel) {
        String pathname = "/test/" + excel;

        ArrayList<Export01> list = new ArrayList<>();

        try (FileReader reader = new FileReader(pathname);
             BufferedReader br = new BufferedReader(reader)
        ) {
            String line;

            ArrayList<String> strings = new ArrayList<>();
            while ((line = br.readLine()) != null) {

                strings.add(line);

                if (strings.size() == 4) {
                    Export01 export01 = new Export01();
                    export01.setCc(strings.get(0));
                    export01.setR_risk_type(strings.get(1));
                    export01.setO_uid(strings.get(2));
                    export01.setS_dev_ip(strings.get(3).replaceAll(",", "."));
                    list.add(export01);
                    strings.clear();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        list.stream().forEach(obj -> {
            System.out.println(obj.toString());
        });

        return list;
    }


}
