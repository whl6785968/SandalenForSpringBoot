package com.sandalen.sandalen.config;

import com.sandalen.sandalen.component.Loginintercepter;
import com.sandalen.sandalen.component.MyLocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//扩展spring boot  即如果没有配一些东西，就使用spring boot默认的，如果有就使用自定义的
@Configuration
public class MyConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index").setViewName("index");
    }

    //将自己的区域解析器放入容器中
    @Bean
    public LocaleResolver localeResolver(){
        System.out.println("我执行了");
        LocaleResolver locale = new MyLocaleResolver();
        return locale;
    }

   /* @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        WebMvcConfigurer webMvcConfigurer = new WebMvcConfigurer(){
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new Loginintercepter()).addPathPatterns("/**").excludePathPatterns("/index","/","/user/login","/static/**");
            }
        };
        return webMvcConfigurer;
    }*/
}
