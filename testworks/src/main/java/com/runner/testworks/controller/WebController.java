package com.runner.testworks.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.runner.testworks.config.RestTemplateConfig;
import com.runner.testworks.config.Result;

import com.runner.testworks.pojo.excel.Export01;
import com.runner.testworks.pojo.User;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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


//    @Autowired
//    RestTemplate restTemplate;

    static RestTemplate httpsRestTemplate;

    static {
        try {
            httpsRestTemplate = new RestTemplate(RestTemplateConfig.generateHttpRequestFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/test01")
    public Result test01(HttpServletRequest request){
//        Cookie[] cookies = request.getCookies();
//        for (Cookie cookie:cookies){
//
//        }

        /**
         *                 result = httpsRestTemplate.exchange(url,
         *                         method, entity, String.class);
         */
        String url = "https://192.168.52.202/soc/search/alert/searchDataResult";
//        String url = "http://localhost:8080/test01";

        /**
         * 请求体
         */
        HashMap<String, Object> map = new HashMap<>();
        map.put("id","1");
        map.put("name","jkl");
        JSONObject jsonObject = new JSONObject(map);

//        RequestEntity<HashMap<String, String>> body = httpsRestTemplate.post(url)
//                .header("Content-Type", "application/json; charset=UTF-8")
//                .body(map);

        /**
         * 请求体+请求头
         */
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.add("Cookie","mccentersessionid=E3C98A8DE9B4130DEE2F767B1387E641; SESSION=f3d485e4-3566-4ad8-99ec-9e454f3b1afc");

        HttpEntity<JSON> fromEntity = new HttpEntity<>(new JSONObject(map), httpHeaders);
        System.out.println(fromEntity.toString());
        ResponseEntity<JSONObject> result = httpsRestTemplate.postForEntity(url, fromEntity, JSONObject.class);
        int statusCodeValue = result.getStatusCodeValue();

        Integer code = (Integer) result.getBody().get("code");



        return Result.success(null);
    }

    @PostMapping("/test02")
    public Result test02(@RequestBody User user){
        return Result.success(user);
    }

    @GetMapping("/test03")
    public Result test03(User user){
        return Result.success(user);
    }

    @PostMapping("/test06")
    public Result test06(@RequestBody Integer[] ids){
//        httpsRestTemplate.exchange()
        for (Integer id :ids){
            System.out.println(id);
        }
        System.out.println();

        return Result.success("haha");

    }

    @GetMapping("/excel01")
    public void excel01(HttpServletResponse response){

        List<Export01> list = readFile("excel01.txt");

        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("合并前风险类型不为空", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), Export01.class).sheet("数据统计").doWrite(list);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    @GetMapping("/excel03")
    public void excel03(HttpServletResponse response){

        List<Export01> list = readFile("excel03.txt");

        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("合并后风险类型不为空", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), Export01.class).sheet("数据统计").doWrite(list);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }


    public static List<Export01> readFile(String excel) {
        String pathname = "/test/"+excel;

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
                    export01.setS_dev_ip(strings.get(3).replaceAll(",","."));
                    list.add(export01);
                    strings.clear();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        list.stream().forEach(obj->{
            System.out.println(obj.toString());
        });

        return list;
    }



}
