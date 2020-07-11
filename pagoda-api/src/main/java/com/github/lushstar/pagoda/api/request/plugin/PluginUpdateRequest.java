package com.github.lushstar.pagoda.api.request.plugin;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * <p>description : PluginUpdateRequest
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/7/11 13:40
 */
@Data
public class PluginUpdateRequest extends PluginBaseRequest{

    @NotBlank(message = "id 不能为空")
    private Long id;

    /**
     * 0 表示未删除
     */
    private Boolean del;

}
