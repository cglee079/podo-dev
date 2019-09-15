package com.cglee079.pododev.web.global.config;

import com.cglee079.pododev.web.global.interceptor.RequestLogInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    @Value("${local.static.url}")
    private String sitemapUrl;

    @Value("${local.static.dir}")
    private String sitemapLocation;


    @Value("${local.upload.base.url}")
    private String uploadedUrl;

    @Value("${local.upload.base.dir}")
    private String uploadedLocation;

    private final RequestLogInterceptor requestLogInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("PUT, POST, GET, DELETE")
                .allowedOrigins("*");

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler(uploadedUrl + "/**")
                .addResourceLocations("file:///" + uploadedLocation + "/");

        registry
                .addResourceHandler(sitemapUrl + "/**")
                .addResourceLocations("file:///" + sitemapLocation + "/");
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestLogInterceptor)
                .addPathPatterns("/**");
    }

}




