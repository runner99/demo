package com.runner.testworks.pojo;

/**
 * @author weizhenqiang
 * @date 2023/5/30 13:56
 */

public class User {
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User() {
    }

    private Integer id;

    private String name;

}
