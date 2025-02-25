package com.runner.testworks.controller.ningbo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author weizhenqiang
 * @date 2024/5/16 9:19
 */

@RestController
public class NingboController {

    /**
     * 获取系统列表
     * @param jsonObject
     * @param request
     */
    @PostMapping("/japi/v1/management/processor")
    public JSONObject test(@RequestBody JSONObject jsonObject, HttpServletRequest request){

        String header = request.getHeader("appToken");

        System.out.println("header:"+header);
        System.out.println("body:"+jsonObject);


        String json="{\n" +
                "  \"retCode\": \"0\",\n" +
                "  \"retInfo\": \"交易成功\",\n" +
                "  \"retData\": {\n" +
                "    \"data\": [\n" +
                "      {\n" +
                "        \"projectId\": \"1231111111111\",\n" +
//                "        \"projectName\": \"项目名称示例1\",\n" +
                "        \"systemList\": [\n" +
                "          {\n" +
                "            \"systemId\": \"1\",\n" +
                "            \"systemName\": \"系统名称1-1\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"systemId\": 2,\n" +
                "            \"systemName\": \"系统名称1-2\"\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      {\n" +
                "        \"projectId\": \"125\",\n" +
//                "        \"projectName\": \"项目名称示例3\",\n" +
                "        \"systemList\": [\n" +
                "          {\n" +
                "            \"systemId\": 3,\n" +
                "            \"systemName\": \"系统名称3-1\"\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    ],\n" +
                "    \"meta\": {\n" +
                "      // 假设的元数据内容，可以根据实际需要填充\n" +
                "    },\n" +
                "    \"pagination\": {\n" +
                "      \"pageIndex\": 1,\n" +
                "      \"pageSize\": 10,\n" +
                "      \"total\": 100\n" +
                "    }\n" +
                "  }\n" +
                "}";


        return JSONObject.parseObject(json);


    }

    /**
     * 获取系统资源列表
     * @param jsonObject
     * @param request
     */
    @PostMapping("/japi/v1/management/processor_a")
    public JSONObject testa(@RequestBody JSONObject jsonObject, HttpServletRequest request){

        String header = request.getHeader("appToken");


        String json="{\n" +
                "  \"retCode\": \"0\",\n" +
                "  \"retInfo\": \"交易成功\",\n" +
                "  \"retData\": [\n" +
                "    {\n" +
                "      \"projectName\": \"项目名称1\",\n" +
                "      \"systemName\": \"系统名称1\",\n" +
                "      \"businessLogicGroup\": 1,\n" +
                "      \"resourceArea\": \"资源区域A\",\n" +
                "      \"serviceType\": \"服务类型A\",\n" +
                "      \"borrowed\": 1,\n" +
                "      \"borrowedStatus\": 1,\n" +
                "      \"hostIp\": \"192.168.1.1\",\n" +
                "      \"ports\": \"8080\",\n" +
                "      \"loadIp\": \"10.0.0.1\",\n" +
                "      \"servicePorts\": \"80\",\n" +
//                "      \"cpu\": \"4\",\n" +
                "      \"memory\": \"8\",\n" +
                "      \"dataDisk\": \"100\",\n" +
                "      \"os\": \"Linux\",\n" +
                "      \"remark\": \"备注信息1\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"projectName\": \"项目名称2\",\n" +
                "      \"systemName\": \"系统名称2\",\n" +
                "      \"businessLogicGroup\": 2,\n" +
                "      \"resourceArea\": \"资源区域B\",\n" +
                "      \"serviceType\": \"服务类型B\",\n" +
                "      \"borrowed\": 0,\n" +
                "      \"hostIp\": \"192.168.1.2\",\n" +
                "      \"ports\": \"9090\",\n" +
                "      \"loadIp\": \"10.0.0.2\",\n" +
                "      \"servicePorts\": \"443\",\n" +
//                "      \"cpu\": \"8\",\n" +
                "      \"memory\": \"16\",\n" +
                "      \"dataDisk\": \"200\",\n" +
                "      \"os\": \"Windows\",\n" +
                "      \"remark\": \"备注信息2\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"projectName\": \"项目名称3\",\n" +
                "      \"systemName\": \"系统名称3\",\n" +
                "      \"businessLogicGroup\": 3,\n" +
                "      \"resourceArea\": \"资源区域C\",\n" +
                "      \"serviceType\": \"服务类型C\",\n" +
                "      \"borrowed\": 1,\n" +
                "      \"hostIp\": \"192.168.1.3\",\n" +
//                "      \"cpu\": \"16\",\n" +
                "      \"memory\": \"32\",\n" +
                "      \"dataDisk\": \"500\",\n" +
                "      \"os\": \"macOS\",\n" +
                "      \"remark\": \"备注信息3\"\n" +
                "    }\n" +
                "    // 可以继续添加更多项目\n" +
                "  ]\n" +
                "}";

        return JSONObject.parseObject(json);

    }


    @GetMapping(value = "/ningbo/test")
    public String testNing(){
        return "aaaaa";
    }

    public static void main(String[] args) {

    }

}
