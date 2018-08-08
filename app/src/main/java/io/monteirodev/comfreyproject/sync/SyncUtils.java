package io.monteirodev.comfreyproject.sync;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;
import com.google.android.gms.common.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.monteirodev.comfreyproject.data.Plant;
import io.monteirodev.comfreyproject.data.database.AppDatabase;

public class SyncUtils {

    private static boolean sInitialized;
    private static final int DAY_IN_SECONDS = (int) TimeUnit.DAYS.toSeconds(1);
    private static final int FLEX_TIME = DAY_IN_SECONDS / 2;

    private static final String COMFREY_SYNC_TAG = "comfrey-sync";

    synchronized public static void initialise(@NonNull final Context context) {
        if (sInitialized) {
            return;
        }
        sInitialized = true;
        scheduleDailySyncService(context);
        checkForEmpty(context);
    }

    private static void startImmediateSync(@NonNull final Context context) {
        Intent intentToSyncImmediately = new Intent(context, ComfreySyncIntentService.class);
        context.startService(intentToSyncImmediately);
    }

    private static void scheduleDailySyncService(@NonNull final Context context) {
        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        Job periodicSyncJob = dispatcher.newJobBuilder()
                .setService(ComfreyFirebaseJobService.class)
                .setTag(COMFREY_SYNC_TAG)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(DAY_IN_SECONDS,
                        DAY_IN_SECONDS + FLEX_TIME))
                .setReplaceCurrent(true)
                .build();
        dispatcher.schedule(periodicSyncJob);
    }

    private static void checkForEmpty(@NonNull final Context context) {
        Thread checkForEmpty = new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = AppDatabase.getInstance(context.getApplicationContext());

                if (db.plantsDao().countPlants() == 0 ||
                        db.plantDetailsDao().countPlantDetails() == 0 ||
                        db.recipesDao().countRecipes() == 0 ||
                        db.recipeDetailsDao().countRecipeDetails() == 0 ||
                        db.getInvolvedDetailsDao().countGetInvolvedDetails() == 0 ||
                        db.aboutDetailsDao().countAboutDetails() == 0) {
                    startImmediateSync(context);
                }
            }
        });
        checkForEmpty.start();
    }
}
