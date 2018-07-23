package io.monteirodev.comfreyproject.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;

public class UiUtils {

    public static LinearLayoutManager getDeviceLayoutManager(@NonNull Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int numColumns = (int) (dpWidth / 256);
        if (numColumns > 1) {
            return new GridLayoutManager(context, numColumns);
        } else {
            return new LinearLayoutManager(context);
        }
    }

}
