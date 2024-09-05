package com.runner.testworks.controller.bjgw;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.runner.testworks.controller.bjgw.vo.*;
import com.runner.testworks.controller.suzhou.utils.TimeFormatEnum;
import com.runner.testworks.controller.suzhou.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Collectors;

/**
 * @author weizhenqiang
 * @date 2023/7/31 19:39
 */
@RestController
@RequestMapping("/bjgw")
@Slf4j
public class BjgwController {

    public static boolean isGlobal;

    public static HashSet<ExcelWrite> result;

    public static HashSet<String> riskRuleSet;

    static {
        HashSet<String> hashSet = new HashSet<>();
//        dsm
        hashSet.add("长链接行为");
        hashSet.add("暴力破解");
        hashSet.add("撞库");
        hashSet.add("账号公用");
        hashSet.add("全局高危操作");
        hashSet.add("高危操作");
        hashSet.add("大量拖库");
        hashSet.add("批量拖库");
        hashSet.add("僵尸账号");
        hashSet.add("账号首次访问");
        hashSet.add("账号风险");


//        现场
        hashSet.add("批量数据泄露");


//        修改后
        hashSet.add("大量拖库风险");
        hashSet.add("批量拖库风险");


        riskRuleSet = hashSet;
    }

    @GetMapping("/test")
    public Result test() {
        try {
            int i = 0;
            int i1 = 12 / i;
        } catch (Exception e) {
            log.error("jkl:{}", e.getMessage());
            return Result.ofSuccess("gg");
        }

        return Result.ofSuccess("666");
    }

    @PostMapping("/import")
    public void excelImport03(@RequestParam("file") MultipartFile file, @RequestParam("name") String name) throws IOException {
        result = new HashSet<>();
        ArrayList<ExcelRead> list = new ArrayList<>();
        EasyExcel.read(file.getInputStream(), ExcelRead.class, new AnalysisEventListener() {

            //          在读取完一行数据后调用
            @Override
            public void invoke(Object o, AnalysisContext analysisContext) {
                list.add((ExcelRead) o);
            }

            //           在读取完所有数据后调用
            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                System.out.println("读完了");
            }
        }).sheet().doRead();

        String[] split = name.split(",");
        HashSet<String> set = new HashSet<>();
        for (int i = 0; i < split.length; i++) {
            set.add(split[i].trim());
        }

        List<ExcelRead> collect = list.stream().filter(obj -> set.contains(obj.getName())).collect(Collectors.toList());


        HashMap<String, List<String>> map = new HashMap<>();

        collect.stream().forEach(obj -> {
            ExcelWrite excelWrite = new ExcelWrite();
            excelWrite.setName(obj.getName().trim());
            excelWrite.setDbVersion(obj.getDbVersion().trim());
            excelWrite.setDbType(obj.getDbType().trim());
            excelWrite.setScanIp(obj.getScanIp().trim());

            if (StringUtils.isNotBlank(obj.getIp().trim())) {
                if (CollectionUtils.isEmpty(map.get(obj.getName()))) {
                    ArrayList<String> list1 = new ArrayList<>();
                    list1.add(obj.getIp().trim());
                    map.put(obj.getName(), list1);
                } else {
                    map.get(obj.getName()).add(obj.getIp());
                }

            }
            if (StringUtils.isNotBlank(obj.getVip().trim())) {
                if (CollectionUtils.isEmpty(map.get(obj.getName()))) {
                    ArrayList<String> list1 = new ArrayList<>();
                    list1.add(obj.getVip().trim());
                    map.put(obj.getName(), list1);
                } else {
                    map.get(obj.getName()).add(obj.getVip());
                }
            }

            result.add(excelWrite);
        });

        result.stream().forEach(obj -> {


            if (!CollectionUtils.isEmpty(map.get(obj.getName()))) {
                StringBuffer buffer = new StringBuffer();
                List<String> list1 = map.get(obj.getName());
                for (int i = 0; i < map.get(obj.getName()).size(); i++) {
                    buffer.append("节点名称node");
                    buffer.append(i + 1);
                    buffer.append(",,");
                    buffer.append(list1.get(i));
                    buffer.append(",;");
                }
                obj.setDesc(buffer.toString());
            }


        });

        System.out.println("转换完成");

    }


    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {

        Set<ExcelWrite> list = result;

        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("export", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), ExcelWrite.class).sheet("数据统计").doWrite(list);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @PostMapping("/riskRuleImport")
    public Result riskRuleImport(@RequestParam("file") MultipartFile file) throws IOException {

        ArrayList<RskRuleRead> list = new ArrayList<>();
        EasyExcel.read(file.getInputStream(), RskRuleRead.class, new AnalysisEventListener() {

            //          在读取完一行数据后调用
            @Override
            public void invoke(Object o, AnalysisContext analysisContext) {
                list.add((RskRuleRead) o);
            }

            //           在读取完所有数据后调用
            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                System.out.println("读完了");
            }
        }).sheet().doRead();
        StringBuffer buffer = new StringBuffer();


//        安华的风险规则
        list.add(new RskRuleRead("批量数据泄露_对接", "对于核心业务的敏感数据，维护人员通常不允许查询大量的数据，避免批量的数据泄露到不法分子手中造成重大损失和影响。在实际的应用中，可以在该规则中增加对敏感数据表的设置，使该规则更具有针对性。 您也可以针对日常的数据库操作的需求增加或减少行数的阈值设置，如果您的业务系统的正常业务中包含的批量SELECT操作，您可采取如下方式优化规则；1.将此业务系统的IP地址从当前规则中排除；2.将该操作的语句模板设置为信任语句；3.新建相应的信任规则。如果出现预期外的该类行为，应该尽快确定该行为造成的影响并及时挽回损失。"));
        list.add(new RskRuleRead("MySQL-数据库用户密码泄露", "攻击者可通过系统用户表获取了用户登录密码密文，根据一定的算法可以暴力的猜测出登录密码明文。 严格控制相关表的访问权限，并避免超级管理员用户的泄露或对外借用"));
        list.add(new RskRuleRead("MYSOL_创建无密码用户或者连接", "MySQL错吴码1133"));
        list.add(new RskRuleRead("MySQL_删除不存在的数据库的行为", "MySQL错误码1008"));


        HashMap<String, String> map = new HashMap<>();
        list.stream().forEach(obj -> {
            if (map.get(obj.getName()) == null) {
                map.put(obj.getName(), "cunzai");
            } else {
                System.out.println("此规则已存在:" + obj.getName());
            }
        });


        int maxDesc = 0;
        for (RskRuleRead obj : list) {
            if (riskRuleSet.contains(obj.getName())) {
                System.out.println("此规则已存在：" + obj.toString());
                continue;
            }
            buffer.append("('e445b6f81ae311ee91e400224648320a', '");
            buffer.append(obj.getName().trim().replaceAll("'", ""));
            buffer.append("', '");
            buffer.append(obj.getRemark().trim().replaceAll("'", "").replaceAll("\n", ""));
//            buffer.append("', 1,  0,   0, 'n'),");
            buffer.append("', ");
            buffer.append(System.nanoTime() % 3 + 1);
            buffer.append(",  0,   0, 'n'),");

            if (StringUtils.isBlank(obj.getName()) || StringUtils.isBlank(obj.getRemark())) {
                System.out.println(obj.toString());
            }
            if (obj.getRemark().length() > maxDesc) {
                maxDesc = obj.getRemark().length();
            }


        }

        return Result.ofSuccess(buffer.toString().substring(0, buffer.toString().length() - 1) + ";");

    }


    @PostMapping("/ipList")
    public Result ipList(@RequestParam("file") MultipartFile file) throws IOException {

        ArrayList<IpList> list = new ArrayList<>();
        EasyExcel.read(file.getInputStream(), IpList.class, new AnalysisEventListener() {

            //          在读取完一行数据后调用
            @Override
            public void invoke(Object o, AnalysisContext analysisContext) {
                list.add((IpList) o);
            }

            //           在读取完所有数据后调用
            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                System.out.println("读完了");
            }
        }).sheet().doRead();

        HashMap<String, List<String>> map = new HashMap<>();
        list.stream().forEach(obj -> {
            if (map.get(obj.getName()) == null) {
                ArrayList<String> list1 = new ArrayList<>();
                list1.add(obj.getIp());
                map.put(obj.getName(), list1);
            } else {
                map.get(obj.getName()).add(obj.getIp());
            }
        });

        StringBuffer buffer = new StringBuffer();

        Set<String> strings = map.keySet();
        for (String obj : strings) {
            buffer.append("{");
            buffer.append("\"nodeName\":\"");
            buffer.append(obj.trim());
            buffer.append("\",\"childs\":[");
            for (String da : map.get(obj)) {
                buffer.append("{");
                buffer.append("\"" + "ip" + "\":");
                buffer.append("\""+da+"\"");
                buffer.append("}");
                buffer.append(",");
            }

            buffer = new StringBuffer(buffer.substring(0, buffer.length() - 1));
            buffer.append("]},");
        }

        String substring = buffer.toString().substring(0, buffer.length() - 1);

        try {
            FileWriter write = new FileWriter("/test/ipList.txt");
            BufferedWriter bw = new BufferedWriter(write);
            bw.write(substring);
            bw.close();
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.ofSuccess(substring);

    }

    @GetMapping("/before")
    public Result getip() {
        List<String> ipList = getIpList();
        StringBuffer stringBuffer = new StringBuffer();
        ipList.stream().forEach(obj -> {
            int i = obj.indexOf(":");
            if (i == -1) {
                stringBuffer.append("\"");
                stringBuffer.append(obj.trim());
                stringBuffer.append("\"");
                stringBuffer.append(",");
            } else {
                stringBuffer.append("\"");
                stringBuffer.append(obj.substring(0, i).trim());
                stringBuffer.append("\"");
                stringBuffer.append(",");
            }
        });
        String[] split = stringBuffer.toString().split(",");
        HashSet<String> result = new HashSet<>();
        for (int i = 0; i < split.length; i++) {
            result.add(split[i]);
        }

        StringBuffer buffer = new StringBuffer();
        result.stream().forEach(obj -> {
            buffer.append(obj);
            buffer.append(",");
        });
        String substring = buffer.toString().substring(0, buffer.length() - 1);
        System.out.println(substring);
        try {
            FileWriter write = new FileWriter("/test/result.txt");
            BufferedWriter bw = new BufferedWriter(write);
            bw.write(substring);
            bw.close();
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Result.ofSuccess("666");
    }

    public static List<String> getIpList() {
        String pathname = "/test/ip.txt";

        ArrayList<String> list = new ArrayList<>();

        try (FileReader reader = new FileReader(pathname);
             BufferedReader br = new BufferedReader(reader)
        ) {
            String line;

            while ((line = br.readLine()) != null) {

                list.add(line);


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<String> result = new ArrayList<>();

        list.stream().forEach(obj -> {
            if (obj != null && obj.trim().length() != 0) {
                String[] split = obj.split(",");
                for (int i = 0; i < split.length; i++) {
                    result.add(split[i].trim());
                }
            }
        });

        return result;
    }

    @GetMapping("/remove")
    public Result remove() {
        List<String> ipList = getResult();

        HashSet<String> set = new HashSet<>();
        ipList.stream().forEach(obj->{
            set.add(obj);
        });

        StringBuffer buffer = new StringBuffer();
        set.stream().forEach(obj->{
            buffer.append(obj);
            buffer.append(",");
        });
        String substring = buffer.toString().substring(0, buffer.toString().length() - 1);
        try {
            FileWriter write = new FileWriter("/test/remove.txt");
            BufferedWriter bw = new BufferedWriter(write);
            bw.write(substring);
            bw.close();
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Result.ofSuccess("666");
    }


    public static List<String> getResult() {
        String pathname = "/test/result.txt";

        StringBuffer buffer = new StringBuffer();

        try (FileReader reader = new FileReader(pathname);
             BufferedReader br = new BufferedReader(reader)
        ) {
            String line;

            while ((line = br.readLine()) != null) {

                buffer.append(line);


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<String> result = new ArrayList<>();

        String[] split = buffer.toString().replaceAll(" ", "").split(",");
        for (int i = 0; i < split.length; i++) {
            result.add(split[i].trim());
        }

        return result;
    }


    @GetMapping("/getIpMapping")
    public Result getIpMapping() {

        String json = getIpMappingJson();
        JSONObject jsonObject = JSONObject.parseObject(json);
        StringBuffer isolatorsIps = new StringBuffer();
        List<IsolatorsVo> list = JSONArray.parseArray(jsonObject.getJSONArray("Isolators").toJSONString(), IsolatorsVo.class);
        list.stream().forEach(obj->{
            obj.getChilds().stream().forEach(da->{
                String s = (String) da.get("ip");
                if (StringUtils.isNotBlank(s)){
                    isolatorsIps.append("\""+s+"\",");
                }
            });
        });


        StringBuffer outAndInner = new StringBuffer();

        jsonObject.getJSONArray("OuterApplication").stream().forEach(obj->{
            String obj1 = (String) obj;
            if (StringUtils.isNotBlank(obj1)){
                outAndInner.append("\""+obj1+"\",");
            }
        });
        jsonObject.getJSONArray("OuterOM").stream().forEach(obj->{
            String obj1 = (String) obj;
            if (StringUtils.isNotBlank(obj1)){
                outAndInner.append("\""+obj1+"\",");
            }
        });
        jsonObject.getJSONArray("InnerApplication").stream().forEach(obj->{
            String obj1 = (String) obj;
            if (StringUtils.isNotBlank(obj1)){
                outAndInner.append("\""+obj1+"\",");
            }
        });
        jsonObject.getJSONArray("InnerOM").stream().forEach(obj->{
            String obj1 = (String) obj;
            if (StringUtils.isNotBlank(obj1)){
                outAndInner.append("\""+obj1+"\",");
            }
        });

        String substring = "isolatorsIps:"+isolatorsIps.toString().substring(0, isolatorsIps.toString().length() - 1);
        System.out.println("isolatorsIps:"+substring);

        String substring1 = "outAndInner:"+outAndInner.toString().substring(0, outAndInner.toString().length() - 1);
        System.out.println("outAndInner:"+substring1);

        String[] split = outAndInner.toString().split(",");
        System.out.println("四大部分的ip数量"+split.length);

        String result = substring+"\r\n"+substring1;
        try {
            FileWriter write = new FileWriter("/test/ipmap.txt");
            BufferedWriter bw = new BufferedWriter(write);
            bw.write(result);
            bw.close();
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Result.ofSuccess(666);
    }

    public static String getIpMappingJson() {
        String pathname = "/test/ip_mapping.json";

        StringBuffer buffer = new StringBuffer();

        try (FileReader reader = new FileReader(pathname);
             BufferedReader br = new BufferedReader(reader)
        ) {
            String line;

            while ((line = br.readLine()) != null) {
                buffer.append(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    public static void main(String[] args) {

        String msg="jkl||aa";
        System.out.println(msg.replaceAll("\\|\\|","hello"));


    }

    public static void logToFile(String filename, String content) {

        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            fw = new FileWriter(filename, true);
            bw = new BufferedWriter(fw);
            bw.write(content+"\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
