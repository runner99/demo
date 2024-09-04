package com.runner.testworks.controller.jinhua.group;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthGroupItemVO {

    /**
     * 权限组ID
     */
    private String authId;
    /**
     * 权限组名称
     */
    private String authGroupName;
    /**
     * 子节点
     */
    private List<AuthGroupItemVO> children;
}