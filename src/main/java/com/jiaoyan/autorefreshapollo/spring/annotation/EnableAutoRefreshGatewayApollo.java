package com.jiaoyan.autorefreshapollo.spring.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xujian03
 * @date 2020/4/16 11:05
 * @description
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(AutoRefreshApolloRegistrar.class)
public @interface EnableAutoRefreshGatewayApollo {
}
