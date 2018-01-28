package com.my.myconfigure;

import com.my.myfilter.SSOFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpRequest;
import org.springframework.web.servlet.DispatcherServlet;


/**
 * Created by Administrator on 2018/1/24.
 */
@Configuration
public class FilterConfigure {

    @Bean
    public FilterRegistrationBean filterDemo4Registration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        //注入过滤器
        registration.setFilter(new SSOFilter());
        //拦截规则
        registration.addUrlPatterns("*");
        //过滤器名称
        registration.setName("ssoFilter");
        //是否自动注册 false 取消Filter的自动注册
        registration.setEnabled(false);
        //过滤器顺序
        registration.setOrder(1);
        return registration;
    }
}
