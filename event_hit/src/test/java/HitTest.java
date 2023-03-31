import com.runner99.util.IdentityVoUtil;
import com.runner99.vo.EventVo;
import com.runner99.vo.IdentityVo;
import com.runner99.vo.Level;
import com.runner99.vo.Tree;
import org.junit.Test;

import java.math.BigInteger;
import java.util.*;

/**
 * @author weizhenqiang
 * @date 2023/3/22 14:29
 */
public class HitTest {
    @Test
    public void test01() {

        ArrayList<IdentityVo> identityVos = IdentityVoUtil.getIdentityVos(100);
        List<Level> treeList = Tree.getTreeList(identityVos);
        EventVo eventVo = new EventVo();

        eventVo.setProp1("prop1=prop1:36");
        eventVo.setProp2("prop2=prop2:36");
        eventVo.setProp3("prop3=prop3:36");
        eventVo.setProp4("prop4=prop4:36");
        eventVo.setProp5("prop5=prop5:36");
        eventVo.setProp6("prop6=prop6:36");
        eventVo.setProp7("prop7=prop7:36");
        eventVo.setProp8("prop8=prop8:36");
        eventVo.setProp9("prop9=prop9:36");
        eventVo.setProp10("prop10=prop10:36");
        eventVo.setProp11("prop11=prop11:36");
        eventVo.setProp12("prop12=prop12:36");
        eventVo.setProp13("prop13=prop13:36");
        eventVo.setProp14("prop14=prop14:36");
        eventVo.setProp15("prop15=prop15:36");
        eventVo.setProp16("prop16=prop16:36");
        eventVo.setProp17("prop17=prop17:36");
        eventVo.setProp18("prop18=prop18:36");
        eventVo.setProp19("prop19=prop19:36");
        eventVo.setProp20("prop20=prop20:36");

        Map<String, String> attributeMap = eventVo.getAttributeMap();
        Set<String> attributes = attributeMap.keySet();
        attributes.stream().forEach(attribute->{
            System.out.println(attribute+"---->"+attributeMap.get(attribute));
        });



    }

}
