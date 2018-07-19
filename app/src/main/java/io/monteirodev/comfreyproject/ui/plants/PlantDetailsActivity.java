package io.monteirodev.comfreyproject.ui.plants;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.monteirodev.comfreyproject.R;
import io.monteirodev.comfreyproject.data.Plant;
import io.monteirodev.comfreyproject.utils.GlideApp;

public class PlantDetailsActivity extends AppCompatActivity {

    public static final String PLANT_ID_KEY = "plant_id";
    public static final String PLANT_KEY = "plant_id";

    @BindView(R.id.plant_image)
    ImageView mPlantImageView;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    private Plant mPlant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_details);
        ButterKnife.bind(this);

        mPlant = getIntent().getParcelableExtra(PLANT_KEY);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(mPlant.getName());
        }
        mCollapsingToolbarLayout.setTitle(mPlant.getName());

        if (savedInstanceState == null) {
            PlantDetailsFragment plantDetailsFragment = new PlantDetailsFragment();
            plantDetailsFragment.setPlant(mPlant);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.plant_container, plantDetailsFragment)
                    .commit();
        }
        
        if (TextUtils.isEmpty(mPlant.getImageUrl())) {
            mPlantImageView.setImageResource(R.drawable.wide_image_placeholder);
        } else {
            GlideApp.with(this)
                    .load(mPlant.getImageUrl())
                    .into(mPlantImageView);
        }
    }
}
