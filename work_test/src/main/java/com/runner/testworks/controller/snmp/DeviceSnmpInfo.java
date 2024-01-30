package com.runner.testworks.controller.snmp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author weizhenqiang
 * @date 2024/1/22 17:46
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeviceSnmpInfo {
    /**
     * cpu占用
     */
    private String cpu;

    /**
     * 内存占用
     */
    private String memory;

    /**
     * 磁盘占用
     */
    private String disk;
}
