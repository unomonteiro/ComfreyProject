package io.monteirodev.comfreyproject.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import io.monteirodev.comfreyproject.R;
import io.monteirodev.comfreyproject.ui.MainActivity;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Implementation of App Widget functionality.
 */
public class ComfreyWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_plants);

        String app_name_small = context.getString(R.string.app_name_small);
        views.setTextViewText(R.id.widget_title_text, app_name_small);

        Intent appIntent = new Intent(context, MainActivity.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, 0);

        views.setOnClickPendingIntent(R.id.widget_title_layout, appPendingIntent);
        views.setOnClickPendingIntent(R.id.widget_layout_main, appPendingIntent);
        views.setViewVisibility(R.id.widget_empty_list_text, GONE);
        views.setViewVisibility(R.id.widget_list, VISIBLE);
        views.setRemoteAdapter(R.id.widget_list,
                new Intent(context, WidgetViewsService.class));
        views.setEmptyView(R.id.widget_list, R.id.widget_empty_list_text);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateComfreyWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId: appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

