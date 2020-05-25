package com.thefuturestic.toastservice;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

public class MyService  extends Service {
    public static final long INTERVAL=15000;//variable to execute services every 10 second
    private Handler mHandler=new Handler(); // run on another Thread to avoid crash
    private Timer mTimer=null; // timer handling
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("unsupported Operation");
    }
    @Override
    public void onCreate() {
        // cancel if service is  already existed
        if(mTimer!=null)
            mTimer.cancel();
        else
            mTimer=new Timer(); // recreate new timer
        mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(),0,INTERVAL);// schedule task
    }
    @Override
    public void onDestroy() {
        Toast.makeText(this, "In Destroy", Toast.LENGTH_SHORT).show();//display toast when method called
        mTimer.cancel();//cancel the timer
    }
    //inner class of TimeDisplayTimerTask
    private class TimeDisplayTimerTask extends TimerTask {
        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    // display toast at every 10 second
                    Toast.makeText(getApplicationContext(), "Hello world", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
