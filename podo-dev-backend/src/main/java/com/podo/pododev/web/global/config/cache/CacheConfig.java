package com.podo.pododev.web.global.config.cache;


import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("deploy")
@EnableCaching
@Configuration
public class CacheConfig {
}
