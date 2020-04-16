package com.jiaoyan.autorefreshapollo.spring.boot;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.jiaoyan.autorefreshapollo.model.GlobeNamespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;
/**
 * @author xujian03
 * @date 2020/4/9 15:28
 * @description spring cloud API Gateway路由配置自动刷新
 */
public class GatewayListenerRegistry implements ApplicationEventPublisherAware {
    @Autowired
    private GatewayProperties gatewayProperties;
    private ApplicationEventPublisher publisher;
    //指定的环境
    @Value("${spring.profiles.active:}")
    private String env;
    @Autowired
    private GlobeNamespace globeNamespace;

    @PostConstruct
    public void init() {
        Config config = ConfigService.getConfig(globeNamespace.getNamespacePrefix()+env+globeNamespace.getNamespaceSuffix());
        Set<String> prefix = new HashSet<>();
        prefix.add("spring.cloud.gateway.");
        //该方法有三个参数，第一个参数是具体的listener，第二个参数是感兴趣的key，第三个参数是感兴趣的key的前缀
        config.addChangeListener(changeEvent -> {
            //重新绑定配置
            publisher.publishEvent(new EnvironmentChangeEvent(changeEvent.changedKeys()));
            //刷新配置bean
            publisher.publishEvent(new RefreshRoutesEvent(gatewayProperties));
        },null,prefix);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
