package io.monteirodev.comfreyproject.ui.recipes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.monteirodev.comfreyproject.R;
import io.monteirodev.comfreyproject.data.Recipe;

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
        List<Recipe> newRecipes = new ArrayList<>();
        newRecipes.add(new Recipe(1, "Hoppers with Eggs", "https://dl.dropboxusercontent.com/s/r7v0pknm99doobo/hoppers.jpg"));
        newRecipes.add(new Recipe(2, "Kashk e Bademjan", "https://dl.dropboxusercontent.com/s/dgw93hlwrgkp8jx/Kashk.jpg"));
        newRecipes.add(new Recipe(3, "Chestnut and Brussel Sprouts Hotpot", null));
        return newRecipes;
    }

    @Override
    public void onRecipeClick(Recipe recipe) {

    }
}
