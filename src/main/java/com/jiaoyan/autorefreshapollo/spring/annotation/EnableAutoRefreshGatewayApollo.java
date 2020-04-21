package com.jiaoyan.autorefreshapollo.spring.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xujian03
 * @date 2020/4/16 11:05
 * @description 开启自动添加listener，用于SpringCloud API Gateway自动刷新路由配置，作用于启动类
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(AutoRefreshApolloRegistrar.class)
public @interface EnableAutoRefreshGatewayApollo {
}
