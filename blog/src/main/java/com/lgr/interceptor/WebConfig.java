package com.lgr.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: MacieSerenity
 * Date: 2020/05/08 13:24
 * Description:
 * Version: V1.0
 */
//public class WebConfig implements WebMvcConfigurerAdapter {//spring 5.0+ 被丢弃
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/*")
                .excludePathPatterns("/admin")
                .excludePathPatterns("/admin/login");
    }
}
