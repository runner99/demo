package com.runner.testworks.controller.bjgw.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author weizhenqiang
 * @date 2023/8/24 10:10
 */

public class RiskRuleVo implements Serializable {
    private static final long serialVersionUID = 543667265077529643L;

    private Long id;

    private String uid;
    /**
     * 风险名称
     */
    private String riskName;
    /**
     * 规则描述
     */
    private String ruleDesc;
    /**
     * 风险等级 1-低  2-中 3-高
     */
    private Integer riskLevel;
    /**
     * 规则内容
     */
    private String ruleContent;
    /**
     * 创建时间
     */
    private Long createTime;
    /**
     * 修改时间
     */
    private Long updateTime;
    /**
     * 0-禁用，1-启用
     */
    private Integer state;
    /**
     * 风险类型
     */
    private Integer riskType;
    /**
     * 过期数字，
     */
    private Integer expireNum;

    /**
     * expire_num单位。 1-年 2-月 3-日 4-时 5-分 6-秒/个
     */
    private Integer expireUnit;

    /**
     * 添加类型 0-系统导入，1-手动添加
     */
    private Integer addType;
    /**
     * 备注
     */
    private String remark;

    /**
     * 风险分类
     */
    private String riskCategory;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRiskName() {
        return riskName;
    }

    public void setRiskName(String riskName) {
        this.riskName = riskName;
    }

    public String getRuleDesc() {
        if(this.getExpireUnit() != null){
            if(this.ruleDesc != null){
                this.ruleDesc = this.ruleDesc.replace("{num}",this.getExpireNum() + "");
            }
        }
        return ruleDesc;
    }

    public void setRuleDesc(String ruleDesc) {
        this.ruleDesc = ruleDesc;
    }

    public Integer getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(Integer riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getRuleContent() {
        return ruleContent;
    }

    public void setRuleContent(String ruleContent) {
        this.ruleContent = ruleContent;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getRiskType() {
        return riskType;
    }

    public void setRiskType(Integer riskType) {
        this.riskType = riskType;
    }

    public Integer getExpireNum() {
        return expireNum;
    }

    public void setExpireNum(Integer expireNum) {
        this.expireNum = expireNum;
    }

    public Integer getAddType() {
        return addType;
    }

    public void setAddType(Integer addType) {
        this.addType = addType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getExpireUnit() {
        return expireUnit;
    }

    public void setExpireUnit(Integer expireUnit) {
        this.expireUnit = expireUnit;
    }

    public String getRiskCategory() {
        return riskCategory;
    }

    public void setRiskCategory(String riskCategory) {
        this.riskCategory = riskCategory;
    }
    private List<RiskRuleDesc> list;

    public List<RiskRuleDesc> getList() {
        return list;
    }

    public void setList(List<RiskRuleDesc> list) {
        this.list = list;
    }
}
