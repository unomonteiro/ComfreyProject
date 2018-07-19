package io.monteirodev.comfreyproject.utils;

import android.content.ContentValues;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.Spanned;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.monteirodev.comfreyproject.R;
import io.monteirodev.comfreyproject.data.Ingredient;

public class RecipeUtils {

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

    @NonNull
    public static List<String> getIngredientsString(@NonNull Context context, List<Ingredient> ingredients, String prefix) {
        List<String> ingredientsString = new ArrayList<>();
        for (Ingredient ingredientObj : ingredients) {
            DecimalFormat df = new DecimalFormat("0.##");
            String quantity = df.format((float) ingredientObj.getQuantity());
            String measure = ingredientObj.getMeasure();
            String ingredient = ingredientObj.getIngredient();
            ingredientsString.add(context.getString(
                    R.string.quantity_measure_ingredient, prefix, quantity, measure, ingredient));
        }
        return ingredientsString;
    }
}
