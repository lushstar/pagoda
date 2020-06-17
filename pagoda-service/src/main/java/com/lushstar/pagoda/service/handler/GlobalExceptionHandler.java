package com.lushstar.pagoda.service.handler;

import com.lushstar.pagoda.api.response.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * <p>description : GlobalExceptionHandler
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/25 21:44
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ServiceResponse<String> customException(Exception e) {
        log.error(e.getMessage(), e);
        return ServiceResponse.error(e.getMessage());
    }

}
