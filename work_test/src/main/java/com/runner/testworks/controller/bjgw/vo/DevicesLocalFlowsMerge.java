package com.runner.testworks.controller.bjgw.vo;

import com.runner.testworks.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author weizhenqiang
 * @date 2023/7/31 19:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DevicesLocalFlowsMerge {



    private List<DeviceMonitorMerge> list;

    @Data
    public static class DeviceMonitorMerge{
        private Integer type;
        private String deviceName;
        private List<User> child;
    }

}
