package com.android.moviegenre.Service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class UpdateServiceIntent extends IntentService {

    public UpdateServiceIntent() {
        super("UpdateServiceIntent");
    }

    protected void onHandleIntent(Intent intent) {
        try {
            DownloadFile();
            Log.d("IntentService", "running");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void DownloadFile() {
        Log.d("IntentService", "running");
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("FILE_DOWNLOADED_UPDATE");
        getBaseContext().sendBroadcast(broadcastIntent);
    }
}