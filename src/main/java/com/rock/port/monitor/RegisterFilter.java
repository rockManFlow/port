package com.rock.port.monitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 注册拦截器
 * Created by caoqingyuan on 2017/10/19.
 */
@Configuration
public class RegisterFilter extends WebMvcConfigurerAdapter {
    @Autowired(required = false)
    private BlogFilter blogFilter;
    /**
     * 配置拦截规则
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(blogFilter)
                .excludePathPatterns("/admin/login.htm"); //登录
    }
}
