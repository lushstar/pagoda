package com.lushstar.pagoda.client.annotation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.StringUtils;
import com.lushstar.pagoda.client.core.DefaultSpringPluginFactory;
import com.lushstar.pagoda.client.core.PagodaScanner;

import java.util.ArrayList;
import java.util.List;

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
            "DefaultSpringPluginFactory";

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
        // scan
        this.doScanner(importingClassMetadata, registry);
    }

    /**
     * 扫包, 只扫描 {@link Pagoda} 标识的类
     */
    private void doScanner(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes annotationAttrs = AnnotationAttributes.fromMap(
                importingClassMetadata.getAnnotationAttributes(EnablePagoda.class.getName()));
        PagodaScanner scanner = new PagodaScanner(registry);
        List<String> basePackages = new ArrayList<>();
        for (String pkg : annotationAttrs.getStringArray("basePackages")) {
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }
        // 默认扫描当前注解下的包
        if (basePackages.isEmpty()) {
            basePackages.add(importingClassMetadata.getClassName().substring(0, importingClassMetadata.getClassName().lastIndexOf(".")));
        }
        scanner.addIncludeFilter(new AnnotationTypeFilter(Pagoda.class));
        scanner.doScan(StringUtils.toStringArray(basePackages));
    }

}
