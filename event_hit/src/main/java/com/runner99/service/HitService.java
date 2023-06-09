package com.runner99.service;

import com.runner99.vo.EventVo;
import com.runner99.vo.IdentityVo;
import com.runner99.vo.Level;
import com.runner99.vo.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author weizhenqiang
 * @date 2023/3/30 13:46
 */
public class HitService {

    //获取命中身份
    public IdentityVo getHitIdentityVo(EventVo event, List<Level> tree, List<IdentityVo> identityVos) {

        int index = 0;


        tree.stream().forEach(level -> {
            String levelName = level.getLevelName();


        });


        return identityVos.get(index);

    }

    public static ArrayList<User> identityVosTree;

    public static void main(String[] args) {

        ArrayList<User> list = new ArrayList<>();
        list.add(new User(12,"jkl"));
        identityVosTree=list;
        identityVosTree.stream().forEach(obj->{
            System.out.println(obj.toString());
        });
        System.out.println(identityVosTree.hashCode());
        ArrayList<User> list2 = new ArrayList<>();
        list2.add(new User(1,"lll"));
        identityVosTree=list2;
        identityVosTree.stream().forEach(obj->{
            System.out.println(obj.toString());
        });
        System.out.println(identityVosTree.hashCode());

    }
}
