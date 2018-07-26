package io.monteirodev.comfreyproject.sync;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import io.monteirodev.comfreyproject.api.ComfreyClient;
import io.monteirodev.comfreyproject.api.ComfreyInterface;
import io.monteirodev.comfreyproject.data.ComfreyData;

public class SyncTask {
    private static final String TAG = SyncTask.class.getSimpleName();

    synchronized public static void syncData(Context context) {
        try {
            ComfreyInterface comfreyInterface =
                    ComfreyClient.getClient().create(ComfreyInterface.class);

            ComfreyData data = comfreyInterface.getData().execute().body();
            if (data == null) {
                Log.d(TAG, "syncData: no data");
            } else {
                Log.d(TAG, "syncData: we have data!");
            }
        } catch (IOException e) {
            Log.e(TAG, "syncData: error: " + e.getMessage(), e);
        }

        Log.d(TAG, "syncData: complete");

    }
}
