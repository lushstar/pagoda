package com.github.lushstar.pagoda.service.response;

import com.github.lushstar.pagoda.api.response.PluginNotifyResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * <p>description : DeferredResultWrapper
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/7 20:51
 */
public class DeferredResultWrapper {

    private static final long TIMEOUT = 30 * 1000;

    private static final ResponseEntity<PluginNotifyResponse> NOT_MODIFIED_RESPONSE = new ResponseEntity<>(HttpStatus.NOT_MODIFIED);

    private final DeferredResult<ResponseEntity<PluginNotifyResponse>> result;

    public DeferredResultWrapper() {
        result = new DeferredResult<>(TIMEOUT, NOT_MODIFIED_RESPONSE);
    }

    public void onTimeout(Runnable timeoutCallback) {
        result.onTimeout(timeoutCallback);
    }

    public void onCompletion(Runnable completionCallback) {
        result.onCompletion(completionCallback);
    }

    public void setResult(PluginNotifyResponse response) {
        result.setResult(new ResponseEntity<>(response, HttpStatus.OK));
    }

    public DeferredResult<ResponseEntity<PluginNotifyResponse>> getResult() {
        return result;
    }

}
