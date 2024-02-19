package com.runner99.sql.controller;


import com.runner99.sql.common.Result;
import com.runner99.sql.mapper.RiskOutMapper;
import com.runner99.sql.pojo.RiskOut;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author weizhenqiang
 * @date 2024/2/18 11:17
 */
@RestController("/sql")
@Slf4j
public class SqlController {


    @Autowired
    private RiskOutMapper riskOutMapper;

    /**
     * 占位符查询和sql注入查询比较
     * @param size
     * @return
     */
    @GetMapping("/testLength")
    public Result<Boolean> testLength(@RequestParam(value = "size") long size) {
        ArrayList<String> list = new ArrayList<>();

        StringBuffer buffer = new StringBuffer();
        long count = size;
        try {
            while (count > 0) {
                list.add("'"+(System.currentTimeMillis()-count)+"'");
                count--;
            }
            list.stream().forEach(obj->{
                buffer.append(obj);
                buffer.append(",");
            });

            //        占位符式
            long zstart = System.currentTimeMillis();
            riskOutMapper.selectzTestLength(list);
            long zend = System.currentTimeMillis();
            log.info("占位符式"+size + "个name查询共耗时：" + (zend - zstart) + "毫秒");


//        sql注入式
            long start = System.currentTimeMillis();
            List<RiskOut> riskOuts = riskOutMapper.selectTestLength(buffer.toString().substring(0, buffer.length() - 1));
            long end = System.currentTimeMillis();
            log.info("sql注入式"+size + "个name查询共耗时：" + (end - start) + "毫秒");


        }catch (Exception e){
//            log.error("testLength is fail :"+e.getMessage(),e);
//            log.error("testLength is fail :"+e.getMessage());

            return Result.fail(e.getMessage());
        }


        return Result.success(true);
    }


    @GetMapping(value = "/getById")
    public Result<RiskOut> getById(@RequestParam Long id) {
        RiskOut riskOut=null;
        try {
            log.info("start select one by id info");
            log.debug("start select one by id debug");
            riskOut = riskOutMapper.selectByPrimaryKey(id);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return Result.success(riskOut);
    }

    @GetMapping(value = "/getByName")
    public Result<List<RiskOut>> getByName(@RequestParam String name) {
        return Result.success(riskOutMapper.selectByName(name));
    }


    /**
     * 一次插入一条和批量插入比较
     * @param size
     * @return
     */
    @GetMapping(value = "/insert")
    public Result<Boolean> insert(@RequestParam(value = "size") Long size) {

        try {
            Long count = size;
            ArrayList<RiskOut> list = new ArrayList<>();
            while (count > 0) {
                count--;
                RiskOut riskOut = new RiskOut();
                riskOut.setName(System.currentTimeMillis() + "_" + size);
                riskOut.setOutIp("192.168.1.101");
                list.add(riskOut);
            }

            long start = System.currentTimeMillis();
            list.stream().forEach(obj -> {
                riskOutMapper.insertSelective(obj);
            });
            long end = System.currentTimeMillis();
            log.info("insert one 插入" + size + "条数据共花费：" + (end - start) + "毫秒");


            long batchStart = System.currentTimeMillis();
            int i = riskOutMapper.insertBatch(list);
            long batchEnd = System.currentTimeMillis();
            log.info("insert batch 插入" + i + "条数据共花费：" + (batchEnd - batchStart) + "毫秒");

        } catch (Exception e) {
            log.error("insert fail :" + e.getMessage(), e);
            return Result.fail(e.getMessage());
        }


        return Result.success(true);
    }


}
