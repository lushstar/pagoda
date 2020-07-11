package com.github.lushstar.pagoda.service.register;

import lombok.Data;

/**
 * <p>description : AppInfo
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/7/11 13:55
 */
@Data
public class AppInfo {

    private String name;

    private String instanceId;

    public AppInfo() {
    }

    public AppInfo(String name, String instanceId) {
        this.name = name;
        this.instanceId = instanceId;
    }
}
