package com.example.Utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    @Value("${user.dir}/uploads")
    private String uploadDir;

    @PostConstruct
    public void init() {
        // 确保上传目录存在
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        System.out.println("上传目录路径: " + dir.getAbsolutePath());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 添加外部文件访问映射
        String pathPattern = "/images/**";
        String location = "file:" + uploadDir + "/";

        System.out.println("配置资源映射 - 路径模式: " + pathPattern);
        System.out.println("配置资源映射 - 实际位置: " + location);

        registry.addResourceHandler(pathPattern)
                .addResourceLocations(location)
                .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));

        // 添加默认的静态资源映射
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}
