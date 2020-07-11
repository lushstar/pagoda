package com.github.lushstar.pagoda.client.spring;

import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.beans.factory.FactoryBean;

/**
 * <p>description : PagodaFactoryBean
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/7 12:15
 */
public class PagodaFactoryBean<T> implements FactoryBean<T> {

    private Class<T> proxyBeanInterface;

    public PagodaFactoryBean() {
    }

    public PagodaFactoryBean(Class<T> proxyBeanInterface) {
        this.proxyBeanInterface = proxyBeanInterface;
    }

    @Override
    public T getObject() throws Exception {
        T target = this.proxyBeanInterface.newInstance();
        AspectJProxyFactory proxyFactory = new AspectJProxyFactory(target);
        return proxyFactory.getProxy();
    }

    @Override
    public Class<?> getObjectType() {
        return this.proxyBeanInterface;
    }

}
