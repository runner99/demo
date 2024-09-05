package com.runner.testworks.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.runner.testworks.pojo.excel.Export01;
import com.runner.testworks.pojo.vo.ReqVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author weizhenqiang
 * @date 2023/5/30 13:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @ExcelProperty("id")
    private Integer id;

    @ExcelProperty("姓名")
    private String name;



    public String per(ReqVo reqVo){
        return "jkl";
    }
}
