package com.github.lushstar.pagoda.api.request.plugin;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * <p>description : PluginDelRequest
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/7/11 18:09
 */
@Data
public class PluginDelRequest {

    @NotNull(message = "id 不能为空")
    private Long id;

    /**
     * 0 表示未删除
     */
    @NotNull(message = "del 不能为空")
    private Boolean del;

}
