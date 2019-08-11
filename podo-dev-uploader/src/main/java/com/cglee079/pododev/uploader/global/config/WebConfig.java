package com.cglee079.pododev.uploader.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${uploader.frontend.subpath}")
    private String frontendSubpath;

    @Value("${uploader.upload.base.dir}")
    protected String uploadedLocation;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*");
    }


    /*
     * static Resource 핸들링,
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler(frontendSubpath + "/**")
                .addResourceLocations("file:///" + uploadedLocation + "/");
    }


}

