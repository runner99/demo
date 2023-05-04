package com.runner.locks.jinhua.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author weizhenqiang
 * @date 2023/4/24 17:45
 */

public class User {
    private int id;
    private String name;

    public User() {
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
