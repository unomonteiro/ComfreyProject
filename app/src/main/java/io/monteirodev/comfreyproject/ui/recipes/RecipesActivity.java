package io.monteirodev.comfreyproject.ui.recipes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.monteirodev.comfreyproject.R;
import io.monteirodev.comfreyproject.data.Ingredient;
import io.monteirodev.comfreyproject.data.Recipe;
import io.monteirodev.comfreyproject.data.Step;

import static io.monteirodev.comfreyproject.ui.recipes.RecipeDetailsActivity.RECIPE_EXTRA;
import static io.monteirodev.comfreyproject.utils.UiUtils.getDeviceLayoutManager;

public class RecipesActivity extends AppCompatActivity implements
        RecipesAdapter.RecipesClickListener {

    private static final String RECIPE_DETAILS_FRAGMENT_KEY = "RECIPE_DETAILS_FRAGMENT_KEY";
    private static final String RECIPE_INDEX_KEY = "RECIPE_INDEX_KEY";
    private static final String RECIPE_KEY = "RECIPE_KEY";

    @BindView(R.id.recipes_recycler_view)
    RecyclerView mRecyclerView;

    private RecipesAdapter mRecipesAdapter;
    private List<Recipe> mRecipeList;
    private RecipeDetailsFragment mRecipeDetailsFragment;
    private boolean mIsTablet;
    private Recipe mRecipe;
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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecipesAdapter = new RecipesAdapter(this);
        mRecyclerView.setAdapter(mRecipesAdapter);
        mRecipeList = getRecipes();
        mRecipesAdapter.setRecipes(mRecipeList);
        if (mIsTablet) {
            if (savedInstanceState == null) {
                mRecipeIndex = 0;
                mRecipe = mRecipeList.get(mRecipeIndex);
                mRecipesAdapter.setSelectedPosition(mRecipeIndex);
                setRecipeDetailsFragment(mRecipe);
            } else {
                mRecipe = savedInstanceState.getParcelable(RECIPE_KEY);
                mRecipeIndex = savedInstanceState.getInt(RECIPE_INDEX_KEY);
                mRecipesAdapter.setSelectedPosition(mRecipeIndex);
                mRecipeDetailsFragment = (RecipeDetailsFragment) getSupportFragmentManager()
                        .getFragment(savedInstanceState, RECIPE_DETAILS_FRAGMENT_KEY);
                replaceRecipeDetailsFragment(mRecipe);
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
            outState.putParcelable(RECIPE_KEY, mRecipe);
            outState.putInt(RECIPE_INDEX_KEY, mRecipeIndex);
            getSupportFragmentManager().putFragment(
                    outState, RECIPE_DETAILS_FRAGMENT_KEY, mRecipeDetailsFragment);
        }
    }

    public List<Recipe> getRecipes() {
        ArrayList<Recipe> newRecipes = new ArrayList<>();

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient(1f, "tbsp", "Ingredient A"));
        ingredients.add(new Ingredient(2f, "unit", "Ingredient B"));
        ingredients.add(new Ingredient(3f, "tbsp", "Ingredient C"));
        ingredients.add(new Ingredient(4f, "gram", "Ingredient D"));
        ingredients.add(new Ingredient(5f, "tbsp", "Ingredient E"));
        ingredients.add(new Ingredient(6f, "tbsp", "Ingredient F"));

        ArrayList<Step> steps = new ArrayList<>();
        steps.add(new Step(1, null, getString(R.string.lorem_m)));
        steps.add(new Step(2, null, getString(R.string.lorem_l)));
        steps.add(new Step(3, null, getString(R.string.lorem_xl)));
        steps.add(new Step(4, null, getString(R.string.lorem_xl)));

        newRecipes.add(new Recipe(1, "Hoppers with Eggs",
                "https://dl.dropboxusercontent.com/s/r7v0pknm99doobo/hoppers.jpg",
                ingredients, steps));
        newRecipes.add(new Recipe(2, "Kashk e Bademjan", "https://dl.dropboxusercontent.com/s/dgw93hlwrgkp8jx/Kashk.jpg",
                ingredients, steps));
        ArrayList<Ingredient> chestnutIngredients = new ArrayList<>();
        chestnutIngredients.add(new Ingredient(1f, "Unit", "Spouts"));
        newRecipes.add(new Recipe(3, "Chestnut and Brussel Sprouts Hotpot",
                null,
                chestnutIngredients, steps));
        return newRecipes;
    }

    @Override
    public void onRecipeClick(Recipe recipe, int index) {
        mRecipe = recipe;
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
        setTitle(String.format(getString(R.string.recipes_with_name), mRecipe.getName()));
        mRecipeDetailsFragment.setRecipe(recipe);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_details_container, mRecipeDetailsFragment)
                .commit();
    }
}
