package com.pjpy.studentManager.config;

import com.pjpy.studentManager.interceptors.LoginInterceptor;
import com.sun.javafx.iio.common.SmoothMinifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @Classname SpringmvcConfig
 * @Description 扩展springmvc功能
 */
@Component
public class SpringmvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("/login");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //System.out.println(System.getProperty("user.dir"));
        String path = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\upload\\imgs\\";
        registry.addResourceHandler("/upload/imgs/**").addResourceLocations("file:"+path);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns("/","/system/login","/easyui/**","/h-ui/**","/upload/**");
    }
}
