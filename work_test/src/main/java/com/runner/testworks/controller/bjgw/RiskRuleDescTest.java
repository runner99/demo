package com.runner.testworks.controller.bjgw;

import cn.hutool.core.bean.BeanUtil;
import com.runner.testworks.controller.bjgw.vo.RiskRule;
import com.runner.testworks.controller.bjgw.vo.RiskRuleDesc;
import com.runner.testworks.controller.bjgw.vo.RiskRuleDescType;
import com.runner.testworks.controller.bjgw.vo.RiskRuleVo;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;

/**
 * @author weizhenqiang
 * @date 2023/8/24 10:31
 */
public class RiskRuleDescTest {
    public static void main(String[] args) {
        ArrayList<RiskRule> list = new ArrayList<>();
        RiskRule riskRule01 = new RiskRule();
        riskRule01.setRiskName("暴力破解");
        riskRule01.setRuleDesc("同一身份或客户端IP或终端IP，1分钟内发生7次登录失败。");
        list.add(riskRule01);

        RiskRule riskRule02 = new RiskRule();
        riskRule02.setRiskName("撞库");
        riskRule02.setRuleDesc("一个数据库账号或一个业务账号，1分钟内发起10次登录事件。");
        list.add(riskRule02);

        RiskRule riskRule03 = new RiskRule();
        riskRule03.setRiskName("账号公用");
        riskRule03.setRuleDesc("登录事件中，一个数据库账号或业务账号，1天使用过10个客户端IP或终端IP");
        list.add(riskRule03);


        ArrayList<RiskRuleVo> result = new ArrayList<>();
        list.stream().forEach(obj->{
            RiskRuleVo riskRuleVo = new RiskRuleVo();
            BeanUtils.copyProperties(obj,riskRuleVo);
            switch (obj.getRiskName()){
                case "暴力破解":{
                    System.out.println("暴力破解");
                    break;
                }
                case "撞库":{
                    System.out.println("撞库");
                    break;
                }
                default:{
                    ArrayList<RiskRuleDesc> riskRuleDescs = new ArrayList<>();
                    riskRuleDescs.add(new RiskRuleDesc(RiskRuleDescType.TEXT.getValue(),obj.getRuleDesc()));
                    riskRuleVo.setList(riskRuleDescs);
                }
            }
            result.add(riskRuleVo);
        });

        System.out.println("gg");


    }
}
