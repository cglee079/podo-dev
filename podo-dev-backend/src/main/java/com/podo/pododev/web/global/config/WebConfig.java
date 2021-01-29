package com.podo.pododev.web.global.config;

import com.podo.pododev.web.global.config.filter.RequestLoggingFilter;
import com.podo.pododev.web.global.config.interceptor.BlogViewLogInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    @Value("${local.upload.base.path}")
    private String uploadedPath;

    @Value("${local.upload.base.dir}")
    private String uploadedLocation;

    private final BlogViewLogInterceptor blogViewLogInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("PUT, POST, GET, DELETE")
                .allowedOrigins("*");

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler(uploadedPath + "/**")
                .addResourceLocations("file:///" + uploadedLocation + "/");
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(blogViewLogInterceptor)
                .addPathPatterns("/api/blogs/*")
                .excludePathPatterns("/api/blogs/archive");
    }

    @Bean
    public FilterRegistrationBean getFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(new RequestLoggingFilter());
        registrationBean.setOrder(Integer.MIN_VALUE);
        registrationBean.setUrlPatterns(Arrays.asList("/api/*"));
        return registrationBean;
    }

}




