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

    /**
     * 异常枚举信息定义
     */
    SYSTEM_ERROR(100000L, "系统内部异常"),
    VALID_ERROR(100001L, "校验异常"),
    PARAM_EMPTY(100002L, "{0} 不能为空"),
    PARAM_REPEAT(100003L, "{0} 不能重复"),
    ;

    private final Long code;

    private final String message;

}
