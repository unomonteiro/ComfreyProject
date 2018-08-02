package io.monteirodev.comfreyproject.data;

import android.arch.persistence.room.Ignore;

import java.util.ArrayList;

public class About {
    @Ignore
    private ArrayList<AboutDetail> details;

    public ArrayList<AboutDetail> getDetails() {
        return details;
    }
}
