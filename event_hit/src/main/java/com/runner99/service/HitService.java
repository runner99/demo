package com.runner99.service;

import com.runner99.vo.EventVo;
import com.runner99.vo.IdentityVo;
import com.runner99.vo.Level;

import java.util.ArrayList;
import java.util.List;

/**
 * @author weizhenqiang
 * @date 2023/3/30 13:46
 */
public class HitService {

    //获取命中身份
    public IdentityVo getHitIdentityVo(EventVo event, List<Level> tree, List<IdentityVo> identityVos){

        int index = 0;



        tree.stream().forEach(level -> {
            String levelName = level.getLevelName();


        });



        return identityVos.get(index);

    }
}
