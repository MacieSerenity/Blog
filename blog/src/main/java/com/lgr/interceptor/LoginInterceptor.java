package com.lgr.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: MacieSerenity
 * Date: 2020/05/08 13:20
 * Description:
 * Version: V1.0
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
//    请求预处理
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        if (request.getSession().getAttribute("user")==null){
            response.sendRedirect("/admin");
            return false;
        }
        return true;
    }
}
