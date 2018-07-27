package io.monteirodev.comfreyproject.sync;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;

import io.monteirodev.comfreyproject.api.ComfreyClient;
import io.monteirodev.comfreyproject.api.ComfreyInterface;
import io.monteirodev.comfreyproject.data.AppDatabase;
import io.monteirodev.comfreyproject.data.ComfreyData;

public class SyncTask {
    private static final String TAG = SyncTask.class.getSimpleName();
    private static AppDatabase mDb;

    synchronized public static void syncData(@NonNull Context context) {
        try {
            ComfreyInterface comfreyInterface =
                    ComfreyClient.getClient().create(ComfreyInterface.class);

            ComfreyData data = comfreyInterface.getData().execute().body();
            if (data == null) {
                Log.d(TAG, "syncData: no data");
            } else {
                Log.d(TAG, "syncData: we have data!");
                mDb = AppDatabase.getInstance(context.getApplicationContext());
                mDb.plantsDao().insertPlants(data.getPlants());
                Log.d(TAG, "syncData: data inserted in database");
            }
        } catch (IOException e) {
            Log.e(TAG, "syncData: error: " + e.getMessage(), e);
        }

        Log.d(TAG, "syncData: complete");

    }
}
