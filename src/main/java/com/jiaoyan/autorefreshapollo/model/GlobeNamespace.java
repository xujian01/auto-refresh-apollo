package com.jiaoyan.autorefreshapollo.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author xujian03
 * @date 2020/4/15 19:56
 * @description 全局namespace配置
 */
@Component
@ConfigurationProperties("globe.namespace")
@Data
public class GlobeNamespace {
    /**
     * namespace前缀
     */
    private String namespacePrefix;
    /**
     * namespace后缀
     */
    private String namespaceSuffix;
}
