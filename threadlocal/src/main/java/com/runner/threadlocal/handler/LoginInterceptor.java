package com.runner.threadlocal.handler;

import com.runner.threadlocal.common.UserEntity;
import com.runner.threadlocal.utils.ThreadLocalUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getHeader("token")!=null) {
            String token = request.getHeader("token");
            String[] split = token.split(",");

            UserEntity userEntity = new UserEntity();
            userEntity.setId(Integer.parseInt(split[0]));
            userEntity.setName(split[1]);
            ThreadLocalUtil.setUserEntity(userEntity);
            return true;
        }
        System.out.println("用户未登录");
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //如果不删除会有内存泄露的风险
        System.out.println(ThreadLocalUtil.get()+"准备删除");
        ThreadLocalUtil.clear();
        System.out.println(ThreadLocalUtil.get()+"已删除");
    }
}
