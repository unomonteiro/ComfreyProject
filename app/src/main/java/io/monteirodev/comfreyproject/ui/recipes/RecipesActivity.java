package io.monteirodev.comfreyproject.ui.recipes;

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
import io.monteirodev.comfreyproject.data.Recipe;

import static io.monteirodev.comfreyproject.ui.recipes.RecipeDetailsActivity.RECIPE_EXTRA;
import static io.monteirodev.comfreyproject.utils.UiUtils.getDeviceLayoutManager;

public class RecipesActivity extends AppCompatActivity implements
        RecipesAdapter.RecipesClickListener {

    private static final String RECIPE_DETAILS_FRAGMENT_KEY = "RECIPE_DETAILS_FRAGMENT_KEY";
    private static final String RECIPE_INDEX_KEY = "RECIPE_INDEX_KEY";

    @BindView(R.id.recipes_recycler_view)
    RecyclerView mRecyclerView;

    private RecipesAdapter mRecipesAdapter;
    private RecipeDetailsFragment mRecipeDetailsFragment;
    private boolean mIsTablet;
    private int mRecipeIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        ButterKnife.bind(this);

        mIsTablet = getResources().getBoolean(R.bool.is_tablet);
        LinearLayoutManager layoutManager = getDeviceLayoutManager(this);
        if (mIsTablet) {
            layoutManager = new LinearLayoutManager(this);
        }
        mRecyclerView.setLayoutManager(layoutManager);
        mRecipesAdapter = new RecipesAdapter(this);
        mRecyclerView.setAdapter(mRecipesAdapter);
        setupViewModel();

        if (mIsTablet) {
            if (savedInstanceState == null) {
                mRecipeIndex = RecyclerView.NO_POSITION;
                mRecipeDetailsFragment = new RecipeDetailsFragment();
            } else {
                mRecipeIndex = savedInstanceState.getInt(RECIPE_INDEX_KEY);
                mRecipeDetailsFragment = (RecipeDetailsFragment) getSupportFragmentManager()
                        .getFragment(savedInstanceState, RECIPE_DETAILS_FRAGMENT_KEY);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mIsTablet && mRecipeIndex != RecyclerView.NO_POSITION) {
            mRecyclerView.scrollToPosition(mRecipeIndex);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mIsTablet) {
            outState.putInt(RECIPE_INDEX_KEY, mRecipeIndex);
            getSupportFragmentManager().putFragment(
                    outState, RECIPE_DETAILS_FRAGMENT_KEY, mRecipeDetailsFragment);
        }
    }

    private void setupViewModel() {
        RecipesViewModel viewModel = ViewModelProviders.of(this).get(RecipesViewModel.class);
        viewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> newRecipes) {
                mRecipesAdapter.setRecipes(newRecipes);
                if (mIsTablet && !CollectionUtils.isEmpty(newRecipes)) {
                    if (mRecipeIndex == RecyclerView.NO_POSITION) {
                        mRecipeIndex = 0;
                    }
                    mRecipesAdapter.setSelectedPosition(mRecipeIndex);
                    replaceRecipeDetailsFragment(newRecipes.get(mRecipeIndex));
                }
            }
        });
    }

    @Override
    public void onRecipeClick(Recipe recipe, int index) {
        if (mIsTablet) {
            mRecipeIndex = index;
            setRecipeDetailsFragment(recipe);
        } else {
            Intent detailIntent = new Intent(this, RecipeDetailsActivity.class);
            detailIntent.putExtra(RECIPE_EXTRA, recipe);
            startActivity(detailIntent);
        }
    }

    private void setRecipeDetailsFragment(Recipe recipe) {
        mRecipeDetailsFragment = new RecipeDetailsFragment();
        replaceRecipeDetailsFragment(recipe);
    }

    private void replaceRecipeDetailsFragment(Recipe recipe) {
        setTitle(String.format(getString(R.string.recipes_with_name), recipe.getName()));
        mRecipeDetailsFragment.setRecipeId(recipe.getId());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_details_container, mRecipeDetailsFragment)
                .commit();
    }
}
