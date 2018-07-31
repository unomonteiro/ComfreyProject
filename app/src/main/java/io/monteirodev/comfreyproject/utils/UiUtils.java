package io.monteirodev.comfreyproject.utils;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.text.Spanned;
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

    /**
     * Html.fromHtml deprecated in Android N
     * https://stackoverflow.com/a/37905107/6997703
     */
    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(html);
        }
    }

}
