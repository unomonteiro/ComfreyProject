package io.monteirodev.comfreyproject.ui.about;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import io.monteirodev.comfreyproject.data.AboutDetail;
import io.monteirodev.comfreyproject.data.database.AppDatabase;

public class AboutViewModel extends AndroidViewModel {

    private LiveData<List<AboutDetail>> details;

    public AboutViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(this.getApplication());
        details = db.aboutDetailsDao().loadAboutDetails();
    }

    public LiveData<List<AboutDetail>> getDetails() {
        return details;
    }
}
