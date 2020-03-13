package com.lushstar.pagoda.client.annotation;

import com.lushstar.pagoda.client.core.DefaultSpringPluginManager;
import com.lushstar.pagoda.client.core.PagodaScanner;
import com.lushstar.pagoda.client.core.PluginSyncActuator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>description : PagodaRegistrar, 动态向项目中注入 {@link DefaultSpringPluginManager}
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/7 11:27
 */
@Slf4j
public class PagodaRegistrar implements ImportBeanDefinitionRegistrar {

    private static final String BASE_PACKAGES = "basePackages";

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // 注册 DefaultSpringPluginManager 类, 提供 pluginManager 的增删改查
        this.registerBeanDefinitionIfNotExists(registry, DefaultSpringPluginManager.class);
        // 注册 PluginSyncActuator 类, 同于同步且更新 pluginManager
        this.registerBeanDefinitionIfNotExists(registry, PluginSyncActuator.class);
        // 包扫描, 把添加 @EnablePagoda 注解的类都扫描到容器环境中, 做增强
        this.doScanner(importingClassMetadata, registry);
    }

    /**
     * 扫包, 只扫描 {@link Pagoda} 标识的类
     */
    private void doScanner(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes annotationAttrs = AnnotationAttributes.fromMap(
                importingClassMetadata.getAnnotationAttributes(EnablePagoda.class.getName()));
        if (annotationAttrs == null) {
            return;
        }
        PagodaScanner scanner = new PagodaScanner(registry);
        List<String> basePackages = new ArrayList<>();
        for (String pkg : annotationAttrs.getStringArray(BASE_PACKAGES)) {
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

    /**
     * 向容器中注入 bean, 只有容器不存在这个 bean 或者这个类型才会注入
     *
     * @param registry  bean 注册器
     * @param beanClass bean 的类型
     */
    private void registerBeanDefinitionIfNotExists(BeanDefinitionRegistry registry, Class<?> beanClass) {
        String beanName = beanClass.getName();
        // 判断 beanName
        if (registry.containsBeanDefinition(beanName)) {
            log.info("current project has been inject {}", beanName);
            return;
        }
        // 判断 bean 类型
        for (String candidate : registry.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = registry.getBeanDefinition(candidate);
            if (Objects.equals(beanDefinition.getBeanClassName(), beanClass.getName())) {
                log.info("current project has been inject {}", beanName);
                return;
            }
        }
        // 给容器中注入 beanClass
        BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(beanClass).getBeanDefinition();
        registry.registerBeanDefinition(beanName, beanDefinition);
        log.info("current project inject a bean, bean name is {}", beanName);
    }

}
