package com.lushstar.pagoda.service.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import com.lushstar.pagoda.service.bo.PluginChangeMetadata;

/**
 * <p>description : DeferredResultWrapper
 *
 * <p>blog : https://Blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/3/7 20:51
 */
public class DeferredResultWrapper {

    private static final long TIMEOUT = 30 * 1000;

    private static final ResponseEntity<PluginChangeMetadata> NOT_MODIFIED_RESPONSE = new ResponseEntity<>(HttpStatus.NOT_MODIFIED);

    private DeferredResult<ResponseEntity<PluginChangeMetadata>> result;

    public DeferredResultWrapper() {
        result = new DeferredResult<>(TIMEOUT, NOT_MODIFIED_RESPONSE);
    }

    public void onTimeout(Runnable timeoutCallback) {
        result.onTimeout(timeoutCallback);
    }

    public void onCompletion(Runnable completionCallback) {
        result.onCompletion(completionCallback);
    }

    public void setResult(PluginChangeMetadata pluginChangeMetadata) {
        result.setResult(new ResponseEntity<>(pluginChangeMetadata, HttpStatus.OK));
    }

    public DeferredResult<ResponseEntity<PluginChangeMetadata>> getResult() {
        return result;
    }

}
