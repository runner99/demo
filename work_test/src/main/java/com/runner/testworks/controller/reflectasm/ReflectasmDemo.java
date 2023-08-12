package com.runner.testworks.controller.reflectasm;

import com.esotericsoftware.reflectasm.ConstructorAccess;
import com.esotericsoftware.reflectasm.FieldAccess;
import com.esotericsoftware.reflectasm.MethodAccess;
import com.runner.testworks.controller.reflectasm.pojo.ReflectasmEntity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author weizhenqiang
 * @date 2023/7/4 17:27
 */
public class ReflectasmDemo {
    public static void main(String[] args) throws Exception {
        ReflectasmEntity reflectasm = new ReflectasmEntity(1L, "hahah");
        System.out.println("--------------单次获取----------------------");

        System.out.println("直接get获取方法返回值单次耗时："+getDirect(reflectasm,1)+"毫秒");
        System.out.println("jdk单次获取反射耗时：" + getJdk(reflectasm, 1)+"毫秒");
        System.out.println("reflectasm单次获取反射对象耗时：" + getReflectasm(reflectasm, 1)+"毫秒");

        System.out.println("---------------多次获取-------------------");

        Integer count = 10000000;
        System.out.println("直接get获取方法返回值"+count+"次耗时："+getDirect(reflectasm,count)+"毫秒");
        System.out.println("jdk" + count + "次耗时：" + getJdk(reflectasm, count)+"毫秒");
        System.out.println("Reflectasm" + count + "次耗时：" + getReflectasm(reflectasm, count)+"毫秒");


        System.out.println("---------------Reflectasm方法索引反射-----------------------");
        System.out.println("Reflectasm方法索引反射"+count+"次耗时"+getReflectasmIndex(reflectasm,count)+"毫秒");


    }

    public static Long getReflectasmIndex(Object reflectasm, Integer count){
        long begin = System.currentTimeMillis();
        MethodAccess methodAccess = MethodAccess.get(reflectasm.getClass());
        int index = methodAccess.getIndex("getName");

        while (count>0){
            String invoke = (String) methodAccess.invoke(reflectasm, index);
            count--;
        }
        long end = System.currentTimeMillis();
        return end-begin;
    }

    public static Long getDirect(ReflectasmEntity reflectasm, Integer count) {
        long begin = System.currentTimeMillis();
        while (count>0){
            String name = reflectasm.getName();
            count--;
        }
        long end = System.currentTimeMillis();
        return end-begin;
    }

    public static Long getReflectasm(Object reflectasm, Integer count) {

        long begin = System.currentTimeMillis();
        Class<?> aClass = reflectasm.getClass();
        MethodAccess methodAccess = MethodAccess.get(aClass);
        while (count > 0) {
            String getName = (String) methodAccess.invoke(reflectasm, "getName");
            count--;
        }

        long end = System.currentTimeMillis();

        return end - begin;
    }


    public static Long getJdk(Object reflectasm, Integer count) throws Exception {

        long begin = System.currentTimeMillis();
        Class<?> aClass = reflectasm.getClass();
        while (count > 0) {
            Method getName = aClass.getMethod("getName");
            String invoke = (String) getName.invoke(reflectasm);
            count--;
        }


        long end = System.currentTimeMillis();

        return end - begin;

    }


}
