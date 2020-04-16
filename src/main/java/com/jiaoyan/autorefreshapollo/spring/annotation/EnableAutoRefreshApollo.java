package com.jiaoyan.autorefreshapollo.spring.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author xujian03
 * @date 2020/4/16 11:05
 * @description
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(AutoRefreshApolloRegistrar.class)
public @interface EnableAutoRefreshApollo {
}
