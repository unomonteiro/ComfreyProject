package io.monteirodev.comfreyproject.ui.plants;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import io.monteirodev.comfreyproject.data.database.AppDatabase;

public class PlantDetailsViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase mDb;
    private final int mPlantId;

    PlantDetailsViewModelFactory(AppDatabase db, int plantId) {
        mDb = db;
        mPlantId = plantId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PlantDetailsViewModel(mDb, mPlantId);
    }
}
