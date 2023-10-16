package com.runner.optional;

import com.alibaba.fastjson.JSONObject;
import com.runner.pojo.User;
import org.junit.Test;

import java.util.Optional;

/**
 * @author weizhenqiang
 * @date 2023/4/19 15:31
 */
public class OptionalTest {
    @Test
    public void test01() {
        User user = new User(11, "jj");
        User user1 = Optional.ofNullable(user).orElseGet(() -> {
            return new User(1, "gg");
        });
        Integer integer = Optional.ofNullable(user).map(obj -> obj.getId()).orElse(1);

        System.out.println(user1);
    }

    @Test
    public void test02() {
        JSONObject jsonObject = JSONObject.parseObject("{\n" +
                "    \"code\":200,\n" +
                "    \"message\":\"jkl\",\n" +
                "    \"data\":{\n" +
                "        \"records\":\n" +
                "        [\n" +
                "            {\n" +
                "            \"id\":1,\n" +
                "            \"name\":\"haha1\"\n" +
                "            },\n" +
                "            {\n" +
                "            \"id\":2,\n" +
                "            \"name\":\"haha2\"\n" +
                "            },\n" +
                "                        {\n" +
                "            \"id\":3,\n" +
                "            \"name\":\"haha3\"\n" +
                "            },\n" +
                "                        {\n" +
                "            \"id\":4,\n" +
                "            \"name\":\"haha4\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}");

        Optional.ofNullable(jsonObject).map(obj->obj.getJSONObject("data")).map(obj->obj.getJSONArray("records")).ifPresent(array->{
            array.stream().forEach(obj->{
                User user = JSONObject.parseObject(obj.toString(), User.class);
                System.out.println(user);
            });
        });


    }

    @Test
    public void test03() {
        String str = null;
        int length = Optional.ofNullable(str).orElse("").length();
        System.out.println(length);


    }
}
