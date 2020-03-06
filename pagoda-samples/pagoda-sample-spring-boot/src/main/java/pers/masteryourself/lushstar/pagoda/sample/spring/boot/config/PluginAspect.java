package pers.masteryourself.lushstar.pagoda.sample.spring.boot.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * <p>description : PluginAspect
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/26 1:23
 */
@Aspect
@Component
@Order(1)
public class PluginAspect {

    @Pointcut("execution(* pers.masteryourself.lushstar.pagoda.sample.spring.boot.service.*.*(..))")
    public void declareJointPointExpression() {
    }

    @Before("declareJointPointExpression()")
    public void doBefore(JoinPoint joinPoint) {
    }

}
