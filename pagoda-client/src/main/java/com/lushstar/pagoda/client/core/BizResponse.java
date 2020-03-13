package com.lushstar.pagoda.client.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>description : BizResponse
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/8 18:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BizResponse<T> implements Serializable {

    private boolean success;
    private int code;
    private String message;
    private T data;

    public static <T> BizResponse<T> success(T data) {
        return success("success", data);
    }

    public static <T> BizResponse<T> success(String message, T data) {
        BizResponse<T> response = new BizResponse<>();
        response.setSuccess(true);
        response.setCode(200);
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    public static <T> BizResponse<T> error(String message) {
        return error(400, message);
    }

    public static <T> BizResponse<T> error(int code, String message) {
        BizResponse<T> response = new BizResponse<>();
        response.setSuccess(false);
        response.setCode(code);
        response.setMessage(message);
        return response;
    }

}
