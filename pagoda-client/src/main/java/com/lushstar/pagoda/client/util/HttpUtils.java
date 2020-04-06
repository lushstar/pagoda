package com.lushstar.pagoda.client.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.lushstar.pagoda.client.core.SimpleHttpResult;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

/**
 * <p>description : HttpUtils
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/7 21:16
 */
public class HttpUtils {

    /**
     * TODO 使用 HttpURLConnection 发送请求, 应该有优化点
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return 响应结果
     */
    public static SimpleHttpResult doGet(String url, JsonObject params) {
        InputStream in = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            String queryString = getQueryString(params);
            if (queryString.length() > 0) {
                queryString = queryString.substring(0, queryString.length() - 1);
                url = url + "?" + queryString;
            }
            URLConnection urlConnection = new URL(url).openConnection();
            HttpURLConnection httpUrlConnection = (HttpURLConnection) urlConnection;
            httpUrlConnection.setDoOutput(false);
            httpUrlConnection.setDoInput(true);
            httpUrlConnection.setUseCaches(false);
            httpUrlConnection.setRequestMethod("GET");
            httpUrlConnection.setConnectTimeout(10 * 1000);
            httpUrlConnection.setReadTimeout(100 * 1000);
            httpUrlConnection.connect();
            int responseCode = httpUrlConnection.getResponseCode();
            if (HttpURLConnection.HTTP_OK == responseCode) {
                byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                in = httpUrlConnection.getInputStream();
                while ((len = in.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, len);
                    byteArrayOutputStream.flush();
                }
                return SimpleHttpResult.success(byteArrayOutputStream.toString("UTF-8"));
            } else {
                return SimpleHttpResult.error(responseCode);
            }
        } catch (Exception e) {
            return SimpleHttpResult.error(500, "请求异常，异常信息：" + e.getClass() + "->" + e.getMessage());
        } finally {
            ResourcesUtils.close(byteArrayOutputStream);
            ResourcesUtils.close(in);
        }
    }

    /**
     * 封装请求参数
     *
     * @param params 请求参数
     * @return 封装后的结果
     * @throws UnsupportedEncodingException 异常信息
     */
    private static String getQueryString(JsonObject params) throws UnsupportedEncodingException {
        StringBuilder queryString = new StringBuilder();
        if (params != null) {
            for (Map.Entry<String, JsonElement> entry : params.entrySet()) {
                queryString.append(entry.getKey())
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"))
                        .append("&");
            }
        }
        return queryString.toString();
    }

}
