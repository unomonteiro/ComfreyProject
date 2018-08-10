package io.monteirodev.comfreyproject.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.android.gms.common.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.monteirodev.comfreyproject.data.Plant;
import io.monteirodev.comfreyproject.data.database.AppDatabase;

import static io.monteirodev.comfreyproject.ui.plants.PlantDetailsActivity.PREF_FAVOURITE_PLANTS;

public class WidgetViewsService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetPlantAdapter(this, intent);
    }
}

class WidgetPlantAdapter implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private Intent intent;
    private List<Plant> mPlants = new ArrayList<>();

    public WidgetPlantAdapter(Context context, Intent intent) {

        this.context = context;
        this.intent = intent;
    }

    private void initData() {
        mPlants.clear();
        Set<String> idStringSet = PreferenceManager.getDefaultSharedPreferences(context).getStringSet(PREF_FAVOURITE_PLANTS, new HashSet<String>());

        if (idStringSet.size() > 0) {
            final List<Integer> ids = new ArrayList<>();
            for (String idString : idStringSet) ids.add(Integer.parseInt(idString));
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    AppDatabase db = AppDatabase.getInstance(context.getApplicationContext());
                    mPlants = db.plantsDao().getPlantsSync(ids);
                }
            });
            thread.start();
        }
    }

    @Override
    public void onCreate() {
        initData();
    }

    @Override
    public void onDataSetChanged() {
        initData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (mPlants == null) {
            return 0;
        } else {
            return mPlants.size();
        }
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteView = new RemoteViews(context.getPackageName(),
                android.R.layout.simple_list_item_1);
        if (CollectionUtils.isEmpty(mPlants)) {
            return null;
        }
        remoteView.setTextViewText(android.R.id.text1, mPlants.get(position).getName());
        remoteView.setTextColor(android.R.id.text1, Color.BLACK);

        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        if (CollectionUtils.isEmpty(mPlants)) {
            return 0;
        } else {
            return mPlants.get(position).getId();
        }
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
