package com.runner99.util;

import com.runner99.vo.IdentityVo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class IdentityVoUtil {

    public static ArrayList<IdentityVo> getIdentityVos(int numObjects) {

        HashSet<IdentityVo> ids = getIds(numObjects);
        while (true){
            if (numObjects==ids.size()){
                break;
            }else {
                HashSet<IdentityVo> ids1 = getIds(numObjects - ids.size());
                ids.addAll(ids1);
            }
        }

        ArrayList<IdentityVo> list = new ArrayList<>();
        ids.stream().forEach(identityVo -> list.add(identityVo));


        return list;

    }

    public static HashSet<IdentityVo> getIds(int number){
        Random random = new Random();
        HashSet<IdentityVo> identityVos = new HashSet<>();

        for (int i = 0; i < number; i++) {
            IdentityVo obj = new IdentityVo();

            obj.setProp1(random.nextInt(2) == 0 ? "prop1:" + random.nextInt(100) : null);
//            obj.setProp1(null);
//            obj.setProp1( "prop1:" + random.nextInt(1000000000));
            obj.setProp2(random.nextInt(2) == 0 ? "prop2:" + random.nextInt(100) : null);
            obj.setProp3(random.nextInt(2) == 0 ? "prop3:" + random.nextInt(100) : null);
            obj.setProp4(random.nextInt(2) == 0 ? "prop4:" + random.nextInt(100) : null);
            obj.setProp5(random.nextInt(2) == 0 ? "prop5:" + random.nextInt(100) : null);
            obj.setProp6(random.nextInt(2) == 0 ? "prop6:" + random.nextInt(100) : null);
            obj.setProp7(random.nextInt(2) == 0 ? "prop7:" + random.nextInt(100) : null);
            obj.setProp8(random.nextInt(2) == 0 ? "prop8:" + random.nextInt(100) : null);
            obj.setProp9(random.nextInt(2) == 0 ? "prop9:" + random.nextInt(100) : null);
            obj.setProp10(random.nextInt(2) == 0 ? "prop10:" + random.nextInt(100) : null);
            obj.setProp11(random.nextInt(2) == 0 ? "prop11:" + random.nextInt(100) : null);
            obj.setProp12(random.nextInt(2) == 0 ? "prop12:" + random.nextInt(100) : null);
            obj.setProp13(random.nextInt(2) == 0 ? "prop13:" + random.nextInt(100) : null);
            obj.setProp14(random.nextInt(2) == 0 ? "prop14:" + random.nextInt(100) : null);
//            obj.setProp14( null);
            obj.setProp15(random.nextInt(2) == 0 ? "prop15:" + random.nextInt(100) : null);
            identityVos.add(obj);

        }
        return identityVos;

    }


}
