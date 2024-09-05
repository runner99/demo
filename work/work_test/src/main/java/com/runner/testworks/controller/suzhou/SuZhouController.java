package com.runner.testworks.controller.suzhou;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.runner.testworks.config.Result;
import com.runner.testworks.controller.suzhou.utils.TimeFormatEnum;
import com.runner.testworks.controller.suzhou.utils.TimeUtils;
import com.runner.testworks.pojo.User;
import com.runner.testworks.pojo.excel.Export01;
import com.runner.testworks.pojo.vo.ReqVo;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author weizhenqiang
 * @date 2023/7/12 15:19
 */
@RestController
@RequestMapping("/suzhou")
public class SuZhouController {

    @PostMapping("/test01")
    public void test01( HttpServletResponse response) {

//        response.setContentType("text/csv");
//        String fileName = null;
//        try {
//            fileName = URLEncoder.encode("弱点管理.csv", "UTF-8").replaceAll("\\+", "%20");
//        } catch (UnsupportedEncodingException e) {
//            throw new RuntimeException(e);
//        }
//        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);
//        response.setCharacterEncoding("UTF-8");
//
//
//        ArrayList<User> list = new ArrayList<>();
//        for (int i=0;i<10;i++){
//            User user = new User(i,"name噶"+i);
//            list.add(user);
//        }


        String[] tableHeaderArr = {"id","姓名","年龄"};
        List<String> cellList = new ArrayList<>();
        cellList.add("1,小明,13");
        cellList.add("2,小强,14");
        cellList.add("3,小红,15");
        cellList.add("4"+","+"gg"+","+"");
        String fileName = "导出文件.csv";
        byte[] bytes = ExportCSVUtil.writeDataAfterToBytes(tableHeaderArr, cellList);
        ExportCSVUtil.responseSetProperties(fileName,bytes, response);
















//        try {
//            PrintWriter writer = response.getWriter();
//            writer.write(0xef);
//            writer.write(0xbb);
//            writer.write(0xbf);
//            writer.println("列1,列2");
//
//            for (User item : list) {
//                writer.println(item.getId() + "," + item.getName());
//            }
//
//            writer.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }

    @PostMapping("/test02")
    public void test02(@RequestParam("idList") List<Integer> list) {
        System.out.println(list);
        List<Integer> integers = new ArrayList<>();


    }


    @PostMapping("/test03")
    public void test03(@RequestBody List<String> assertHostIps) {
        assertHostIps.stream().forEach(sub->{
            System.out.println(sub);
        });
    }


    public static void main(String[] args) {


    }
    public static String getIpsString(List<String> ips) {
        StringBuffer sb = new StringBuffer();
        for (String ip:ips){
            sb.append("'");
            sb.append(ip);
            sb.append("'");
            sb.append(",");
        }

        String string = sb.toString();
        return string.substring(0,string.length()-1);
    }


}
