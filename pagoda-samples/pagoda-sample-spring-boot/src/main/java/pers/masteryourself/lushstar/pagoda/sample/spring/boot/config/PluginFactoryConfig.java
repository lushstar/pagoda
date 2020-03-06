package pers.masteryourself.lushstar.pagoda.sample.spring.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.masteryourself.lushstar.pagoda.client.DefaultSpringPluginFactory;
import pers.masteryourself.lushstar.pagoda.client.PluginFactory;

/**
 * <p>description : PluginFactoryConfig
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/26 1:23
 */
@Configuration
public class PluginFactoryConfig {

    @Bean
    public PluginFactory pluginFactory() {
        return new DefaultSpringPluginFactory();
    }

}
