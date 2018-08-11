package io.monteirodev.comfreyproject.ui.plants;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.monteirodev.comfreyproject.R;
import io.monteirodev.comfreyproject.data.Plant;
import io.monteirodev.comfreyproject.widget.WidgetIntentService;

import static io.monteirodev.comfreyproject.ui.plants.PlantDetailsActivity.PLANT_EXTRA;
import static io.monteirodev.comfreyproject.ui.plants.PlantDetailsActivity.PREF_FAVOURITE_PLANTS;
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
    private MenuItem mFavourite;
    private Plant mPlant;


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
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mIsTablet) {
            getMenuInflater().inflate(R.menu.favourite_menu, menu);
            mFavourite = menu.findItem(R.id.action_favourite);
            mFavourite.setIcon(getFavouriteIcon());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_favourite) {
            updateFavourite();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private int getFavouriteIcon() {
        return isFavourite() ? R.drawable.ic_heart : R.drawable.ic_heart_outline;
    }

    private boolean isFavourite() {
        Set<String> favouritesSet = getFavouriteSet();
        String plantId = String.valueOf(mPlant.getId());
        return favouritesSet.contains(plantId);
    }

    @NonNull
    private Set<String> getFavouriteSet() {
        return PreferenceManager.getDefaultSharedPreferences(this)
                .getStringSet(PREF_FAVOURITE_PLANTS, new HashSet<String>());
    }


    private void updateFavourite() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPrefs.edit();

        // https://stackoverflow.com/a/14034804/1329854
        Set<String> favourites = new HashSet<>(
                sharedPrefs.getStringSet(PREF_FAVOURITE_PLANTS, new HashSet<String>()));

        String plantId = String.valueOf(mPlant.getId());
        if (favourites.contains(plantId)) {
            favourites.remove(plantId);
        } else {
            favourites.add(plantId);
        }

        editor.putStringSet(PREF_FAVOURITE_PLANTS, favourites)
                .apply();
        WidgetIntentService.startActionUpdatePlants(this);
        updateFavouriteIcons(getFavouriteIcon());
    }

    private void updateFavouriteIcons(int favouriteIcon) {
        if (mFavourite != null) {
            mFavourite.setIcon(favouriteIcon);
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
                    mPlant = newPlants.get(mPlantIndex);
                    replacePlantDetailsFragment(mPlant);
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
        mPlant = plant;
        setTitle(String.format(getString(R.string.plants_with_name), plant.getName()));
        updateFavouriteIcons(getFavouriteIcon());
        mPlantDetailsFragment.setPlantId(plant.getId());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.plant_details_container, mPlantDetailsFragment)
                .commit();
    }
}
