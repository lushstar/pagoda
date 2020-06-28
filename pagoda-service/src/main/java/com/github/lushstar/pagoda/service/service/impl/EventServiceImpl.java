package com.github.lushstar.pagoda.service.service.impl;

import com.github.lushstar.pagoda.service.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * <p>description : ApplicationEventServiceImpl
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/7 20:45
 */
@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Override
    public void sendEvent(ApplicationEvent event) {
        eventPublisher.publishEvent(event);
    }

}
