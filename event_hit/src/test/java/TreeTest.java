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
    public void testTreeList(){
        //构建用户
        ArrayList<IdentityVo> identityVos = IdentityVoUtil.getIdentityVos(5);
        // TODO 需要对身份信息进行排序操作
        identityVos.stream().forEach(identityVo -> System.out.println(identityVo));

        long begin= System.currentTimeMillis();
        Tree.getTreeList(identityVos);
        long end = System.currentTimeMillis();
        System.out.println("创建"+identityVos.size()+"个身份共需要"+(end-begin)+"毫秒");



        //获取某一层的所有属性值
//        Set<String> prop1 = getLevelAttributes("prop1", identityVos);
//        prop1.stream().forEach(prop-> System.out.println(prop));

    }


    @Test
    public void test02(){
        HashMap<String, String> map = new HashMap<>();
        map.put("haha", "");
        Set<String> set = map.keySet();
        set.stream().forEach(s -> {
            String s1 = map.get(s);
            if (s1 == null) {

                map.put(s, "asdf");
            }
            map.put(s,s1+1);
            String s2 = map.get(s);
            map.put(s,s2+1);

            System.out.println(map.get(s));
        });
    }

    //获取某一层的所有属性值
    public Set<String> getLevelAttributes(String levelName , List<IdentityVo> IdentityVos){
        Set<String> set = new HashSet<>();
        IdentityVos.stream().forEach(identityVo -> {
            try {
                Field field = IdentityVo.class.getDeclaredField(levelName);
                field.setAccessible(true);
                String o = (String) field.get(identityVo);
                set.add(o);



            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return set;
    }


    @Test
    public void test03(){

        String sub = "1";
        for (int i=0;i<9999;i++){
                sub+=0;
        }
        System.out.println(sub.substring(99,163));
    }


}
