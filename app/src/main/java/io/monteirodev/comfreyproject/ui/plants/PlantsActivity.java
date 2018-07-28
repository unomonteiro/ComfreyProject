package io.monteirodev.comfreyproject.ui.plants;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.common.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.monteirodev.comfreyproject.R;
import io.monteirodev.comfreyproject.data.Plant;
import io.monteirodev.comfreyproject.data.database.AppDatabase;

import static io.monteirodev.comfreyproject.ui.plants.PlantDetailsActivity.PLANT_EXTRA;
import static io.monteirodev.comfreyproject.utils.UiUtils.getDeviceLayoutManager;

public class PlantsActivity extends AppCompatActivity implements 
        PlantsAdapter.PlantClickListener {

    private static final String PLANT_DETAILS_FRAGMENT_KEY = "PLANT_DETAILS_FRAGMENT_KEY";
    private static final String PLANT_LIST_KEY = "PLANT_LIST_KEY";
    private static final String PLANT_INDEX_KEY = "PLANT_INDEX_KEY";
    private static final String PLANT_KEY = "PLANT_KEY";

    @BindView(R.id.plants_recycler_view)
    RecyclerView mRecyclerView;

    private PlantsAdapter mPlantsAdapter;
    private ArrayList<Plant> mPlantList;
    private PlantDetailsFragment mPlantDetailsFragment;
    private boolean mIsTablet;
    private Plant mPlant;
    private int mPlantIndex;
    private AppDatabase mDb;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plants);
        ButterKnife.bind(this);
        mDb = AppDatabase.getInstance(getApplicationContext());
        mIsTablet = getResources().getBoolean(R.bool.is_tablet);
        LinearLayoutManager layoutManager = getDeviceLayoutManager(this);
        if (mIsTablet) {
            layoutManager = new LinearLayoutManager(this);
        }
        mRecyclerView.setLayoutManager(layoutManager);
        mPlantsAdapter = new PlantsAdapter(this);
        mRecyclerView.setAdapter(mPlantsAdapter);
        retrievePlants();

        if (mIsTablet) {
            if (savedInstanceState == null) {
                mPlantIndex = RecyclerView.NO_POSITION;
            } else {
                mPlant = savedInstanceState.getParcelable(PLANT_KEY);
                mPlantList = savedInstanceState.getParcelableArrayList(PLANT_LIST_KEY);
                mPlantIndex = savedInstanceState.getInt(PLANT_INDEX_KEY);
                mPlantsAdapter.setPlantList(mPlantList);
                mPlantsAdapter.setSelectedPosition(mPlantIndex);
                mPlantDetailsFragment = (PlantDetailsFragment) getSupportFragmentManager()
                        .getFragment(savedInstanceState, PLANT_DETAILS_FRAGMENT_KEY);
                replacePlantDetailsFragment(mPlant);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mIsTablet && mPlantIndex != RecyclerView.NO_POSITION) {
            mRecyclerView.scrollToPosition(mPlantIndex);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mIsTablet) {
            outState.putParcelable(PLANT_KEY, mPlant);
            outState.putParcelableArrayList(PLANT_LIST_KEY, mPlantList);
            outState.putInt(PLANT_INDEX_KEY, mPlantIndex);
            getSupportFragmentManager().putFragment(
                    outState, PLANT_DETAILS_FRAGMENT_KEY, mPlantDetailsFragment);
        }
    }

    private void retrievePlants() {
        final LiveData<List<Plant>> plants = mDb.plantsDao().loadPlants();
        plants.observe(this, new Observer<List<Plant>>() {
            @Override
            public void onChanged(@Nullable List<Plant> newPlants) {
                mPlantsAdapter.setPlantList(newPlants);
                if (mIsTablet && !CollectionUtils.isEmpty(newPlants) &&
                        mPlantIndex == RecyclerView.NO_POSITION) {
                    mPlantsAdapter.setSelectedPosition(mPlantIndex);
                    setPlantDetailsFragment(newPlants.get(0));
                }
            }
        });
    }

    @Override
    public void onPlantClick(Plant plant, int index) {
        mPlant = plant;
        if (mIsTablet) {
            mPlantIndex = index;
            setPlantDetailsFragment(plant);
        } else {
            Intent detailIntent = new Intent(this, PlantDetailsActivity.class);
            detailIntent.putExtra(PLANT_EXTRA, plant);
            startActivity(detailIntent);
        }
    }

    private void setPlantDetailsFragment(Plant plant) {
        mPlantDetailsFragment = new PlantDetailsFragment();
        replacePlantDetailsFragment(plant);
    }

    private void replacePlantDetailsFragment(Plant plant) {
        mPlant = plant;
        setTitle(String.format(getString(R.string.plants_with_name), plant.getName()));
        mPlantDetailsFragment.setPlant(plant);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.plant_details_container, mPlantDetailsFragment)
                .commit();
    }
}
