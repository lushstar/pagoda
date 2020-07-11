package com.github.lushstar.pagoda.client.spring;

import com.github.lushstar.pagoda.client.plugin.DefaultSpringPluginManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>description : PagodaRegistrar, 动态向项目中注入 {@link DefaultSpringPluginManager}
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/7 11:27
 */
@Slf4j
public class PagodaRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // 包扫描, 把加上 @Pagoda 注解的类都扫描到容器环境中, 做 proxy 代理
        this.doScanner(importingClassMetadata, registry);
    }

    /**
     * 扫包, 只扫描 {@link Pagoda} 标识的类
     *
     * @param annotationMetadata {@link AnnotationMetadata}
     * @param registry           {@link BeanDefinitionRegistry}
     */
    private void doScanner(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes annotationAttrs = AnnotationAttributes.fromMap(
                annotationMetadata.getAnnotationAttributes(EnablePagoda.class.getName()));
        if (annotationAttrs == null) {
            return;
        }
        PagodaScanner scanner = new PagodaScanner(registry);
        List<String> basePackages = new ArrayList<>();
        for (String pkg : annotationAttrs.getStringArray("basePackages")) {
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }
        // 默认扫描当前注解下的包
        if (basePackages.isEmpty()) {
            basePackages.add(annotationMetadata.getClassName().substring(0, annotationMetadata.getClassName().lastIndexOf(".")));
        }
        scanner.addIncludeFilter(new AnnotationTypeFilter(Pagoda.class));
        scanner.doScan(StringUtils.toStringArray(basePackages));
    }

}
