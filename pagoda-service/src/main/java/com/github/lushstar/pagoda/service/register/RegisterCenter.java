package com.github.lushstar.pagoda.service.register;

import com.github.lushstar.pagoda.api.response.PluginNotifyMetadata;
import com.github.lushstar.pagoda.service.response.DeferredResultWrapper;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * <p>description : RegisterCenter
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/7/11 13:54
 */
public class RegisterCenter {

    /**
     * 应用注册中心
     * key 是应用名称, value 是应用信息集合
     */
    private static final Map<String, List<AppInfo>> REGISTER_CENTER = new ConcurrentHashMap<>();

    /**
     * 缓存插件变化通知
     * key 是应用名称, value 是插件变化元数据
     */
    public static final Map<String, PluginNotifyMetadata> CACHE_CONFIGS = new ConcurrentHashMap<>();

    /**
     * 缓存请求
     * key 是实例 Id, value 是 DeferredResultWrapper
     */
    public static final Map<String, DeferredResultWrapper> HOLD_REQUEST_CONFIGS = new ConcurrentHashMap<>();

    public static synchronized void register(String appName, String instanceId) {
        List<AppInfo> appInfos = REGISTER_CENTER.get(appName);
        AppInfo appInfo = new AppInfo(appName, instanceId);
        if (appInfos == null) {
            appInfos = new ArrayList<>();
            appInfos.add(appInfo);
            REGISTER_CENTER.put(appName, appInfos);
        } else {
            appInfos.add(appInfo);
        }
    }

    public static List<AppInfo> get(String appName) {
        return REGISTER_CENTER.get(appName);
    }

    public static AppInfo get(String appName, String instanceId) {
        List<AppInfo> appInfos = REGISTER_CENTER.get(appName);
        if (CollectionUtils.isEmpty(appInfos)) {
            return null;
        }
        List<AppInfo> appInfoList = appInfos.stream().filter(appInfo -> instanceId.equals(appInfo.getInstanceId())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(appInfoList)) {
            return null;
        }
        return appInfoList.get(0);
    }

}
