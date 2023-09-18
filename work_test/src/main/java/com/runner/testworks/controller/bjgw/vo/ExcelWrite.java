package com.runner.testworks.controller.bjgw.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

/**
 * @author weizhenqiang
 * @date 2023/9/12 17:10
 */

@Data
public class ExcelWrite {

    @ExcelProperty("name")
    @ColumnWidth(15)
    private String name;

    @ExcelProperty("dbType")
    @ColumnWidth(15)
    private String dbType;

    @ExcelProperty("dbVersion")
    @ColumnWidth(15)
    private String dbVersion;

    @ColumnWidth(15)
    @ExcelProperty("scanIp")
    private String scanIp;

    @ColumnWidth(15)
    @ExcelProperty("desc")
    private String desc;
}
