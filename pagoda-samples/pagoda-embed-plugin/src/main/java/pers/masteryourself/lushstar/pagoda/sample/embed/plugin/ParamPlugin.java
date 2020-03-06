package pers.masteryourself.lushstar.pagoda.sample.embed.plugin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * <p>description : ParamPlugin
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/26 1:23
 */
@Slf4j
public class ParamPlugin implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        log.info("插件 {}-{} [{}] 方法执行前的入参是 [{}]", this.getClass().getSimpleName(), Constants.VERSION, method, objects);
    }

}
