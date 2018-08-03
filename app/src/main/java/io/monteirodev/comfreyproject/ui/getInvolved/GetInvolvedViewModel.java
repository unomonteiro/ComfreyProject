package io.monteirodev.comfreyproject.ui.getInvolved;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import io.monteirodev.comfreyproject.data.GetInvolvedDetail;
import io.monteirodev.comfreyproject.data.database.AppDatabase;

public class GetInvolvedViewModel extends AndroidViewModel {

    private LiveData<List<GetInvolvedDetail>> details;

    public GetInvolvedViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(this.getApplication());
        details = db.getInvolvedDetailsDao().loadGetInvolvedDetails();
    }

    public LiveData<List<GetInvolvedDetail>> getDetails() {
        return details;
    }
}
