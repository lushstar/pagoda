package com.github.lushstar.pagoda.client.spring;

import com.github.lushstar.pagoda.client.plugin.DefaultSpringPluginManager;
import com.github.lushstar.pagoda.client.plugin.PluginManager;
import com.github.lushstar.pagoda.client.plugin.PluginSyncActuator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>description : PagodaAutoConfiguration
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/7/11 13:13
 */
@Configuration
@EnableConfigurationProperties({PagodaProperties.class})
public class PagodaAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public PluginManager pluginManager() {
        return new DefaultSpringPluginManager();
    }

    @Bean
    @ConditionalOnMissingBean
    public PluginSyncActuator pluginSyncActuator(PagodaProperties pagodaProperties, PluginManager pluginManager) {
        return new PluginSyncActuator(pagodaProperties, pluginManager);
    }

}
