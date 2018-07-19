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

import static io.monteirodev.comfreyproject.ui.recipes.RecipeDetailsActivity.RECIPE_KEY;

public class RecipesActivity extends AppCompatActivity implements
        RecipesAdapter.RecipesClickListener {

    @BindView(R.id.recipes_recicler_view)
    RecyclerView mRecyclerView;

    private RecipesAdapter mRecipesAdapter;
    private List<Recipe> mRecipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        ButterKnife.bind(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecipesAdapter = new RecipesAdapter(this);
        mRecyclerView.setAdapter(mRecipesAdapter);
        mRecipeList = getRecipes();
        mRecipesAdapter.setRecipes(mRecipeList);
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
        newRecipes.add(new Recipe(3, "Chestnut and Brussel Sprouts Hotpot",
                null,
                ingredients, steps));
        return newRecipes;
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        Intent detailIntent = new Intent(this, RecipeDetailsActivity.class);
        detailIntent.putExtra(RECIPE_KEY, recipe);
        startActivity(detailIntent);

    }
}
