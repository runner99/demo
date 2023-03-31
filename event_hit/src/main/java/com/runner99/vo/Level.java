package com.runner99.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author weizhenqiang
 * @date 2023/3/27 17:27
 */
@Data
@EqualsAndHashCode
public class Level {

    /**
     * 每一层的属性名
     */
    private String levelName;


    /**
     * 本层非空结点
     */
    private Map<String, Node> levelValue =new HashMap<>();


    /**
     * 本层空结点
     */
    private Map<String, Node> nullLevelValue= new HashMap<>();


}
