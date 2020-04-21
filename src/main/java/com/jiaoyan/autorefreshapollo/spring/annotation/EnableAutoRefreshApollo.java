package com.jiaoyan.autorefreshapollo.spring.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author xujian03
 * @date 2020/4/16 11:05
 * @description 开启自动添加listener，用于自动刷新@ConfigurationProperties配置，作用于启动类
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(AutoRefreshApolloRegistrar.class)
public @interface EnableAutoRefreshApollo {
}
