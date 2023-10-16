package com.runner.stream;

import com.runner.pojo.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;


/**
 * stream流学习
 */
public class StreamTest {

    //集合中长度以及筛选遍历
    @Test
    public void test01() {
        List<String> list = new ArrayList<>();
        list.add("张无忌");
        list.add("周芷若");
        list.add("赵敏");
        list.add("小昭");
        list.add("殷离");
        list.add("张三");
        list.add("张三丰");
        //返回的是long，为集合的长度
        System.out.println(list.stream().count());
        list.stream()
                .filter(name -> name.startsWith("张") && name.length() == 3)
                //name或者max不影响结果
//                .filter(max -> max.length() == 3)
                .forEach(haha -> System.out.println(haha));
    }

    //顺序流与并行流的区别
    @Test
    public void test02() {
        List<String> list = new ArrayList<>();
        list.add("张无忌");
        list.add("周芷若");
        list.add("赵敏");
        list.add("小昭");
        list.add("殷离");
        list.add("张三");
        list.add("张三丰");

        //stream为顺序流，输出与输入顺序一致
        list.stream().forEach(name -> System.out.println(name));
        System.out.println("-----------------");
        System.out.println("-----------------");
        //stream为并行流，输出与输入顺序不一致
        list.parallelStream().forEach(name -> System.out.println(name));
    }

    //结合实体类进行筛选遍历
    @Test
    public void test03() {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new User(i, "渣渣辉" + i + "号"));
        }
        ArrayList<User> users = new ArrayList<>();
//        list.stream()
//                .filter(user -> user.getName().equals("渣渣辉8号") && user.getId() == 8)
////                .filter(user -> user.getName().startsWith("渣渣"))
//                .forEach(user -> users.add(user));
        //并行流输出
//        list.parallelStream().forEach(System.out::println);
        List<User> collect = list.stream().filter(user -> {
            return user.getId() == 5?false:true;
        }).collect(Collectors.toList());
        System.out.println(collect.toString());
    }


    //stream结合Predicate表达式返回集合
    //注意:Predicate完全可以由原始的方法替代，这样可以少一步，详见test01
    @Test
    public void test04() {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new User(i, "渣渣辉" + i + "号"));
        }
        //表示并且的关系
        Predicate<User> predicate01 = user -> user.getId() > 3;
        Predicate<User> predicate02 = user -> user.getId() < 6;
        List<User> collect = list.stream()
                .filter(predicate01)
                .filter(predicate02)
                .collect(Collectors.toList());
        System.out.println(collect);

        //表示或者的关系
        Predicate<User> predicate03 = user -> user.getId() < 3;
        Predicate<User> predicate04 = user -> user.getId() > 6;
        List<User> collect1 = list.stream()
                .filter(predicate03.or(user -> user.getId() > 6))
                .collect(Collectors.toList());
        System.out.println(collect1);
    }

    //limit的限制数量的使用
    @Test
    public void test05() {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new User(i, "渣渣辉" + i + "号"));
        }
//        System.out.println(list.stream().max((num1, num2) -> Integer.compare(num1.getId(), num2.getId())).get());
        List<User> collect = list.stream().limit(3).collect(Collectors.toList());
        System.out.println(collect);
    }

    //sorted排序，默认为升序，加入参数Comparator.reverseOrder()为降序
    @Test
    public void test06() {
        List<Integer> list = Arrays.asList(1, 7, 3, 66, -11, 7, 8, 32, 2, 2);
        //默认升序排序
//        list.stream().sorted().forEach(str-> System.out.println(str));
        //降序排序
//        list.stream().sorted(Comparator.reverseOrder()).forEach(str-> System.out.println(str));
        //选出最大值
        System.out.println(list.stream().max(Integer::compareTo).get());
        //选出最小值
        System.out.println(list.stream().min(Integer::compareTo).get());
        //求出长度
        System.out.println(list.stream().count());
    }


    //map对集合中的元素进行特定的操作，返回可以多个值
    //reduce按照出入的逻辑进行出来，返回的是一个值
    @Test
    public void test07() {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new User(i, "渣渣辉" + i + "号"));
        }

        List<Integer> collect = list.stream().map(user -> user.getId()).collect(Collectors.toList());
        collect.stream().forEach(name-> System.out.println(name));



//        list.stream()
//                .map(user -> user.getName())
//                .forEach(System.out::println);

//        List<Integer> list2 = Arrays.asList(1, 7, 3, 66, -11, 7, 8, 32, 2, 2);
//        System.out.println(list2.stream().reduce((num, sum) -> num + sum).get());
    }

    //map表示映射（收集对象的id），filter表示过滤（去除对象id小于5的）
    @Test
    public void test08() {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new User(i, "渣渣辉" + i + "号"));
        }
        List<User> users = list.stream().filter(user -> user.getName().equals("渣渣辉3号")).collect(Collectors.toList());
        List<Integer> collect = list.stream().map(user -> user.getId()).filter(user -> user > 5).collect(Collectors.toList());
        Optional<Integer> max = collect.stream().max(Integer::compareTo);
        System.out.println(max.get() == 9);

    }

}
