package io.monteirodev.comfreyproject.sync;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.common.util.CollectionUtils;

import java.util.List;

import io.monteirodev.comfreyproject.api.ComfreyClient;
import io.monteirodev.comfreyproject.api.ComfreyInterface;
import io.monteirodev.comfreyproject.data.ComfreyData;
import io.monteirodev.comfreyproject.data.Plant;
import io.monteirodev.comfreyproject.data.PlantDetail;
import io.monteirodev.comfreyproject.data.database.AppDatabase;

public class SyncTask {
    private static final String TAG = SyncTask.class.getSimpleName();
    private static AppDatabase mDb;

    synchronized public static void syncData(@NonNull Context context) {
        try {
            ComfreyInterface comfreyInterface =
                    ComfreyClient.getClient().create(ComfreyInterface.class);

            ComfreyData comfreyData = comfreyInterface.getData().execute().body();
            if (comfreyData == null) {
                Log.d(TAG, "syncData: no comfreyData");
            } else {
                Log.d(TAG, "syncData: we have comfreyData!");
                mDb = AppDatabase.getInstance(context.getApplicationContext());

                mDb.plantsDao().deleteAll();
                mDb.plantDetailsDao().deleteAll();

                List<Plant> plants = comfreyData.getPlants();
                if (!CollectionUtils.isEmpty(plants)) {
                    mDb.plantsDao().insertList(plants);
                    for (Plant plant : plants) {
                        List<PlantDetail> plantDetails = plant.getDetails();
                        if (!CollectionUtils.isEmpty(plantDetails)) {
                            for (PlantDetail detail : plantDetails) {
                                detail.setPlantId(plant.getId());
                                mDb.plantDetailsDao().insert(detail);
                            }
                        }
                    }
                }
                Log.d(TAG, "syncData: comfreyData inserted in database");
            }
        } catch (Exception e) {
            Log.e(TAG, "syncData: error: " + e.getMessage(), e);
        }

        Log.d(TAG, "syncData: complete");

    }
}
