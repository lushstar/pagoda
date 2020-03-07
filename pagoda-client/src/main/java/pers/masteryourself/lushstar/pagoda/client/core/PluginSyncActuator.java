package pers.masteryourself.lushstar.pagoda.client.core;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import pers.masteryourself.lushstar.pagoda.client.PluginFactory;
import pers.masteryourself.lushstar.pagoda.client.po.Plugin;
import pers.masteryourself.lushstar.pagoda.client.util.HttpUtils;
import pers.masteryourself.lushstar.pagoda.client.util.SimpleHttpResult;

import java.net.HttpURLConnection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * <p>description : PluginSyncActuator, 插件同步执行器
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/7 21:09
 */
@Slf4j
public class PluginSyncActuator implements EnvironmentAware, ApplicationContextAware {

    private PluginFactory pluginFactory;

    private static final Gson GSON = new Gson();

    private static AtomicBoolean initFlag = new AtomicBoolean(false);

    String appName;
    String serviceUrl;

    public PluginSyncActuator() {
        this.initScheduleSync();
    }

    public void initScheduleSync() {
        if (initFlag.compareAndSet(false, true)) {
            log.info("pluginSyncActuator start");
            ScheduledExecutorService pool = Executors.newSingleThreadScheduledExecutor();
            // 第一次有 5s 延时，防止初始化未完成
            pool.scheduleWithFixedDelay(this::syncPluginInfo, 5, 3, TimeUnit.SECONDS);
        }
    }

    private void syncPluginInfo() {
        log.info("插件信息同步开始");
        SimpleHttpResult httpResult = HttpUtils.doGet(serviceUrl + appName, null);
        if (HttpURLConnection.HTTP_NOT_MODIFIED == httpResult.getCode()) {
            log.info("poll config 304 no change");
            return;
        }
        if (HttpURLConnection.HTTP_OK != httpResult.getCode()) {
            log.info("poll config error >>> {} >>> {}", httpResult.getCode(), httpResult.getMsg());
            return;
        }
        String result = httpResult.getData();
        if (result == null || "".equals(result)) {
            return;
        }
        Plugin plugin = GSON.fromJson(result, Plugin.class);
        this.notifyPlugin(plugin);
    }

    /**
     * 更改插件
     *
     * @param plugin
     */
    private void notifyPlugin(Plugin plugin) {
        switch (plugin.getSourceType()) {
            case INSTALL:
                pluginFactory.install(plugin);
                break;
            case ACTIVE:
                pluginFactory.active(plugin.getId());
                break;
            case DISABLE:
                pluginFactory.disable(plugin.getId());
                break;
            case UNINSTALL:
                pluginFactory.uninstall(plugin.getId());
                break;
            default:
                log.warn("操作有问题, {}", plugin.getSourceType());
                break;
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        // 从环境变量中解析参数
        appName = environment.getProperty("pagoda.app.name", "blank");
        serviceUrl = environment.getProperty("pagoda.service.url", "http://127.0.0.1:7070");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        pluginFactory = applicationContext.getBean(PluginFactory.class);
    }

}
