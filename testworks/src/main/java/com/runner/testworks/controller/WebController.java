package com.runner.testworks.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.runner.testworks.config.RestTemplateConfig;
import com.runner.testworks.config.Result;

import com.runner.testworks.pojo.excel.Export01;
import com.runner.testworks.pojo.User;
import com.runner.testworks.pojo.vo.ReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
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

    @PostMapping("/test02")
    public Result test02(@RequestBody User user) {
        return Result.success(user);
    }

    @GetMapping("/test03")
    public void test03(String id) {
        System.out.println(id);
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
