package com.runner.testworks.controller.xzlt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.runner.testworks.controller.bjgw.vo.Result;
import com.runner.testworks.controller.xzlt.enums.ThirdOssPrefixEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author weizhenqiang
 * @date 2023/11/8 9:29
 */
@RestController
//@RequestMapping("/xzlt")
@Slf4j
public class XzltController {


    private static final String appId = "hzmc";

    /**
     * 赛猊腾龙终端DLP获取sign
     * @param appId
     * @param userName
     * @param timestamp
     * @return
     */
    @GetMapping("/api/auth/create-sign")
    public String getSign(@RequestParam("appId") String appId,
                          @RequestParam("userName") String userName,
                          @RequestParam("timestamp") String timestamp) {

        log.info("appId:{},userName:{},timestamp:{}", appId, userName, timestamp);

        return "SIGN" + System.currentTimeMillis();
    }

    /**
     * 赛猊腾龙终端DLP 获取token
     * @param appId
     * @param userName
     * @param timestamp
     * @return
     */
    @GetMapping("/api/auth/create-token")
    public Result<String> getToken(@RequestParam("appId") String appId,
                                   @RequestParam("userName") String userName,
                                   @RequestParam("timestamp") String timestamp) {

        log.info("appId:{},userName:{},timestamp:{}", appId, userName, timestamp);

        return Result.ofSuccess("TOKEN" + System.currentTimeMillis());
    }


    /**
     * 青笠API监测 获取token和username
     * @param jsonObject
     * @return
     */
    @PostMapping("/api/v1/sso/getToken")
    public JSONObject qingliGetToken(@RequestBody JSONObject jsonObject) {

        log.info("request body:{}",jsonObject.toJSONString());

        HashMap<String, Object> map = new HashMap<>();
        map.put("code",200  );
        map.put("token","token"+System.currentTimeMillis());
        map.put("msg","ok");
        map.put("username","weizhenqiang");
        return new JSONObject(map);

    }


    public static void main(String[] args) {
        String msg="{\"r_risk\":0,\"f_err\":\"0\",\"e_category\":\"common\",\"e_type\":\"db_access\",\"s_os_user\":\"LAPTOP-QVV78GH8\\Administrator\",\"s_dev_ip\":\"2.0.0.1\",\"s_dev_mac\":\"NA\",\"b_action\":\"上传\",\"o_name\":\"测试文档.docx\"}";
//        Map map = JSON.parseObject(msg, Map.class);
        Map map1 = JSONObject.parseObject(msg, Map.class);
        System.out.println(map1);
    }


}
