import com.runner99.util.EventBo;
import org.junit.Test;

import java.util.*;

/**
 * @author weizhenqiang
 * @date 2023/3/22 14:29
 */
public class HitTest {

    @Test
    public void test01(){

        EventBo event = new EventBo();
        Set<String> keys =
                event.getEVENT_MAP().keySet();

        keys.stream().forEach(key->{
            System.out.println(key+":"+event.getEVENT_MAP().get(key));
        });



    }

    @Test
    public void test02(){
        Set<String> event = new HashSet<>();
        for (int i=1;i<100;i++){
            event.add("value"+i);
        }

        Set<String> info = new HashSet<>();
        for (int i=1;i<10;i++){
            info.add("value"+i);
        }
        info.add("value"+22);

        System.out.println(event.containsAll(info));


    }


    @Test
    public void test03(){
//        List<String> event = new ArrayList<>();
//        for (int i=1;i<100;i++){
//            event.add("value"+i);
//        }
//
//        List<String> info = new ArrayList<>();
//        for (int i=1;i<10;i++){
//            info.add("value"+i);
//        }
//        info.add("value"+22);
//
//        System.out.println(event.containsAll(info));




    }
}
