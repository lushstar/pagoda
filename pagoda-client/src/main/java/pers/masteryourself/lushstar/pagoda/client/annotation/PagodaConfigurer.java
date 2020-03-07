package pers.masteryourself.lushstar.pagoda.client.annotation;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Set;

/**
 * <p>description : PagodaConfigurer
 * <p>只对标注了 {@link Pagoda}注解的 spring bean 有效，将会为其做一层 aop 包装
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/7 11:44
 */
public class PagodaConfigurer implements BeanDefinitionRegistryPostProcessor {

    private PagodaFactoryBean<?> pagodaFactoryBean = new PagodaFactoryBean<Object>();

    public static final String PAGODA_ANNOTATION_BEAN_NAME =
            "pers.masteryourself.lushstar.pagoda.client.annotation.Pagoda";

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        GenericBeanDefinition definition;
        String[] beanDefinitionNames = registry.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = registry.getBeanDefinition(beanDefinitionName);
            if (findPagoda(beanDefinition, registry)) {
                // 找到
                this.reRegister(registry, beanDefinitionName, beanDefinition);
            }
            // 没有找到 do nothing
        }
    }

    /**
     * 重新注冊
     *
     * @param registry
     * @param beanDefinitionName
     * @param beanDefinition
     */
    private void reRegister(BeanDefinitionRegistry registry, String beanDefinitionName, BeanDefinition beanDefinition) {
        GenericBeanDefinition definition;
        definition = (GenericBeanDefinition) beanDefinition;
        definition.getConstructorArgumentValues().addGenericArgumentValue(beanDefinition.getBeanClassName());
        definition.setBeanClass(this.pagodaFactoryBean.getClass());
        definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        registry.removeBeanDefinition(beanDefinitionName);
        registry.registerBeanDefinition(beanDefinitionName, definition);
    }

    /**
     * 查找 pagoda 标识的类
     *
     * @param registry
     * @return
     */
    private boolean findPagoda(BeanDefinition beanDefinition, BeanDefinitionRegistry registry) {
        if (beanDefinition instanceof AnnotatedBeanDefinition) {
            AnnotatedBeanDefinition annotatedBeanDefinition = (AnnotatedBeanDefinition) beanDefinition;
            AnnotationMetadata metadata = annotatedBeanDefinition.getMetadata();
            Set<String> annotationTypes = metadata.getAnnotationTypes();
            return annotationTypes.stream().anyMatch(annotationName -> annotationName.equals(PAGODA_ANNOTATION_BEAN_NAME));
        }
        return false;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // do nothing
    }

}
