package io.monteirodev.comfreyproject.ui.plants;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
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
    private static final String PLANT_KEY = "PLANT_KEY";

    @BindView(R.id.plants_recycler_view)
    RecyclerView mRecyclerView;

    private PlantsAdapter mPlantsAdapter;
    private List<Plant> mPlantList;
    private PlantDetailsFragment mPlantDetailsFragment;
    private boolean mIsTablet;
    private Plant mPlant;
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
        mPlantList = getPlants();
        mPlantsAdapter.setPlantList(mPlantList);
        if (mIsTablet) {
            if (savedInstanceState == null) {
                mPlantIndex = 0;
                mPlant = mPlantList.get(mPlantIndex);
                mPlantsAdapter.setSelectedPosition(mPlantIndex);
                setPlantDetailsFragment(mPlant);
            } else {
                mPlant = savedInstanceState.getParcelable(PLANT_KEY);
                mPlantIndex = savedInstanceState.getInt(PLANT_INDEX_KEY);
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
            outState.putInt(PLANT_INDEX_KEY, mPlantIndex);
            getSupportFragmentManager().putFragment(
                    outState, PLANT_DETAILS_FRAGMENT_KEY, mPlantDetailsFragment);
        }
    }

    public List<Plant> getPlants() {
        List<Plant> newPlants = new ArrayList<>();
        newPlants.add(new Plant(1, "Couve", "https://dl.dropboxusercontent.com/s/vd0cbsiraddueix/couve_galega.jpg", "Belmira", null, "Some long information about the plant to be filled in later"));
        newPlants.add(new Plant(1, "Lettuce", "https://dl.dropboxusercontent.com/s/7sp2bsplynb3zks/lettuce.jpg", "Lucinda", null, "Some long information about the plant to be filled in later"));
        newPlants.add(new Plant(1, "Tomato", null, "Tomas", null, "Some long information about the plant to be filled in later"));
        return newPlants;
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
        setTitle(String.format(getString(R.string.plants_with_name), mPlant.getName()));
        mPlantDetailsFragment.setPlant(plant);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.plant_details_container, mPlantDetailsFragment)
                .commit();
    }
}
