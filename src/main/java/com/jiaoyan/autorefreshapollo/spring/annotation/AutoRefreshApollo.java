package com.jiaoyan.autorefreshapollo.spring.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author xujian03
 * @date 2020/4/15 10:44
 * @description 自动刷新@ConfigurationProperties的配置
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoRefreshApollo {
    /**
     * apollo namespace的前缀，如application.yml的application部分，如果不设置则自动寻找项目的全局namespace
     * @return
     */
    String value();

    /**
     * apollo namespace的前缀，如application.yml的.yml部分，如果不设置则自动寻找项目的全局namespace
     * @return
     */
    String namespaceSuffix() default "yml";
}
