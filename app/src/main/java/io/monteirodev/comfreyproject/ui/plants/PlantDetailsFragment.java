package io.monteirodev.comfreyproject.ui.plants;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.monteirodev.comfreyproject.R;
import io.monteirodev.comfreyproject.data.PlantDetail;
import io.monteirodev.comfreyproject.data.Recipe;
import io.monteirodev.comfreyproject.data.database.AppDatabase;
import io.monteirodev.comfreyproject.ui.recipes.RecipeDetailsActivity;

import static io.monteirodev.comfreyproject.ui.recipes.RecipeDetailsActivity.RECIPE_EXTRA;

public class PlantDetailsFragment extends Fragment implements
        PlantDetailsAdapter.RecipeClickListener {

    private static final String KEY_PLANT_ID = "key_plant_id";

    @BindView(R.id.plant_details_recycler_view)
    RecyclerView mRecyclerView;

    private PlantDetailsAdapter mPlantDetailsAdapter;

    private int mPlantId = -1;
    private Unbinder unbinder;
    private AppDatabase mDb;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_plant_details, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        if (savedInstanceState != null) {
            mPlantId = savedInstanceState.getInt(KEY_PLANT_ID, -1);
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mPlantDetailsAdapter = new PlantDetailsAdapter(this);
        mRecyclerView.setAdapter(mPlantDetailsAdapter);

        setupViewModel();

        return rootView;
    }

    private void setupViewModel() {
        FragmentActivity activity = getActivity();
        if (activity != null && mPlantId != -1) {
            mDb = AppDatabase.getInstance(activity.getApplicationContext());
            PlantDetailsViewModelFactory factory =
                    new PlantDetailsViewModelFactory(mDb, mPlantId);
            PlantDetailsViewModel plantDetailsViewModel =
                    ViewModelProviders.of(this, factory).get(PlantDetailsViewModel.class);
            subscribeToModel(plantDetailsViewModel);
        }
    }

    private void subscribeToModel(final PlantDetailsViewModel model) {
        model.getPlantDetails().observe(this, new Observer<List<PlantDetail>>() {
            @Override
            public void onChanged(@Nullable List<PlantDetail> plantDetails) {
                model.getPlantDetails().removeObserver(this);
                if (plantDetails != null) {
                    mPlantDetailsAdapter.setDetails(plantDetails);
                }
            }
        });
        model.getRecipe().observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(@Nullable Recipe recipe) {
                model.getRecipe().removeObserver(this);
                if (recipe != null) {
                    mPlantDetailsAdapter.setRecipe(recipe);
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_PLANT_ID, mPlantId);
    }

    @Override
    public void onDestroyView() {
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
        super.onDestroyView();
    }

    public void setPlantId(int plantId) {
        mPlantId = plantId;
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        Intent detailIntent = new Intent(getActivity(), RecipeDetailsActivity.class);
        detailIntent.putExtra(RECIPE_EXTRA, recipe);
        startActivity(detailIntent);
    }
}
