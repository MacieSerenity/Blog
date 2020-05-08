package com.lgr.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: MacieSerenity
 * Date: 2020/04/13 10:53
 * Description:
 * Version: V1.0
 */
@Aspect
@Component
public class LogAspect {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());

//    拦截所有私有和公有的类 来源于com.lgr.blog下的所有类的所有方法（...）带参数的
//    @Pointcut("execution(* com.lgr.web.*.*(..))")
    @Pointcut(value = "execution(* com.lgr.web.*.*(..))")
    public void login(){
        System.out.println("拦截");
    }

    @Before("login()")
    public void doBefore(JoinPoint joinPoint){
//        获得Servlet请求
        ServletRequestAttributes servletRequestAttributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        得到request请求
        HttpServletRequest httpServletRequest=servletRequestAttributes.getRequest();
//        将请求中的URL和IP存起来
        String url=httpServletRequest.getRequestURI();
        String ip=httpServletRequest.getRemoteAddr();
//        通过传递进来的joinpoint对象的方法获取到使用的方法名
//        getSignature().getDeclaringTypeName() 获取类名
//        joinPoint.getSignature().getName() 获取方法
        String classMethod=joinPoint.getSignature().getDeclaringTypeName()
                +"."+joinPoint.getSignature().getName();
//        获取请求的参数
        Object[] args=joinPoint.getArgs();
//        放入RequestLog对象
        RequestLog requestLog=new RequestLog(url,ip,classMethod,args);
//        记录请求
        logger.info("Request:{}",requestLog);
    }

    @After("login()")
    public void doAfter(){
        logger.info("----------doAfter----------");
    }

    @AfterReturning(returning = "result",pointcut = "login()")
    public void doAfterReturn(Object result){
        logger.info("Result : {}"+result);
    }

    private class RequestLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
