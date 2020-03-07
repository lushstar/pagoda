package pers.masteryourself.lushstar.pagoda.client.annotation;

import org.springframework.context.annotation.Import;
import pers.masteryourself.lushstar.pagoda.client.core.PluginSyncActuator;

import java.lang.annotation.*;

/**
 * <p>description : EnablePagoda, 开启 pagoda 动态 aop 功能
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/7 11:27
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({PagodaRegistrar.class, PluginSyncActuator.class})
public @interface EnablePagoda {

    String[] basePackages() default {};

}
