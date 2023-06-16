package com.runner.testworks.controller.jinhua;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSONObject;
import com.runner.testworks.config.RestTemplateConfig;
import com.runner.testworks.config.Result;

import com.runner.testworks.pojo.excel.Export01;
import com.runner.testworks.pojo.vo.ReqVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author weizhenqiang
 * @date 2023/5/10 14:36
 * 金华httpsRestTemplate文件上传，excel导出，导入功能
 */

@RestController
@RequestMapping("/jinhua")
@Slf4j
public class JinHuaController {

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
            if (result.getStatusCode().equals(HttpStatus.OK)){
                log.info("请求成功");
            }
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
     * 测试rpc下载文件
     *
     * @return
     */
    @GetMapping("/test02")
    public void test02(HttpServletResponse response, String fileName) {

        String url = "http://localhost:8080/test02/test?fileName="+fileName;

        HttpHeaders httpHeaders = new HttpHeaders();

        HashMap<String, Object> map = new HashMap<>();
//        map.put("fileName", fileName);

        HttpEntity fromEntity = new HttpEntity<>(new JSONObject(map), httpHeaders);

        try {
            ResponseEntity<byte[]> exchange = httpsRestTemplate.exchange(url, HttpMethod.GET, fromEntity, byte[].class);
//            ResponseEntity<byte[]> exchange = httpsRestTemplate.getForEntity(url, byte[].class);
            byte[] data = exchange.getBody();

            response.reset();
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Disposition", "attachment; filename="+fileName);
            response.addHeader("Content-Length", "" + data.length);
            response.setContentType("application/octet-stream; charset=UTF-8");
            IOUtils.write(data, response.getOutputStream());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

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
            System.out.println("创建临时文件" + tempFilePath);
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
     * 测试远程调用上传文件，非磁盘占用方式
     */
    @PostMapping("/test04")
    public void test04(MultipartFile file, String name) {

        String url = "http://localhost:8080/test03/test";

        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("multipart/form-data");
        headers.setContentType(type);
        headers.add("Content-Length", String.valueOf(file.getSize()));


        ByteArrayResource resource = null;
        try {
            resource = new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            };
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //设置请求体

        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
        form.add("file", resource);
        form.add("name", name);
        form.add("desc","haha");

//　　　用HttpEntity封装整个请求报文
        HttpEntity<MultiValueMap<String, Object>> files = new HttpEntity<>(form, headers);
        try {
            ResponseEntity<JSONObject> exchange = httpsRestTemplate.exchange(url, HttpMethod.POST, files, JSONObject.class);
            if (exchange.getStatusCode().equals(HttpStatus.OK)){
                System.out.println("请求成功");
            }
//            exchange.get
            System.out.println(exchange);
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }

    }

    /**
     * 模拟三方上传文件
     *
     * @param file
     * @param name
     */
    @PostMapping("/test03/test")
    public Result testfileRpcUpload(@RequestParam("file") MultipartFile file,
                                    @RequestParam("msg") String msg,
                                    @RequestParam("name") String name,
                                    @RequestParam("type") String type) {
        System.out.println("模拟三方接收文件");
        System.out.println(msg);
        System.out.println(name);
        System.out.println(type);
        try {
            System.out.println(name);
            String tempFilePath = "C:\\test\\" + file.getOriginalFilename();
            File tempFile = new File(tempFilePath);
            file.transferTo(tempFile);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Result.success(null);

    }

    /**
     * 模拟三方下载文件(1)
     */
    @GetMapping("/test02/test")
    public void testfileRpcDownload(HttpServletResponse response, @RequestParam("fileName") String fileName) {
        List<Export01> list = readFile("excel01.txt");

        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);
            EasyExcel.write(response.getOutputStream(), Export01.class).sheet("数据统计").doWrite(list);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


    /**
     * 模拟三方下载文件(2)
     */
    @GetMapping("/test02/test02")
    public void testfileRpcDownload02(HttpServletResponse response) {
        List<Export01> list = readFile("excel01.txt");

        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("haha.xlsx", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);
            EasyExcel.write(response.getOutputStream(), Export01.class).sheet("数据统计").doWrite(list);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * 模拟三方下载文件(3)
     */
    @GetMapping("/test02/test03")
    public void testfileRpcDownload03(HttpServletResponse response) {

        try {
            String filePath="C:\\test\\ruoyi.zip\\";
            byte[] data = InputStream2ByteArray(filePath);
            response.reset();
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Disposition", "attachment; filename="+"haa.zip");
            response.addHeader("Content-Length", "" + data.length);
            response.setContentType("application/octet-stream; charset=UTF-8");
            IOUtils.write(data, response.getOutputStream());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private static byte[] InputStream2ByteArray(String filePath) throws IOException {

        InputStream in = new FileInputStream(filePath);
        byte[] data = toByteArray(in);
        in.close();

        return data;
    }

    private static byte[] toByteArray(InputStream in) throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while ((n = in.read(buffer)) != -1) {
            out.write(buffer, 0, n);
        }
        return out.toByteArray();
    }

    /**
     * EXCEL导出功能
     *
     * @param response
     */
    @GetMapping("/excel01")
    public void excelExport01(HttpServletResponse response) {

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
    public void excelExport03(HttpServletResponse response) {

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
     * 读取excel文件
     * @param file
     * @param response
     * @throws IOException
     */
    @PostMapping("/excelImport03")
    public void excelImport03(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws IOException {

        ArrayList<Export01> list = new ArrayList<>();
        EasyExcel.read(file.getInputStream(), Export01.class, new AnalysisEventListener() {

//          逐行读取excel的数据
            @Override
            public void invoke(Object o, AnalysisContext analysisContext) {
                list.add((Export01) o);
            }

//           读取完之后的操作
            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                System.out.println("读完了");
            }
        }).sheet().doRead();

        System.out.println(list);
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

//        list.stream().forEach(obj -> {
//            System.out.println(obj.toString());
//        });

        return list;
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

    @GetMapping("/test")
    public Result test(){
        log.info("jkl");
        return Result.success(null);
    }

    public static void main(String[] args) {
        Object yml="string";
        String method=(String)yml;
        HttpMethod resolve = HttpMethod.resolve(method);
        HttpMethod method1 = Optional.ofNullable(resolve).orElse(HttpMethod.POST);
        System.out.println(resolve);
    }

}
