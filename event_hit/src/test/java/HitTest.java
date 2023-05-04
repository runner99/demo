import com.runner99.util.EventUtil;
import com.runner99.util.IdentityVoUtil;
import com.runner99.vo.*;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author weizhenqiang
 * @date 2023/3/22 14:29
 */
public class HitTest {

    private Long[] result;

    @Test
    public void test01() {

        //获取无序的身份列表
        ArrayList<IdentityVo> identityVos = IdentityVoUtil.getIdentityVos(10000);

        IdentityVo identityVo = new IdentityVo();
        identityVo.setProp1("prop1:36");
        identityVo.setProp2("prop2:36");
        identityVo.setProp3("prop3:36");
        identityVo.setProp4("prop4:36");
        identityVo.setProp5("prop5:36");
        identityVo.setProp6("prop6:36");
        identityVo.setProp7("prop7:36");
        identityVo.setProp8("prop8:36");
        identityVo.setProp9("prop9:36");
        identityVo.setProp10("prop10:36");
        identityVo.setProp11("prop11:36");
        identityVo.setProp12("prop12:36");
        identityVo.setProp13("prop13:36");
        identityVo.setProp14("prop14:36");
        identityVo.setProp15("prop15:36");
        identityVos.add(identityVo);


        //对身份列表进行排序
        ArrayList<IdentityVo> sortIdentityVos = sortIdentityVo(identityVos);

        sortIdentityVos.stream().forEach(sortIdentityVo -> System.out.println(sortIdentityVo));

        //生成树
        long m = System.currentTimeMillis();
        ArrayList<Level> treeList = Tree.getTreeList(sortIdentityVos);
        ArrayList<Level> treeClone = (ArrayList<Level>) treeList.clone();
        long n = System.currentTimeMillis();
        System.out.println("构建树耗时：" + (n - m) / 1000 + "秒");

        HashMap<String, String> eventMap = new HashMap<>();
        eventMap.put("prop1", "prop1:36");
        eventMap.put("prop2", "prop2:36");
        eventMap.put("prop3", "prop3:36");
        eventMap.put("prop4", "prop4:36");
        eventMap.put("prop5", "prop5:36");
        eventMap.put("prop6", "prop6:36");
        eventMap.put("prop7", "prop7:36");
        eventMap.put("prop8", "prop8:36");
        eventMap.put("prop9", "prop9:36");
        eventMap.put("prop10", "prop10:36");
        eventMap.put("prop11", "prop11:36");
        eventMap.put("prop12", "prop12:36");
        eventMap.put("prop13", "prop13:36");
        eventMap.put("prop14", "prop14:36");
        eventMap.put("prop15", "prop15:36");
        eventMap.put("prop16", "prop16:36");
        eventMap.put("prop17", "prop17:36");
        eventMap.put("prop18", "prop18:36");
        eventMap.put("prop19", "prop19:36");
        eventMap.put("prop20", "prop20:36");


//        IdentityVo hitIdentityVo = Tree.getHitIdentityVo(eventMap, treeList, sortIdentityVos);
//        if (hitIdentityVo != null) {
//            System.out.println("事件命中");
//            System.out.println(hitIdentityVo);
//        }


        long eventBegin = System.currentTimeMillis();
        List<Map<String, String>> events = EventUtil.getEvents(100000);
        long eventEnd = System.currentTimeMillis();
        System.out.println("构建事件花费："+(eventEnd-eventBegin)+"毫秒");
        events.add(eventMap);

        printTree(treeList);


        long begin = System.nanoTime();
        for(int i=0;i<events.size();i++){
            IdentityVo hitIdentityVo = Tree.getHitIdentityVo(events.get(i), treeList, sortIdentityVos);
            if (hitIdentityVo!=null){
                System.out.println(events.get(i));
                System.out.println(hitIdentityVo);
            }
        }
        printTree(treeList);
        long end = System.nanoTime();
        long diff = end-begin;
        System.out.println(events.size()+"个事件总耗时："+diff+"纳秒,平均单次耗时："+diff/events.size()+"纳秒");

    }


    //获取排完序后的身份集合
    public static ArrayList<IdentityVo> sortIdentityVo(List<IdentityVo> list) {

        ArrayList<IdentityVoSort> sortList = new ArrayList<>();

        list.stream().forEach(identityVo -> {
            IdentityVoSort identityVoSort = new IdentityVoSort();

            //TODO 获取当前identityVo的权重值
            identityVoSort.setNumber(getIdentityVoWeight(identityVo));
            identityVoSort.setIdentityVo(identityVo);
            sortList.add(identityVoSort);

        });
        Collections.sort(sortList, new Comparator<IdentityVoSort>() {
            @Override
            public int compare(IdentityVoSort o1, IdentityVoSort o2) {

                return o2.getNumber() - o1.getNumber();

            }
        });

        ArrayList<IdentityVo> result = new ArrayList<>();
        sortList.stream().forEach(identityVoSort -> {
            result.add(identityVoSort.getIdentityVo());
        });

        return result;

    }


    // TODO 获取IdentityVo的权重值
    public static int getIdentityVoWeight(IdentityVo identityVo) {
        AtomicInteger result = new AtomicInteger();

        Field[] declaredFields = identityVo.getClass().getDeclaredFields();
        Arrays.stream(declaredFields).forEach(field -> {
            try {
                Field attr = IdentityVo.class.getDeclaredField(field.getName());
                attr.setAccessible(true);
                if (attr.get(identityVo) != null) {
                    result.addAndGet(Integer.parseInt(field.getName().substring(4)));
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

//        identityVo.getClass().getDeclaredField();
        return result.get();
    }


    @Test
    public void test02() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("haha" + i);
        }

        long begin1 = System.nanoTime();
        //for循环
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
        long end1 = System.nanoTime();

        long begin2 = System.nanoTime();
        //stream流
        list.stream().forEach(s -> {
            System.out.println(s);
        });
        long end2 = System.nanoTime();


        System.out.println("for循环消耗   时间为" + (end1 - begin1) + "纳秒");
        System.out.println("stream循环消耗时间为" + (end2 - begin2) + "纳秒");

    }

    @Test
    public void test03() {
        Long[] m = {0L, 5L};
        long begin = System.nanoTime();
        int listIndex = Tree.getListIndex(m);
        long end = System.nanoTime();
        System.out.println(listIndex);
//        System.out.println("耗时"+(end-begin));
    }


    @Test
    public void test04() {
        HashMap<String, String> map1 = new HashMap<>();
        HashMap<String, String> map2 = new HashMap<>();
        HashSet<Map<String,String>> sets = new HashSet<>();
        map1.put("a", "b");
        map2.put("a", "b");

        sets.add(map2);
        sets.add(map1);
        System.out.println(sets.size());

    }

    public static void printTree(List<Level> list){
//        list.stream().forEach(lev->{
//
//        });
        StringBuffer stringBuffer = new StringBuffer();
        for (int i=0;i<list.size();i++){
            stringBuffer.append(list.get(i).hashCode());
        }
        System.out.println("树"+stringBuffer);
    }


}
