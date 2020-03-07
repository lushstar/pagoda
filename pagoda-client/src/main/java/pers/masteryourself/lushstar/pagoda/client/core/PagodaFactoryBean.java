package pers.masteryourself.lushstar.pagoda.client.core;

import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.beans.factory.FactoryBean;

/**
 * <p>description : PagodaFactoryBean
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/7 12:15
 */
public class PagodaFactoryBean<T> implements FactoryBean<T> {

    private Class<T> beanInterface;

    public PagodaFactoryBean() {
    }

    public PagodaFactoryBean(Class<T> beanInterface) {
        this.beanInterface = beanInterface;
    }

    @Override
    public T getObject() throws Exception {
        T target = this.beanInterface.newInstance();
        AspectJProxyFactory proxyFactory = new AspectJProxyFactory(target);
        return proxyFactory.getProxy();
    }

    @Override
    public Class<?> getObjectType() {
        return this.beanInterface;
    }

    public void setBeanInterface(Class<T> beanInterface) {
        this.beanInterface = beanInterface;
    }

}
