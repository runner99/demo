package com.runner.testworks.controller.quzhou.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author weizhenqiang
 * @date 2023/11/24 10:19
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RuleExport {

    @ExcelProperty("风险规则名称")
    @ColumnWidth(20)
    private String ruleName;

    @ExcelProperty("告警等级（1-低 2-中 3-高）")
    @ColumnWidth(40)
    private Integer alertingLevel;

    @ExcelProperty("数据源id列表")
    @ColumnWidth(20)
    private String dsIds;

    @ExcelProperty("规则说明")
    @ColumnWidth(20)
    private String ruleDesc;

    @ExcelProperty("规则表达式")
    @ColumnWidth(20)
    private String regularExpression;

    @ExcelProperty("规则表达式（1-登录事件  2-访问事件）")
    @ColumnWidth(60)
    private Integer eventType;

    @ExcelProperty("登录时间起")
    @ColumnWidth(20)
    private Long loginTimeStart;

    @ExcelProperty("登录时间止")
    @ColumnWidth(20)
    private Long loginTimeEnd;

    @ExcelProperty("访问时间起")
    @ColumnWidth(20)
    private Long accessTimeStart;

    @ExcelProperty("访问时间止")
    @ColumnWidth(20)
    private Long accessTimeEnd;

    @ExcelProperty("风险类型")
    @ColumnWidth(20)
    private String riskCategory;
}
