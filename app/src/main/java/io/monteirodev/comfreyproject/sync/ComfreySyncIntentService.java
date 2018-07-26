package io.monteirodev.comfreyproject.sync;

import android.app.IntentService;
import android.content.Intent;

public class ComfreySyncIntentService extends IntentService {

    public ComfreySyncIntentService() {
        super("ComfreySyncIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SyncTask.syncData(this);
    }
}
