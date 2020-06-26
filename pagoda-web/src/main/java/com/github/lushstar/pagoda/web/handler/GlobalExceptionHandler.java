package com.github.lushstar.pagoda.web.handler;

import com.github.lushstar.ladder.commons.exceptions.BizException;
import com.github.lushstar.pagoda.api.response.ServiceResponse;
import com.github.lushstar.pagoda.common.ex.PagodaExceptionEnum;
import com.github.lushstar.pagoda.web.vo.WebResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>description : GlobalExceptionHandler
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/25 21:11
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ServiceResponse<String> validationException(Exception e) {
        log.error(e.getMessage(), e);
        return ServiceResponse.error(PagodaExceptionEnum.VALID_ERROR.getCode().intValue(), e.getMessage());
    }

    @ExceptionHandler(BizException.class)
    @ResponseBody
    public ServiceResponse<String> bizExceptionHandler(BizException e) {
        log.error(e.getMessage(), e);
        return ServiceResponse.error(e.getCode().intValue(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public WebResponse<String> customException(Exception e) {
        log.error("GlobalExceptionHandler catch exception", e);
        return WebResponse.error(e.getMessage());
    }

}