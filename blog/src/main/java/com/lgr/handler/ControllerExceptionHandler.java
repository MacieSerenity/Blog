package com.lgr.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: MacieSerenity
 * Date: 2020/04/12 17:09
 * Description:
 * Version: V1.0
 */
/*
* controllerAdvice是Controller拦截器
* */
@ControllerAdvice
public class ControllerExceptionHandler {
    //    初始化一个日志
    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHander(HttpServletRequest request,Exception e) throws Exception {

        logger.error("Request URL : {}, Exception : {}",request.getRequestURI(),request,e);

//        如果异常有识别码，例如404或者500，就不用这个类处理，交给springboot处理
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class)!=null){
            throw e;
        }


        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("url:",request.getRequestURL());
        modelAndView.addObject("exception:",e);
        modelAndView.setViewName("error/error");
        return modelAndView;
    }

}
