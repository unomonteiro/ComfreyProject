package io.monteirodev.comfreyproject.ui.recipes;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import io.monteirodev.comfreyproject.data.RecipeDetail;
import io.monteirodev.comfreyproject.data.database.AppDatabase;
import io.monteirodev.comfreyproject.ui.DetailsAdapter;

public class RecipeDetailsFragment extends Fragment {

    private static final String KEY_RECIPE_ID = "key_recipe_id";

    @BindView(R.id.recipe_details_recycler_view)
    RecyclerView mRecyclerView;

    private DetailsAdapter mDetailsAdapter;

    private int mRecipeId;
    private Unbinder unbinder;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        if (savedInstanceState != null) {
            mRecipeId = savedInstanceState.getInt(KEY_RECIPE_ID, -1);
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDetailsAdapter = new DetailsAdapter();
        mRecyclerView.setAdapter(mDetailsAdapter);

        setupViewModel();

        return rootView;
    }

    private void setupViewModel() {
        FragmentActivity activity = getActivity();
        if (activity != null && mRecipeId != -1) {
            AppDatabase db = AppDatabase.getInstance(activity.getApplicationContext());
            RecipeDetailsViewModelFactory factory =
                    new RecipeDetailsViewModelFactory(db, mRecipeId);
            RecipeDetailsViewModel recipeDetailsViewModel =
                    ViewModelProviders.of(this, factory).get(RecipeDetailsViewModel.class);
            subscribeToModel(recipeDetailsViewModel);
        }
    }

    private void subscribeToModel(final RecipeDetailsViewModel model) {
        model.getRecipeDetails().observe(this, new Observer<List<RecipeDetail>>() {
            @Override
            public void onChanged(@Nullable List<RecipeDetail> recipeDetails) {
                model.getRecipeDetails().removeObserver(this);
                if (recipeDetails != null) {
                    mDetailsAdapter.setDetailList(recipeDetails);
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_RECIPE_ID, mRecipeId);
    }

    @Override
    public void onDestroyView() {
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
        super.onDestroyView();
    }

    public void setRecipeId(int recipeId) {
        mRecipeId = recipeId;
    }
}
