package pers.masteryourself.lushstar.pagoda.service.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import pers.masteryourself.lushstar.pagoda.service.bo.PluginBo;

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

    private static final ResponseEntity<PluginBo> NOT_MODIFIED_RESPONSE = new ResponseEntity<>(HttpStatus.NOT_MODIFIED);

    private DeferredResult<ResponseEntity<PluginBo>> result;

    public DeferredResultWrapper() {
        result = new DeferredResult<>(TIMEOUT, NOT_MODIFIED_RESPONSE);
    }

    public void onTimeout(Runnable timeoutCallback) {
        result.onTimeout(timeoutCallback);
    }

    public void onCompletion(Runnable completionCallback) {
        result.onCompletion(completionCallback);
    }

    public void setResult(PluginBo pluginBo) {
        result.setResult(new ResponseEntity<>(pluginBo, HttpStatus.OK));
    }

    public DeferredResult<ResponseEntity<PluginBo>> getResult() {
        return result;
    }

}
