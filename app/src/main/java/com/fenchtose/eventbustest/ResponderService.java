package com.fenchtose.eventbustest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import com.fenchtose.eventbustest.MainActivity.MessageEvent;
import de.greenrobot.event.EventBus;

/**
 * Created by konnectez on 07/02/15.
 */
public class ResponderService extends Service {

    private String TAG = "ResponderService";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        Toast.makeText(this, "Service onCreate", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        Toast.makeText(this, "service onStartCommand", Toast.LENGTH_SHORT).show();
        return START_STICKY;
    }

    public void onEvent(MessageEvent event) {
        if (event.getMessage().toLowerCase().equals("quit")) {
            Log.i(TAG, "onDestroy called");
            stopSelf();
            return;
        }

        ServiceMessageEvent response = new ServiceMessageEvent(
                new StringBuilder(event.getMessage()).reverse().toString());
//        event.setMessage();
        EventBus.getDefault().post(response);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service onDestroy", Toast.LENGTH_SHORT).show();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public class ServiceMessageEvent {
        private String message;

        public ServiceMessageEvent(String text) {
            message = text;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String msg) { message = msg;}
    }
}
