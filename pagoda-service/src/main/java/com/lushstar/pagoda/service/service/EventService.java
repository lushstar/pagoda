package com.lushstar.pagoda.service.service;

import org.springframework.context.ApplicationEvent;

/**
 * <p>description : EventService
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/7 20:45
 */
public interface EventService {

    void sendEvent(ApplicationEvent event);

}
