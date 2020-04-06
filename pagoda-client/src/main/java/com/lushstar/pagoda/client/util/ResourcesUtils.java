package com.lushstar.pagoda.client.util;

import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.IOException;

/**
 * <p>description : ResourcesUtils
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/7 21:22
 */
@Slf4j
public class ResourcesUtils {

    /**
     * 释放资源
     *
     * @param closeable 可以关闭的资源
     */
    public static void close(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

}
