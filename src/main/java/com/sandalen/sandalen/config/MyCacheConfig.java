package com.sandalen.sandalen.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

@Configuration
public class MyCacheConfig {
    @Bean
    public KeyGenerator keyGenerator(){
        return new KeyGenerator(){
          public Object generate(Object target, Method method,Object... params){
              return method.getName();
          }
        };
    }
}
