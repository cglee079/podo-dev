package com.cglee079.pododev.web.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Iterator;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${upload.base.url}")
    private String uploadedUrl;

    @Value("${upload.base.dir}")
    private String uploadedLocation;

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
    }

//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        Iterator<HttpMessageConverter<?>> converterIterator = converters.iterator();
//        while (converterIterator.hasNext()) {
//            // Do NOT add new one, MUST replace
//            HttpMessageConverter converter = converterIterator.next();
//            if (converter.getSupportedMediaTypes().contains(MediaType.APPLICATION_JSON))
//                converterIterator.remove();
//        }
//
//        converters.add(htmlEscapingConverter());
//
//    }
//
//    private HttpMessageConverter<?> htmlEscapingConverter() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.getFactory().setCharacterEscapes(new HTMLCharacterEscapes());
//        MappingJackson2HttpMessageConverter htmlEscapingConverter =
//                new MappingJackson2HttpMessageConverter();
//        htmlEscapingConverter.setObjectMapper(objectMapper);
//
//        return htmlEscapingConverter;
//    }
}




