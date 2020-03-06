package pers.masteryourself.lushstar.pagoda.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pers.masteryourself.lushstar.pagoda.web.vo.WebResponse;

/**
 * <p>description : GlobalExceptionHandler
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/25 21:11
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public WebResponse<String> customException(Exception e) {
        log.error(e.getMessage(), e);
        return WebResponse.error(e.getMessage());
    }

}
