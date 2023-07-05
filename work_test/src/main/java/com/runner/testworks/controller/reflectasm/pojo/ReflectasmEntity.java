package com.runner.testworks.controller.reflectasm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author weizhenqiang
 * @date 2023/7/4 17:29
 */
@Data
@NoArgsConstructor
public class ReflectasmEntity {

    private Long id;

    public String name;


    public String name01;
    public String name02;
    public String name03;
    public String name04;
    public String name05;
    public String name06;
    public String name07;
    public String name08;
    public String name09;
    public String name10;


    public ReflectasmEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public ReflectasmEntity(Long id, String name, String name01, String name02, String name03, String name04, String name05, String name06, String name07, String name08, String name09, String name10) {
        this.id = id;
        this.name = name;
        this.name01 = name01;
        this.name02 = name02;
        this.name03 = name03;
        this.name04 = name04;
        this.name05 = name05;
        this.name06 = name06;
        this.name07 = name07;
        this.name08 = name08;
        this.name09 = name09;
        this.name10 = name10;
    }
}
