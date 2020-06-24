package com.github.lushstar.pagoda.client.core;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.github.lushstar.pagoda.api.dto.PluginChangeMetadata;
import com.github.lushstar.pagoda.api.dto.SourceType;
import com.github.lushstar.pagoda.client.PluginManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * <p>description : PluginSyncActuator, 插件同步执行器
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/7 21:09
 */
@Slf4j
public class PluginSyncActuator implements EnvironmentAware, ApplicationContextAware {

    private static final String PART_PLUGIN_SYNC_URL = "/service/plugin/sync/part/";

    private static final String FULL_PLUGIN_SYNC_URL = "/service/plugin/sync/full/";

    private static AtomicBoolean initFlag = new AtomicBoolean(false);

    private PluginManager pluginManager;

    private String appName;

    private String serviceUrl;

    @PostConstruct
    public void initScheduleSync() {
        if (initFlag.compareAndSet(false, true)) {
            log.info("init sync thread");
            initPartIncrementThread();
            initFullInIncrementThread();
        }
    }

    /**
     * 初始化增量同步线程
     * 第一次有 5s 延时，防止初始化未完成, 每隔 3s 同步一次信息, 每次同步信息最大有 60s 延时
     */
    private void initPartIncrementThread() {
        ScheduledExecutorService pool = Executors.newSingleThreadScheduledExecutor(r -> new Thread(r, "part-increment-plugin-sync-thread"));
        pool.scheduleWithFixedDelay(this::partSyncPluginInfo, 5, 3, TimeUnit.SECONDS);
    }

    /**
     * 初始化全量同步线程
     * 第一次有 5s 延时，防止初始化未完成, 每隔 60s 同步一次全量信息, 从数据库中查询数据，直接返回
     */
    private void initFullInIncrementThread() {
        ScheduledExecutorService pool = Executors.newSingleThreadScheduledExecutor(r -> new Thread(r, "full-increment-plugin-sync-thread"));
        pool.scheduleWithFixedDelay(this::fullSyncPluginInfo, 5, 60, TimeUnit.SECONDS);
    }

    private void partSyncPluginInfo() {
        log.info("{} plugin info part sync start", Thread.currentThread().getName());
        String result = this.syncPluginInfo(serviceUrl + PART_PLUGIN_SYNC_URL + appName);
        if (StrUtil.isEmpty(result)) {
            return;
        }
        PluginChangeMetadata pluginChangeMetadata = JSONUtil.toBean(result, PluginChangeMetadata.class);
        this.notifyPlugin(pluginChangeMetadata);
    }

    private void fullSyncPluginInfo() {
        log.info("{} plugin info full sync start", Thread.currentThread().getName());
        String result = this.syncPluginInfo(serviceUrl + FULL_PLUGIN_SYNC_URL + appName);
        if (StrUtil.isEmpty(result)) {
            return;
        }
        BizResponse<List<PluginChangeMetadata>> bizResponse = JSONUtil.toBean(result, new TypeReference<BizResponse<List<PluginChangeMetadata>>>() {
        }.getType(), false);
        List<PluginChangeMetadata> pluginChangeMetadataList = bizResponse.getData();
        if (pluginChangeMetadataList == null) {
            return;
        }
        pluginChangeMetadataList.forEach(pluginChangeMetadata -> {
            SourceType oldSourceType = pluginChangeMetadata.getSourceType();
            if (oldSourceType == SourceType.ACTIVE || oldSourceType == SourceType.DISABLE) {
                // 这里如果是 ACTIVE、DISABLE 类型，要先判断插件缓存是否有次插件，没有插件就下载
                if (!pluginManager.hasPlugin(pluginChangeMetadata.getId())) {
                    log.warn("plugin id {} is empty, begin download", pluginChangeMetadata.getId());
                    pluginChangeMetadata.setSourceType(SourceType.INSTALL);
                    this.notifyPlugin(pluginChangeMetadata);
                }
                // 还原
                pluginChangeMetadata.setSourceType(oldSourceType);
                this.notifyPlugin(pluginChangeMetadata);
            }
        });
    }

    /**
     * 更改插件
     *
     * @param pluginChangeMetadata
     */
    private void notifyPlugin(PluginChangeMetadata pluginChangeMetadata) {
        switch (pluginChangeMetadata.getSourceType()) {
            case INSTALL:
                pluginManager.install(pluginChangeMetadata);
                break;
            case ACTIVE:
                pluginManager.active(pluginChangeMetadata.getId());
                break;
            case DISABLE:
                pluginManager.disable(pluginChangeMetadata.getId());
                break;
            case UNINSTALL:
                pluginManager.uninstall(pluginChangeMetadata.getId());
                break;
            default:
                log.warn("操作有问题, {}", pluginChangeMetadata.getSourceType());
                break;
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        // 从环境变量中解析参数
        appName = environment.getProperty("pagoda.app.name", "blank");
        serviceUrl = environment.getProperty("pagoda.service.url", "http://localhost:7070/service/plugin/sync/");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        pluginManager = applicationContext.getBean(PluginManager.class);
    }

    private String syncPluginInfo(String url) {
        HttpResponse response = null;
        try {
            response = HttpUtil.createGet(url)
                    .setConnectionTimeout(3 * 1000)
                    .setReadTimeout(100 * 1000)
                    .execute();
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            return null;
        }
        int status = response.getStatus();
        if (HttpURLConnection.HTTP_NOT_MODIFIED == status) {
            log.info("poll config 304 no change");
            return null;
        }
        String result = response.body();
        if (HttpURLConnection.HTTP_OK != status) {
            log.info("poll config error >>> {} >>> {}", status, result);
            return null;
        }
        if (StrUtil.isEmpty(result)) {
            return null;
        }
        return result;
    }

}
