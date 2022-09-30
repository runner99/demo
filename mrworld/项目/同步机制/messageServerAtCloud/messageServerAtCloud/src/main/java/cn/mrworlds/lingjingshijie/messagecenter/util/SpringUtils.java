package cn.mrworlds.lingjingshijie.messagecenter.util;

import cn.mrworlds.lingjingshijie.messagecenter.Constant.Api;
import cn.mrworlds.lingjingshijie.messagecenter.server.MRTPServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Slf4j
@Component
public class SpringUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static final String RESPONSE_RESULT_SYMBOL = "RESPONSE_RESULT_SYMBOL";


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(SpringUtils.applicationContext == null) {
            SpringUtils.applicationContext = applicationContext;
        }

        log.info("==================================Spring 上下文 启动成功======================================");

        removeRoomResidue();

        new Thread() {
            @Override
            public void run() {
                new MRTPServer(10010).start();
            }}.start();
        log.info("==================================网关服务启动成功 上下文 启动成功======================================");
    }

    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    //通过name获取 Bean.
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }

    public static void removeRoomResidue(){
        HttpUtil.sendPostForm(Api.getRemove_room_residue(),new HashMap<>());
    }


}
