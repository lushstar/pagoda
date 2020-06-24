package com.github.lushstar.pagoda.client.core;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * <p>description : PagodaScanner
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/7 16:02
 */
public class PagodaScanner extends ClassPathBeanDefinitionScanner {

    private PagodaFactoryBean<?> pagodaFactoryBean = new PagodaFactoryBean<>();

    public static List<String> pagodaBeanNames = new ArrayList<>();

    public PagodaScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    /**
     * 扫包
     *
     * @param basePackages 包路径
     * @return
     */
    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
        if (beanDefinitions.isEmpty()) {
            logger.warn("No Pagoda bean was found in '" + Arrays.toString(basePackages) + "' package. Please check your configuration.");
        } else {
            this.processBeanDefinitions(beanDefinitions);
        }
        return beanDefinitions;
    }

    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions) {
        beanDefinitions.forEach(beanDefinitionHolder -> {
            this.registerBean(beanDefinitionHolder);
            pagodaBeanNames.add(beanDefinitionHolder.getBeanName());
        });
    }

    /**
     * 注冊到 Spring 容器中
     *
     * @param beanDefinitionHolder
     */
    private void registerBean(BeanDefinitionHolder beanDefinitionHolder) {
        BeanDefinition beanDefinition = beanDefinitionHolder.getBeanDefinition();
        GenericBeanDefinition definition = (GenericBeanDefinition) beanDefinition;
        // 添加构造参数
        definition.getConstructorArgumentValues().addGenericArgumentValue(beanDefinition.getBeanClassName());
        // 设置 bean 的类型是 pagodaFactoryBean
        definition.setBeanClass(this.pagodaFactoryBean.getClass());
        // 根据类型自动注入
        definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
    }

}
