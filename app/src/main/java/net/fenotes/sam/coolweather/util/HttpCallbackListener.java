package net.fenotes.sam.coolweather.util;

/**
 * Created by sam on 15/6/22.
 */
public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);
}
