package com.example.common;

//import com.example.Utils.JWTInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 *
 * 前端webURL的设置
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

//    @Resource
//    JWTInterceptor interceptor;

    @Override
    public void configurePathMatch(PathMatchConfigurer pathMatchConfigurer) {
//    指定controller统一的接口前缀
        pathMatchConfigurer.addPathPrefix("/api", clazz -> clazz.isAnnotationPresent(RestController.class));
    }

    //    加自定义拦截器
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(interceptor)
//                .addPathPatterns("/api/**")
//                .excludePathPatterns("/api/admin/login")
//                .excludePathPatterns("/api/admin/register");
//    }

}
