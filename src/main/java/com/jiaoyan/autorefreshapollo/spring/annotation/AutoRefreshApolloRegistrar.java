package com.jiaoyan.autorefreshapollo.spring.annotation;

import com.jiaoyan.autorefreshapollo.spring.boot.ConfigurationPropertiesListenerRegistry;
import com.jiaoyan.autorefreshapollo.spring.boot.GatewayListenerRegistry;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Objects;

/**
 * @author xujian03
 * @date 2020/4/16 10:36
 * @description 注册bean
 */
public class AutoRefreshApolloRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        //加了@EnableAutoRefreshApollo注解才会注册ConfigurationPropertiesListenerRegistry
        if (importingClassMetadata.hasAnnotation(EnableAutoRefreshApollo.class.getName())) {
            registerBeanDefinitionIfNotExists(registry, ConfigurationPropertiesListenerRegistry.class.getName(),ConfigurationPropertiesListenerRegistry.class);
        }
        //加了@EnableAutoRefreshGatewayApollo注解才会注册GatewayListenerRegistry
        if (importingClassMetadata.hasAnnotation(EnableAutoRefreshGatewayApollo.class.getName())) {
            registerBeanDefinitionIfNotExists(registry, GatewayListenerRegistry.class.getName(),GatewayListenerRegistry.class);
        }
    }
    private boolean registerBeanDefinitionIfNotExists(BeanDefinitionRegistry registry, String beanName,
                                                      Class<?> beanClass) {
        if (registry.containsBeanDefinition(beanName)) return false;

        String[] candidates = registry.getBeanDefinitionNames();
        for (String candidate : candidates) {
            BeanDefinition beanDefinition = registry.getBeanDefinition(candidate);
            if (Objects.equals(beanDefinition.getBeanClassName(), beanClass.getName())) {
                return false;
            }
        }

        BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(beanClass).getBeanDefinition();
        registry.registerBeanDefinition(beanName, beanDefinition);

        return true;
    }
}
