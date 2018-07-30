package io.monteirodev.comfreyproject.ui.plants;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import io.monteirodev.comfreyproject.data.Plant;
import io.monteirodev.comfreyproject.data.database.AppDatabase;

public class PlantsViewModel extends AndroidViewModel {

    private LiveData<List<Plant>> plants;

    public PlantsViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(this.getApplication());
        plants = db.plantsDao().loadPlants();
    }

    public LiveData<List<Plant>> getPlants() {
        return plants;
    }
}
