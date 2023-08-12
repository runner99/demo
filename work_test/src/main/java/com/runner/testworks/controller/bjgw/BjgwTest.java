package com.runner.testworks.controller.bjgw;

import com.alibaba.fastjson.JSON;
import com.runner.testworks.controller.bjgw.vo.IsolationTop5;
import com.runner.testworks.controller.bjgw.vo.Result;

import java.util.ArrayList;

/**
 * @author weizhenqiang
 * @date 2023/8/4 17:50
 */
public class BjgwTest {
    public static void main(String[] args) {

//        ArrayList<TimeHotVo> list = new ArrayList<>();
//        for (int i = 0; i < 3; i++) {
//            TimeHotVo timeHotVo = new TimeHotVo();
//            timeHotVo.setTime("11:2"+i);
//            timeHotVo.setLow(i+10);
//            timeHotVo.setCurrent(i+15);
//            timeHotVo.setHigh(i+20);
//            list.add(timeHotVo);
//        }
//
//        String jsonString = JSON.toJSONString(Result.ofSuccess(list));
//        System.out.println(jsonString);

//        ArrayList<AssetHot> list = new ArrayList<>();
//        for (int i=1;i<4;i++){
//            AssetHot assetHot = new AssetHot();
//            assetHot.setId(i);
//            assetHot.setHot(i+1000);
//            assetHot.setDbName("嘎嘎嘎"+i);
//            list.add(assetHot);
//        }
//        String jsonString = JSON.toJSONString(Result.ofSuccess(list));
//        System.out.println(jsonString);


//        ArrayList<AccessTrend> list = new ArrayList<>();
//        for (int i=1;i<4;i++){
//            AccessTrend accessTrend = new AccessTrend();
//            accessTrend.setTime("15:0"+i);
//            accessTrend.setCount(i+1000);
//            list.add(accessTrend);
//        }
//        String jsonString = JSON.toJSONString(Result.ofSuccess(list));
//        System.out.println(jsonString);

        ArrayList<IsolationTop5> list = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            IsolationTop5 isolationTop5 = new IsolationTop5();
            isolationTop5.setCount(i+100);
            isolationTop5.setIp("192.168.21."+i);
            list.add(isolationTop5);
        }
        String jsonString = JSON.toJSONString(Result.ofSuccess(list));
        System.out.println(jsonString);


    }
}
