import com.runner99.util.IdentityVoUtil;
import com.runner99.vo.IdentityVo;
import com.runner99.vo.Tree;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author weizhenqiang
 * @date 2023/3/28 10:51
 */
public class TreeTest {

    @Test
    public void testTreeList() {
        //构建用户
        ArrayList<IdentityVo> identityVos = IdentityVoUtil.getIdentityVos(5);
        // TODO 需要对身份信息进行排序操作
        identityVos.stream().forEach(identityVo -> System.out.println(identityVo));

        long begin = System.currentTimeMillis();
        Tree.getTreeList(identityVos);
        long end = System.currentTimeMillis();
        System.out.println("创建" + identityVos.size() + "个身份共需要" + (end - begin) + "毫秒");


        //获取某一层的所有属性值
//        Set<String> prop1 = getLevelAttributes("prop1", identityVos);
//        prop1.stream().forEach(prop-> System.out.println(prop));

    }


    @Test
    public void test02() {
        HashMap<String, String> map = new HashMap<>();
        map.put("haha", "");
        Set<String> set = map.keySet();
        set.stream().forEach(s -> {
            String s1 = map.get(s);
            if (s1 == null) {

                map.put(s, "asdf");
            }
            map.put(s, s1 + 1);
            String s2 = map.get(s);
            map.put(s, s2 + 1);

            System.out.println(map.get(s));
        });
    }


    @Test
    public void test03() {
        Long[] m = {0L, 0L, 1L};
        Long[] n = {0L, 0L, 1L};
        long begin = System.nanoTime();
        Long[] twoLong = Tree.getTwoLong(m, n);
        long end = System.nanoTime();
        System.out.println("耗时：" + (end - begin) + "纳秒");
        System.out.println(Arrays.toString(twoLong));

    }


    @Test
    public void test04() {
        Long[] arr = {0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 1L, 0L, 312130L, 1L, 0L, 0L, 0L, 0L, 4210L, 0L, 0L, 0L, 0L, 0L, 1L};
        long begin = System.nanoTime();
        int listIndex = Tree.getListIndex(arr);
        long end = System.nanoTime();
        System.out.println(end - begin);
        System.out.println(listIndex);

    }

    @Test
    public void test05() {

        Long[] a = {0L,12L,22L};
        Long[] clone = a.clone();
        System.out.println(a.equals(clone));
        clone[2]=89L;
        System.out.println("数组"+Arrays.toString(a));
        System.out.println("数组"+Arrays.toString(clone));
    }


}
