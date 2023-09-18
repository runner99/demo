package com.runner.testworks.controller.bjgw.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author weizhenqiang
 * @date 2023/9/15 14:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IpList {


    @ExcelProperty("name")
    @ColumnWidth(15)
    private String name;

    @ExcelProperty("ip")
    @ColumnWidth(15)
    private String ip;


}
