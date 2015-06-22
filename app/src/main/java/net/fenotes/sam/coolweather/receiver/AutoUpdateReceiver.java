package net.fenotes.sam.coolweather.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import net.fenotes.sam.coolweather.service.AutoUpdateService;

/**
 * Created by sam on 15/6/22.
 */
public class AutoUpdateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, AutoUpdateService.class);
        context.startService(i);
    }
}
