package io.monteirodev.comfreyproject.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.Context;

import io.monteirodev.comfreyproject.R;

public class WidgetIntentService extends IntentService {
    private static final String ACTION_UPDATE_PLANTS= "io.monteirodev.comfreyproject.widget.action.action_update_plants";

    public WidgetIntentService() {
        super("WidgetIntentService");
    }

    public static void startActionUpdatePlants(Context context) {
        Intent intent = new Intent(context, WidgetIntentService.class);
        intent.setAction(ACTION_UPDATE_PLANTS);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_PLANTS.equals(action)) {
                handleActionUpdatePlants();
            }
        }
    }

    private void handleActionUpdatePlants() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, ComfreyWidget.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list);
        ComfreyWidget.updateComfreyWidgets(this, appWidgetManager, appWidgetIds);
    }
}
