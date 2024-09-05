package com.runner.testworks.controller.bjgw.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

/**
 * @author weizhenqiang
 * @date 2023/9/12 17:10
 */

@Data
public class ExcelRead {

    @ExcelProperty("name")
    @ColumnWidth(15)
    private String name;

    @ExcelProperty("ip")
    @ColumnWidth(15)
    private String ip;

    @ExcelProperty("vip")
    @ColumnWidth(15)
    private String vip;

    @ExcelProperty("scanIp")
    @ColumnWidth(15)
    private String scanIp;

    @ColumnWidth(15)
    @ExcelProperty("dbType")
    private String dbType;

    @ColumnWidth(15)
    @ExcelProperty("dbVersion")
    private String dbVersion;
}
