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

import java.util.concurrent.TimeUnit;

public class SyncUtils {

    private static boolean sInitialized;
    private static final int DAY_IN_SECONDS = (int) TimeUnit.DAYS.toSeconds(1);
    private static final int FLEX_TIME = DAY_IN_SECONDS / 2;

    public static final String COMFREY_SYNC_TAG = "comfrey-sync";

    synchronized public static void initialise(@NonNull final Context context) {
        if (sInitialized) {
            return;
        }
        sInitialized = true;
        scheduleDailySyncService(context);
        checkForEmpty(context);
    }

    public static void startImmediateSync(@NonNull final Context context) {
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
                // todo query database here
                startImmediateSync(context);
            }
        });
        checkForEmpty.start();
    }
}
