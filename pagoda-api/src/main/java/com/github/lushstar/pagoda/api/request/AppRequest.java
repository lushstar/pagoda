package com.github.lushstar.pagoda.api.request;

import lombok.Data;

/**
 * <p>description : AppRequest
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/6/24 14:19
 */
@Data
public class AppRequest {

    private Long id;

    /**
     * 0 表示未删除
     */
    private Boolean del;

    /**
     * 应用名称
     */
    private String name;

    /**
     * 描述信息
     */
    private String description;

}
