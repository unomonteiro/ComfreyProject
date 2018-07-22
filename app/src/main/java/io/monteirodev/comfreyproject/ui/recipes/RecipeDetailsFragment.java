package io.monteirodev.comfreyproject.ui.recipes;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.monteirodev.comfreyproject.R;
import io.monteirodev.comfreyproject.data.Recipe;

public class RecipeDetailsFragment extends Fragment {

    @BindView(R.id.recipe_details_recycler_view)
    RecyclerView mRecyclerView;

    private Recipe mRecipe;
    private Unbinder unbinder;

    public RecipeDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        if (mRecipe != null) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            RecipeDetailsAdapter detailsAdapter = new RecipeDetailsAdapter();
            detailsAdapter.setIngredients(mRecipe.getIngredients());
            detailsAdapter.setSteps(mRecipe.getSteps());
            mRecyclerView.setAdapter(detailsAdapter);
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
        super.onDestroyView();
    }

    public void setRecipe(Recipe recipe) {
        mRecipe = recipe;
    }
}
