package com.runner.testworks.controller.snmp;

import com.runner.testworks.config.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.snmp4j.*;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author weizhenqiang
 * @date 2023/11/13 15:09
 */

@RestController
@RequestMapping("/snmp")
@Slf4j
public class SnmpController {

    private static final String dir = "/data";

    private static final String community = "hzmc+Ra2$yuL";


    @GetMapping("/getInfo")
    public Result getInfo() {

        return Result.success(null);
    }

    public static void main(String[] args) {
        String ipAddress = "192.168.238.132";
        DeviceSnmpInfo deviceSnmpInfo = SnmpUtilBk.getDeviceSnmpInfo(ipAddress);

        System.out.println(deviceSnmpInfo.toString());

    }

}


