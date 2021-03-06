package com.lushstar.pagoda.client.core;

import lombok.Data;

/**
 * <p>description : PluginChangeMetadata
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/8 16:57
 */
@Data
public class PluginChangeMetadata {

    private Long id;

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
