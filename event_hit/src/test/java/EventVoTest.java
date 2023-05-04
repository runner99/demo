import com.runner99.vo.EventVo;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author weizhenqiang
 * @date 2023/3/31 11:51
 */
public class EventVoTest {

    @Test
    public void test01() {
        EventVo eventVo = new EventVo();

        long begin = System.currentTimeMillis();

        Field[] declaredFields = eventVo.getClass().getDeclaredFields();
        List<String> list = fieldsToString(declaredFields);
        list.stream().forEach(attribute -> {
//            System.out.println(attribute);
        });

        long end = System.currentTimeMillis();
        System.out.println(end - begin);

    }

    @Test
    public void test02() {
        HashMap<String, String> map = new HashMap<>();
        String put = map.put("a1", "a2");
        long begin = System.nanoTime();

        map.get("a1");
        long end = System.nanoTime();
        System.out.println(end - begin);
    }

    @Test
    public void test03() {
        Long a = 123241234142351L;
        Long b = 14321435132512352L;
        Long[] longs = new Long[15];
        for (int i = 0; i < longs.length; i++) {
            int m = i * 1324123 + 123;
            longs[i] = (long) m;
        }

        Long result = -1L;


        HashMap<String, String> map = new HashMap<>();
        long begin = System.nanoTime();
        for (int i = 0; i < longs.length; i++) {
            result &= longs[i];
            map.get("key");
        }
        long end = System.nanoTime();

        System.out.println(end - begin);
    }


    public static List<String> fieldsToString(Field[] fields) {
        ArrayList<String> list = new ArrayList<>();
        for (Field field : fields) {
            list.add(field.getName());
        }

        return list;
    }
}
