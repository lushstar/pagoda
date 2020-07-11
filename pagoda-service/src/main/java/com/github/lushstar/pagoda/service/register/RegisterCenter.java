package com.github.lushstar.pagoda.service.register;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    private static final Map<String, List<AppInfo>> REGISTER_CENTER = new ConcurrentHashMap<>();

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

}
