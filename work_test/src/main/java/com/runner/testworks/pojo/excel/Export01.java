package com.runner.testworks.pojo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

/**
 * @author weizhenqiang
 * @date 2023/6/2 10:19
 */
@Data
public class Export01 {

    @ExcelProperty("cc")
    @ColumnWidth(15)
    private String cc;

    @ExcelProperty("r_risk_type")
    @ColumnWidth(80)
    private String r_risk_type;

    @ExcelProperty("o_uid")
    @ColumnWidth(30)
    private String o_uid;

    @ColumnWidth(20)
    @ExcelProperty("s_dev_ip")
    private String s_dev_ip;


}
