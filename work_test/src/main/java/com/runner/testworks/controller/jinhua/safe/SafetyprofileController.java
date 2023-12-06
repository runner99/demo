package com.runner.testworks.controller.jinhua.safe;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.runner.testworks.controller.bjgw.vo.Result;
import com.runner.testworks.controller.jinhua.safe.vo.*;
import com.runner.testworks.pojo.excel.Export01;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author weizhenqiang
 * @date 2023/11/2 21:42
 */
@RestController
@RequestMapping("/jinhua/demo")
@Slf4j
public class SafetyprofileController {

    //safetyprofilequeryList
    @GetMapping("/get")
    public Result get(@RequestParam Integer page, @RequestParam Integer size,
                      @RequestParam(value = "timeSlot", required = false) String timeSlot,
                      @RequestParam(value = "title", required = false) String title,
                      @RequestParam(value = "isKeynote", required = false) Integer isKeynote) {

        ArrayList<Item> items = new ArrayList<>();
        ArrayList<FileParam> fileParams01 = new ArrayList<>();
        fileParams01.add(new FileParam("test.xlsl","/path/test.xlsl"));
        items.add(new Item(1, "2023年6月", "asdf", "asdf", fileParams01, 12));

        ArrayList<FileParam> fileParams02 = new ArrayList<>();
        fileParams02.add(new FileParam("test02.xlsl","/path/test02.xlsl"));
        items.add(new Item(2, "2023年9月", "asdf2", "asdf2", fileParams02, 112));
        SafetyprofileVo safetyprofileVo = new SafetyprofileVo(1, 2, 3, items);
        return Result.ofSuccess(safetyprofileVo);
    }


    @PostMapping("/add")
    public Result add(@RequestBody SafetyprofileReq req) {
        log.info(req.toString());
//        req.getOpceSecureFileParams().stream().forEach(obj->{
            log.info("文件详情：{}",req.getOpceSecureFileParams());

//        });

        return Result.ofSuccess(req.toString());
    }

    public static void main(String[] args) {
        SafetyprofileReq safetyprofileReq = new SafetyprofileReq();
        System.out.println(JSON.toJSONString(safetyprofileReq));
    }
    @PostMapping("/update")
    public Result update(@RequestBody SafetyprofileReq req) {
        log.info(req.toString());
//        req.getOpceSecureFileParams().stream().forEach(obj->{
//            log.info("文件详情：{}",obj.toString());
//
//        });
        return Result.ofSuccess(req.toString());
    }

    //    safetyprofile_delete
    @DeleteMapping("/del")
    public Result del(@RequestBody Integer[] ids) {
        Arrays.asList(ids).stream().forEach(obj -> {
            log.info(String.valueOf(obj));
        });
        return Result.ofSuccess(null);
    }

    //    safetyprofile_upload
    @PostMapping("/fileUpload")
    public Result fileUpload(MultipartFile file) {
        if (file == null) {
            log.error("file is null");
        }
        ArrayList<FileUpLoadResp> list = new ArrayList<>();
        list.add(new FileUpLoadResp("3-详细设计说明书-数据安全20230718111828.docx", "/file/3-详细设计说明书-数据安全20230718111828.docx"));

        return Result.ofSuccess(list);
    }

    //safetyprofile_export
//    @GetMapping("/export")
//    public void testfileRpcExport(@RequestParam(required = false)List<Integer> idList,
//                                  @RequestParam(required = false) String timeSlot,
//                                  @RequestParam(required = false) String title,
//                                  @RequestParam(required = false) Integer isKeynote,
//                                  HttpServletResponse response) {
    @GetMapping("/export")
    public void testfileRpcExport(SafetyprofileExport safetyprofileExport,
                                  HttpServletResponse response) {
//        if (idList != null) {
//            idList.stream().forEach(obj -> {
//                log.info(obj + "");
//            });
//        }
//        log.info(timeSlot);
//        log.info(title);
//        log.info(isKeynote + "");
        System.out.println(safetyprofileExport.toString());
        List<Export01> list = readFile("excel01.txt");
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("一个测试.xls", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);
            EasyExcel.write(response.getOutputStream(), Export01.class).sheet("数据统计").doWrite(list);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


    @PostMapping("/download")
    public void download(@RequestParam String path,
            HttpServletResponse response) {

        System.out.println(path);
        List<Export01> list = readFile("excel01.txt");
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("一个测试.xls", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);
            EasyExcel.write(response.getOutputStream(), Export01.class).sheet("数据统计").doWrite(list);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


    /**
     * 逐行读取文件并用Export01封装
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
                    export01.setRRiskType(strings.get(1));
                    export01.setOUid(strings.get(2));
                    export01.setSDevIp(strings.get(3).replaceAll(",", "."));
                    list.add(export01);
                    strings.clear();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }


}
