package pers.masteryourself.lushstar.pagoda.client.annotation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import pers.masteryourself.lushstar.pagoda.client.core.DefaultSpringPluginFactory;

/**
 * <p>description : PagodaRegistrar, 动态向项目中注入 {@link DefaultSpringPluginFactory}
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/7 11:27
 */
@Slf4j
public class PagodaRegistrar implements ImportBeanDefinitionRegistrar {

    public static final String PLUGIN_FACTORY_BEAN_NAME =
            "pers.masteryourself.lushstar.pagoda.client.core.DefaultSpringPluginFactory";

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        String[] beanDefinitionNames = registry.getBeanDefinitionNames();
        boolean flag = false;
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = registry.getBeanDefinition(beanDefinitionName);
            if (beanDefinition.getBeanClassName().equals(PLUGIN_FACTORY_BEAN_NAME)) {
                flag = true;
                break;

            }
        }
        if (flag) {
            log.info("current project has been inject {}", PLUGIN_FACTORY_BEAN_NAME);
        } else {
            // register DefaultSpringPluginFactory
            registry.registerBeanDefinition("defaultSpringPluginFactory", new RootBeanDefinition(DefaultSpringPluginFactory.class));
            log.info("current project inject {}", PLUGIN_FACTORY_BEAN_NAME);
        }
        // register PagodaConfigurer
        registry.registerBeanDefinition("pagodaConfigurer", new RootBeanDefinition(PagodaConfigurer.class));
    }

}
