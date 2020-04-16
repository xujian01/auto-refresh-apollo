package com.jiaoyan.autorefreshapollo.spring.boot;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.jiaoyan.autorefreshapollo.spring.annotation.AutoRefreshApollo;
import com.jiaoyan.autorefreshapollo.model.GlobeNamespace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author xujian03
 * @date 2020/4/9 15:28
 * @description 基于AutoRefreshApolloProperties和ConfigurationProperties注解的自动配置刷新
 */
@Slf4j
public class ConfigurationPropertiesListenerRegistry implements ApplicationEventPublisherAware, ApplicationContextAware {
    private ApplicationEventPublisher publisher;
    private ApplicationContext applicationContext;
    //指定的环境
    @Value("${spring.profiles.active:}")
    private String env;
    @Autowired
    private GlobeNamespace globeNamespace;

    @PostConstruct
    public void init() {
        Map<String,Object> beanMap = applicationContext.getBeansWithAnnotation(AutoRefreshApollo.class);
        for (Object bean : beanMap.values()) {
            Set<String> prefixSet = new HashSet<>();
            Class clazz = bean.getClass();
            ConfigurationProperties configurationProperties = (ConfigurationProperties) clazz.getAnnotation(ConfigurationProperties.class);
            if (configurationProperties == null) continue;
            String prefix = configurationProperties.value();
            if (StringUtils.isEmpty(prefix)) prefix = configurationProperties.prefix();
            if (!StringUtils.isEmpty(prefix)) prefix+=".";
            prefixSet.add(prefix);

            AutoRefreshApollo clazzAnnotation = (AutoRefreshApollo) clazz.getAnnotation(AutoRefreshApollo.class);
            String namespacePrefix = clazzAnnotation.value();
            if (!StringUtils.isEmpty(env)) namespacePrefix+="-";

            String namespaceSuffix = clazzAnnotation.namespaceSuffix();
            Config config = ConfigService.getConfig(namespacePrefix+env+"."+namespaceSuffix);
            //该方法有三个参数，第一个参数是具体的listener，第二个参数是感兴趣的key，第三个参数是感兴趣的key的前缀
            log.info("----------添加Apollo配置监听器-----------");
            config.addChangeListener(changeEvent -> {
                log.info("----------刷新配置-----------START");
                //重新绑定配置
                publisher.publishEvent(new EnvironmentChangeEvent(changeEvent.changedKeys()));
                //刷新配置bean
                publisher.publishEvent(new RefreshRoutesEvent(bean));
                log.info("----------刷新配置-----------END");
            },null,prefixSet);
        }
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
