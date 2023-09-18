package com.runner.testworks.controller.bjgw.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author weizhenqiang
 * @date 2023/9/13 16:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RskRuleRead {
    @ExcelProperty("name")
    @ColumnWidth(15)
    private String name;

    @ExcelProperty("remark")
    @ColumnWidth(15)
    private String remark;
}
