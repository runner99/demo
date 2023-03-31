package com.runner99.vo;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author weizhenqiang
 * @date 2023/3/27 17:31
 */

public class Node {


    private String key;


    /**
     * 待命中身份列表
     */
    private Long[] list;



    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long[] getList() {
        return list;
    }

    public void setList(Long[] list) {
        this.list = list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (!key.equals(node.key)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(list, node.list);
    }

    @Override
    public int hashCode() {
        int result = key.hashCode();
        result = 31 * result + Arrays.hashCode(list);
        return result;
    }

    @Override
    public String toString() {
        return "Node{" +
                "key='" + key + '\'' +
                ", list=" + Arrays.toString(list) +
                '}';
    }


    //节点详细信息
    public List<IdentityVo> getNodeInfo(List<IdentityVo> IdentityVos){

        int length = this.list.length;
        ArrayList<IdentityVo> list1 = new ArrayList<>();
        IdentityVos.stream().forEach(identityVo -> {

        });
        return list1;
    }
}
