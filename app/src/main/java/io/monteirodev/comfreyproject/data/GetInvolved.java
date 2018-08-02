package io.monteirodev.comfreyproject.data;

import android.arch.persistence.room.Ignore;

import java.util.ArrayList;

public class GetInvolved {
    @Ignore
    private ArrayList<GetInvolvedDetail> details;

    public ArrayList<GetInvolvedDetail> getDetails() {
        return details;
    }
}
