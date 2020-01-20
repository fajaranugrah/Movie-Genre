package com.android.moviegenre.Service;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;

import androidx.annotation.NonNull;

public class UpdateService extends Service {

    IBinder mBinder = new LocalBinder();
    private Handler mServiceHandler;
    private static final String TAG = "update";
    Thread mThread;

    public UpdateService() {

    }

    @Override
    public void onCreate() {
        // The service is being created
        HandlerThread handlerThread = new HandlerThread(TAG);
        handlerThread.start();
        mServiceHandler = new Handler(handlerThread.getLooper());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // The service is starting, due to a call to startService()
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        stopForeground(true);
        // A client is binding to the service with bindService()
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // All clients have unbound with unbindService()
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        // A client is binding to the service with bindService(),
        // after onUnbind() has already been called
        stopForeground(true);
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        // The service is no longer used and is being destroyed
        mServiceHandler.removeCallbacksAndMessages(null);
    }

    /**
     * Class used for the client Binder.  Since this service runs in the same process as its
     * clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        public UpdateService getService() {
            return UpdateService.this;
        }
    }

    public void requestUpdate(@NonNull final Activity activity) {
        startService(new Intent(activity, UpdateService.class));
        //update(activity);
    }

    public void update(@NonNull final Activity activity) {
        mThread = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    startService(new Intent(activity, UpdateServiceIntent.class));
                } catch (Exception e) {
                    mThread.interrupt();
                    e.printStackTrace();
                }
            }
        };
        mThread.start();
    }
}
