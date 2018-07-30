package io.monteirodev.comfreyproject.ui.plants;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import io.monteirodev.comfreyproject.data.Plant;
import io.monteirodev.comfreyproject.data.database.AppDatabase;

public class PlantDetailsViewModel extends ViewModel {
    private LiveData<Plant> plant;

    public PlantDetailsViewModel(AppDatabase db, int plantId) {
        plant = db.plantsDao().loadPlant(plantId);
    }

    public LiveData<Plant> getPlant() {
        return plant;
    }
}
