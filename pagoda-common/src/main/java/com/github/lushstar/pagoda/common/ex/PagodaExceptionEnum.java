package com.github.lushstar.pagoda.common.ex;

import com.github.lushstar.ladder.commons.exceptions.BizExceptionAssert;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>description : PagodaExceptionEnum
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/6/23 16:51
 */
@Getter
@AllArgsConstructor
public enum PagodaExceptionEnum implements BizExceptionAssert {

    // 默认异常
    SYSTEM_ERROR(100001L, "系统内部异常"),

    // client 异常
    APP_NAME_EMPTY(100101L, "app name 不能为空"),
    SERVICE_URL_EMPTY(100101L, "service url 不能为空"),

    // 数据校验异常
    VALID_ERROR(100201L, "校验异常"),
    PARAM_EMPTY(100202L, "{0}不能为空"),
    PARAM_REPEAT(100203L, "{0}不能重复"),
    ID_DATA_NULL(100204L, "[id={0}]的数据查询为空"),
    ;

    private final Long code;

    private final String message;

}
