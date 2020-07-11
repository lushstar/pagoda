package com.github.lushstar.pagoda.api.request.app;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * <p>description : AppUpdateRequest
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/7/11 13:30
 */
@Data
public class AppUpdateRequest extends AppBaseRequest {

    @NotBlank(message = "id 不能为空")
    private Long id;


    /**
     * 0 表示未删除
     */
    private Boolean del;

}
