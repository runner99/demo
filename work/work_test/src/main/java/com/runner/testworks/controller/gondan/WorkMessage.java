package com.runner.testworks.controller.gondan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class WorkMessage {
    private String databaseName;
    private List<IdentityInfo> identityInfos;
    private String ownerPhone;
    private String periodValidity;
    private List<String> richsqlContents;
    private String serviceType;
    private List<SqlInfo> sqlInfos;
    private List<String> sqlTransfer;
    private List<RiskDisposal> riskDisposal;


    @Data
    public static class IdentityInfo {
        private String appName;
        private String appSign;
        private String certificate;
        private String clientIp;
        private String databaseUser;
        private String hostName;
        private String identityName;
        private String macAddr;
        private String osUser;
        private String userName;

    }

    @Data
    public static class SqlInfo {
        // Assuming this class has its own fields and methods
        // Getters and Setters
        // ...
    }


    @Data
    public static class RiskDisposal {
        private String type;
        private String level;
        private String rule;
        private String alarmCount;
        private String lastTime;
        private String address;


    }

    public static void main(String[] args) {
        long now = System.currentTimeMillis();
        long endTime = now - now % (60 * 1000);

        System.out.println(endTime);
        System.out.println(endTime-1000*60*5);
    }
}