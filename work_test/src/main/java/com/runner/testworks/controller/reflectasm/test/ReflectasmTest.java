package com.runner.testworks.controller.reflectasm.test;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.runner.testworks.controller.reflectasm.pojo.ReflectasmEntity;
import com.runner.testworks.controller.reflectasm.test.queue.AddData;
import com.runner.testworks.controller.reflectasm.test.queue.DataQueue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author weizhenqiang
 * @date 2023/7/5 10:27
 */
public class ReflectasmTest {


    private static int ADD_THREAD_SIZE=6;

    private static MethodAccess methodAccess;

    private static Integer ASM_INDEX;
    private static Integer ASM_INDEX01;
    private static Integer ASM_INDEX02;
    private static Integer ASM_INDEX03;
    private static Integer ASM_INDEX04;
    private static Integer ASM_INDEX05;
    private static Integer ASM_INDEX06;
    private static Integer ASM_INDEX07;
    private static Integer ASM_INDEX08;
    private static Integer ASM_INDEX09;
    private static Integer ASM_INDEX10;



    static {
        methodAccess = MethodAccess.get(ReflectasmEntity.class);

        ASM_INDEX = methodAccess.getIndex("getName");



        ASM_INDEX01 = methodAccess.getIndex("getName01");
        ASM_INDEX02 = methodAccess.getIndex("getName02");
        ASM_INDEX03 = methodAccess.getIndex("getName03");
        ASM_INDEX04 = methodAccess.getIndex("getName04");
        ASM_INDEX05 = methodAccess.getIndex("getName05");
        ASM_INDEX06 = methodAccess.getIndex("getName06");
        ASM_INDEX07 = methodAccess.getIndex("getName07");
        ASM_INDEX08 = methodAccess.getIndex("getName08");
        ASM_INDEX09 = methodAccess.getIndex("getName09");
        ASM_INDEX10 = methodAccess.getIndex("getName10");



    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(ADD_THREAD_SIZE);
        for (int i=0;i<ADD_THREAD_SIZE;i++){
            executorService.execute(new AddData());
        }

        Thread.sleep(5000L);

        long begin = System.currentTimeMillis();

        /**单个属性读取
         * 处理4991062条数据耗时：373毫秒
         * 处理5894194条数据耗时：3025毫秒
         * 处理5876683条数据耗时：558毫秒
         * 处理5876859条数据耗时：3092毫秒
         * 处理5086797条数据耗时：382毫秒
         */
//        int count = asmTest();

        /**
         * 10个属性读取
         *太慢了
         */
//        int count = asmTestAttr();


        /**单个属性读取
         *处理5809466条数据耗时：2748毫秒
         * 处理5132860条数据耗时：585毫秒
         * 处理4913413条数据耗时：306毫秒
         * 处理5780030条数据耗时：2902毫秒
         * 处理5887767条数据耗时：361毫秒
         */
//        int count = asmTestIndex();

        /**
         * 10个属性读取
         * 处理1908203条数据耗时：2504毫秒
         * 处理1923563条数据耗时：2717毫秒
         * 处理1854644条数据耗时：2481毫秒
         * 处理1822030条数据耗时：2498毫秒
         * 处理1783843条数据耗时：2385毫秒
         *
         */
//        int count = asmTestIndexAttr();

        /**单个属性读取
         * 处理5564163条数据耗时：2472毫秒
         * 处理5788387条数据耗时：2771毫秒
         * 处理5204054条数据耗时：650毫秒
         * 处理5626485条数据耗时：2741毫秒
         * 处理5708804条数据耗时：3034毫秒
         */
//        int count = get();

        /**
         * 10个属性读取
         *处理1332367条数据耗时：333毫秒
         * 处理1315749条数据耗时：240毫秒
         * 处理1488042条数据耗时：326毫秒
         * 处理1348724条数据耗时：321毫秒
         * 处理1382401条数据耗时：352毫秒
         */
        int count = getAttr();


        long end = System.currentTimeMillis();



        System.out.println("处理"+count+"条数据耗时："+(end-begin)+"毫秒");



    }


    public static int asmTest(){

        int count=0;

        while (true){
            ReflectasmEntity poll = DataQueue.EVENT_QUEUE.poll();
            if (poll!=null){
                String getName = (String) methodAccess.invoke(poll, "getName");
                count++;
                continue;
            }
            break;
        }
        return count;

    }

    public static int asmTestAttr(){

        int count=0;

        while (true){
            ReflectasmEntity poll = DataQueue.EVENT_QUEUE.poll();
            if (poll!=null){

                String name01 = (String) methodAccess.invoke(poll, "getName01");
                String name02 = (String) methodAccess.invoke(poll, "getName02");
                String name03 = (String) methodAccess.invoke(poll, "getName03");
                String name04 = (String) methodAccess.invoke(poll, "getName04");
                String name05 = (String) methodAccess.invoke(poll, "getName05");
                String name06 = (String) methodAccess.invoke(poll, "getName06");
                String name07 = (String) methodAccess.invoke(poll, "getName07");
                String name08 = (String) methodAccess.invoke(poll, "getName08");
                String name09 = (String) methodAccess.invoke(poll, "getName09");
                String name10 = (String) methodAccess.invoke(poll, "getName10");
                count++;
                continue;
            }
            break;
        }
        return count;

    }


    public static int asmTestIndex(){

        int count=0;

        while (true){
            ReflectasmEntity poll = DataQueue.EVENT_QUEUE.poll();
            if (poll!=null){
                String getName = (String) methodAccess.invoke(poll, ASM_INDEX);
                count++;
                continue;
            }
            break;
        }
        return count;

    }

    public static int asmTestIndexAttr(){

        int count=0;

        while (true){
            ReflectasmEntity poll = DataQueue.EVENT_QUEUE.poll();
            if (poll!=null){
                String name01 = (String) methodAccess.invoke(poll, ASM_INDEX01);
                String name02 = (String) methodAccess.invoke(poll, ASM_INDEX02);
                String name03 = (String) methodAccess.invoke(poll, ASM_INDEX03);
                String name04 = (String) methodAccess.invoke(poll, ASM_INDEX04);
                String name05 = (String) methodAccess.invoke(poll, ASM_INDEX05);
                String name06 = (String) methodAccess.invoke(poll, ASM_INDEX06);
                String name07 = (String) methodAccess.invoke(poll, ASM_INDEX07);
                String name08 = (String) methodAccess.invoke(poll, ASM_INDEX08);
                String name09 = (String) methodAccess.invoke(poll, ASM_INDEX09);
                String name10 = (String) methodAccess.invoke(poll, ASM_INDEX10);
                count++;
                continue;
            }
            break;
        }
        return count;

    }


    public static int getAttr(){

        int count=0;
        while (true){
            ReflectasmEntity poll = DataQueue.EVENT_QUEUE.poll();
            if (poll!=null){
                String name01 = poll.getName01();
                String name02 = poll.getName02();
                String name03 = poll.getName03();
                String name04 = poll.getName04();
                String name05 = poll.getName05();
                String name06 = poll.getName06();
                String name07 = poll.getName07();
                String name08 = poll.getName08();
                String name09 = poll.getName09();
                String name10 = poll.getName10();

                count++;
                continue;
            }
            break;
        }

        return count;

    }

    public static int get(){

        int count=0;
        while (true){
            ReflectasmEntity poll = DataQueue.EVENT_QUEUE.poll();
            if (poll!=null){
                String name = poll.getName();
                count++;
                continue;
            }
            break;
        }

        return count;

    }



}
