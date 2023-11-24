package com.runner.testworks.controller.quzhou.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author weizhenqiang
 * @date 2023/11/24 10:19
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RuleExport {

    @ExcelProperty("id")
    @ColumnWidth(15)
    private int id;

    @ExcelProperty("姓名")
    @ColumnWidth(15)
    private String name;


}
