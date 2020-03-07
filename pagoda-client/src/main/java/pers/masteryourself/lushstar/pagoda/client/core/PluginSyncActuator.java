package pers.masteryourself.lushstar.pagoda.client.core;

import lombok.extern.slf4j.Slf4j;
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
public class PluginSyncActuator {

    private static AtomicBoolean initFlag = new AtomicBoolean(false);

    public PluginSyncActuator() {
        this.initScheduleSync();
    }

    public void initScheduleSync() {
        if (initFlag.compareAndSet(false, true)) {
            log.info("pluginSyncActuator start");
            ScheduledExecutorService pool = Executors.newSingleThreadScheduledExecutor();
            pool.scheduleWithFixedDelay(PluginSyncActuator::syncPluginInfo, 0, 3, TimeUnit.SECONDS);
        }
    }

    private static void syncPluginInfo() {
        log.info("插件信息同步开始");
        SimpleHttpResult httpResult = HttpUtils.doGet("", null);
        if (HttpURLConnection.HTTP_NOT_MODIFIED == httpResult.getCode()) {
            log.info("poll config 304 no change");
            return;
        }
        if (HttpURLConnection.HTTP_OK != httpResult.getCode()) {
            log.info("poll config error >>> {} >>> {}", httpResult.getCode(), httpResult.getMsg());
            return;
        }
        System.out.println(httpResult.getData());
    }

}
