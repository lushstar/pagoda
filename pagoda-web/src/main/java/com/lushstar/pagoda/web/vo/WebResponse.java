package com.lushstar.pagoda.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>description : WebResponse
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/2/25 21:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebResponse<T> implements Serializable {

    private boolean success;
    private int code;
    private String message;
    private T data;

    public static <T> WebResponse<T> success(T data) {
        return success("success", data);
    }

    public static <T> WebResponse<T> success(String message, T data) {
        WebResponse<T> response = new WebResponse<>();
        response.setSuccess(true);
        response.setCode(200);
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    public static <T> WebResponse<T> error(String message) {
        return error(400, message);
    }

    public static <T> WebResponse<T> error(int code, String message) {
        WebResponse<T> response = new WebResponse<>();
        response.setSuccess(false);
        response.setCode(code);
        response.setMessage(message);
        return response;
    }

}
