package com.lushstar.pagoda.client.core;

import lombok.Data;

/**
 * <p>description : SimpleHttpResult
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/7 21:17
 */
@Data
public class SimpleHttpResult {

    private Boolean success;
    private String data;
    private Integer code;
    private String msg;

    public SimpleHttpResult(Boolean success, String data, Integer code, String msg) {
        this.success = success;
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public static SimpleHttpResult success(String data) {
        return new SimpleHttpResult(true, data, 200, "请求成功");
    }

    public static SimpleHttpResult error(Integer code) {
        return new SimpleHttpResult(false, null, code, "请求失败");
    }

    public static SimpleHttpResult error(String msg) {
        return new SimpleHttpResult(false, null, 200, msg);
    }

    public static SimpleHttpResult error(Integer code, String msg) {
        return new SimpleHttpResult(false, null, code, msg);
    }

}
