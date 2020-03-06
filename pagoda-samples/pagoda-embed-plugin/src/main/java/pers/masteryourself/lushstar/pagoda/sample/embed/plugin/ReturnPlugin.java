package pers.masteryourself.lushstar.pagoda.sample.embed.plugin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

/**
 * <p>description : ReturnPlugin
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/26 1:23
 */
@Slf4j
public class ReturnPlugin implements AfterReturningAdvice {

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        log.info("插件 {}-{} [{}] 方法执行后的返回值是 [{}]", this.getClass().getSimpleName(), Constants.VERSION, method, returnValue);
    }

}
