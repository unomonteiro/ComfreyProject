package io.monteirodev.comfreyproject.ui.recipes;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.monteirodev.comfreyproject.R;
import io.monteirodev.comfreyproject.data.Recipe;
import io.monteirodev.comfreyproject.utils.GlideApp;

public class RecipeDetailsActivity extends AppCompatActivity {

    public static final String RECIPE_KEY = "io.monteirodev.comfreyproject.ui.plants.recipe_key";

    @BindView(R.id.recipe_image)
    ImageView mRecipeImageView;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(RECIPE_KEY)) {
            mRecipe = intent.getParcelableExtra(RECIPE_KEY);

        }
        if (mRecipe == null) {
            finish();
            Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
        }

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setTitle(mRecipe.getName());
        }
        mCollapsingToolbarLayout.setTitle(mRecipe.getName());

        if (savedInstanceState == null) {
            RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();
            recipeDetailsFragment.setRecipe(mRecipe);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.recipe_container, recipeDetailsFragment)
                    .commit();
        }

        if (TextUtils.isEmpty(mRecipe.getImage())) {
            mRecipeImageView.setImageResource(R.drawable.wide_image_placeholder);
        } else {
            GlideApp.with(this)
                    .load(mRecipe.getImage())
                    .into(mRecipeImageView);
        }
    }
}
