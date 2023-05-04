package com.runner99.pojo;

import lombok.Data;

import java.util.List;

/**
 * @author weizhenqiang
 * @date 2023/4/9 23:11
 */
@Data
public class User {
    private String id;
    private String name;
    private List<Home> home;
}
