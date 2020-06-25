package com.github.lushstar.pagoda.client.annotation;

import java.lang.annotation.*;

/**
 * <p>description : Pagoda, 标识此类是一个需要扩展的 AOP 切面类, Pagoda 仅对标识了 {@link Pagoda} 注解的类生效
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/7 11:29
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Pagoda {
}
