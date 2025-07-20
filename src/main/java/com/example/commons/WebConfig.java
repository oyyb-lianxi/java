package com.example.commons;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Administrator
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    /**
     * 解决跨域
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 指定需要跨域的路径
                .allowedOrigins("https://www.zhuoying.xin") // 允许的源
//                .allowedOrigins("*") // 允许的源
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "PUT", "DELETE","OPTIONS") // 允许的 HTTP 方法
                .allowedHeaders("Content-Type", "Authorization","X-Requested-With") // 允许的请求头
                .maxAge(3600);// 预检请求缓存时间（秒）
    }


    /**
     * 拦截请求
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenInterceptor())
//                .addPathPatterns("/*/studentAppointments") // 应用到所有路径
                .addPathPatterns("/appointments/studentAppointments")
//                .excludePathPatterns("/login/**","/file/**");// 排除某些路径
                 .excludePathPatterns("/login/**","/file/**");// 排除某些路径
    }


}
