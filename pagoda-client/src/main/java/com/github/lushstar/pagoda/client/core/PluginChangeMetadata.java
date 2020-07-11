package com.github.lushstar.pagoda.client.core;

import com.github.lushstar.pagoda.common.enums.SourceType;
import lombok.Data;

/**
 * <p>description : PluginChangeMetadata
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/8 16:40
 */
@Data
public class PluginChangeMetadata {

    private Long appPluginId;

    private String appName;

    private String address;

    private String className;

    private SourceType sourceType;

    /**
     * 0、false 表示禁用
     */
    private boolean active;

    /**
     * 临时变量，仅用于客户端
     * 表示下载后的缓存地址
     */
    private String localAddress;

}
