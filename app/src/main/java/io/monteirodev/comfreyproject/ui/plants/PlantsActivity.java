package io.monteirodev.comfreyproject.ui.plants;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.common.util.CollectionUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.monteirodev.comfreyproject.R;
import io.monteirodev.comfreyproject.data.Plant;

import static io.monteirodev.comfreyproject.ui.plants.PlantDetailsActivity.PLANT_EXTRA;
import static io.monteirodev.comfreyproject.utils.UiUtils.getDeviceLayoutManager;

public class PlantsActivity extends AppCompatActivity implements 
        PlantsAdapter.PlantClickListener {

    private static final String PLANT_DETAILS_FRAGMENT_KEY = "PLANT_DETAILS_FRAGMENT_KEY";
    private static final String PLANT_INDEX_KEY = "PLANT_INDEX_KEY";

    @BindView(R.id.plants_recycler_view)
    RecyclerView mRecyclerView;

    private PlantsAdapter mPlantsAdapter;
    private PlantDetailsFragment mPlantDetailsFragment;
    private boolean mIsTablet;
    private int mPlantIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plants);
        ButterKnife.bind(this);
        mIsTablet = getResources().getBoolean(R.bool.is_tablet);
        LinearLayoutManager layoutManager = getDeviceLayoutManager(this);
        if (mIsTablet) {
            layoutManager = new LinearLayoutManager(this);
        }
        mRecyclerView.setLayoutManager(layoutManager);
        mPlantsAdapter = new PlantsAdapter(this);
        mRecyclerView.setAdapter(mPlantsAdapter);
        setupViewModel();

        if (mIsTablet) {
            if (savedInstanceState == null) {
                mPlantIndex = RecyclerView.NO_POSITION;
                mPlantDetailsFragment = new PlantDetailsFragment();
            } else {
                mPlantIndex = savedInstanceState.getInt(PLANT_INDEX_KEY);
                mPlantDetailsFragment = (PlantDetailsFragment) getSupportFragmentManager()
                        .getFragment(savedInstanceState, PLANT_DETAILS_FRAGMENT_KEY);
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
            outState.putInt(PLANT_INDEX_KEY, mPlantIndex);
            getSupportFragmentManager().putFragment(
                    outState, PLANT_DETAILS_FRAGMENT_KEY, mPlantDetailsFragment);
        }
    }

    private void setupViewModel() {
        PlantsViewModel viewModel = ViewModelProviders.of(this).get(PlantsViewModel.class);
        viewModel.getPlants().observe(this, new Observer<List<Plant>>() {
            @Override
            public void onChanged(@Nullable List<Plant> newPlants) {
                mPlantsAdapter.setPlantList(newPlants);
                if (mIsTablet && !CollectionUtils.isEmpty(newPlants)) {
                    if (mPlantIndex == RecyclerView.NO_POSITION) {
                        mPlantIndex = 0;
                    }
                    mPlantsAdapter.setSelectedPosition(mPlantIndex);
                    replacePlantDetailsFragment(newPlants.get(mPlantIndex));
                }
            }
        });
    }

    @Override
    public void onPlantClick(Plant plant, int index) {
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
        setTitle(String.format(getString(R.string.plants_with_name), plant.getName()));
        mPlantDetailsFragment.setPlantId(plant.getId());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.plant_details_container, mPlantDetailsFragment)
                .commit();
    }
}
