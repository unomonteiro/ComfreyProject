package io.monteirodev.comfreyproject.ui.plants;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.monteirodev.comfreyproject.R;

public class PlantDetailsActivity extends AppCompatActivity {

    public static final String PLANT_ID_KEY = "plant_id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_details);
    }
}
