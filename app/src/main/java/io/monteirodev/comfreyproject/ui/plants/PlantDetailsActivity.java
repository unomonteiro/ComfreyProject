package io.monteirodev.comfreyproject.ui.plants;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.monteirodev.comfreyproject.R;
import io.monteirodev.comfreyproject.data.Plant;
import io.monteirodev.comfreyproject.utils.GlideApp;
import io.monteirodev.comfreyproject.widget.WidgetIntentService;

import static io.monteirodev.comfreyproject.utils.Constants.PREF_FAVOURITE_PLANTS;

public class PlantDetailsActivity extends AppCompatActivity {

    public static final String PLANT_EXTRA = "io.monteirodev.comfreyproject.ui.plants.plant_id";

    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.plant_image)
    ImageView mPlantImageView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fab)
    FloatingActionButton mFloatingActionButton;

    private Menu mMenu;
    private Plant mPlant;
    private MenuItem mFavourite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_details);
        ButterKnife.bind(this);

        mPlant = getIntent().getParcelableExtra(PLANT_EXTRA);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(mPlant.getName());
        }
        mCollapsingToolbarLayout.setTitle(mPlant.getName());

        if (savedInstanceState == null) {
            PlantDetailsFragment plantDetailsFragment = new PlantDetailsFragment();
            plantDetailsFragment.setPlantId(mPlant.getId());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.plant_container, plantDetailsFragment)
                    .commit();
        }

        if (TextUtils.isEmpty(mPlant.getImageUrl())) {
            mPlantImageView.setImageResource(R.drawable.wide_image_placeholder);
        } else {
            GlideApp.with(this)
                    .load(mPlant.getImageUrl())
                    .into(mPlantImageView);
        }

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;
                    showOption(R.id.action_favourite);
                } else if (isShow) {
                    isShow = false;
                    hideOption(R.id.action_favourite);
                }
            }
        });

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFavourite();
            }
        });
        mFloatingActionButton.setImageResource(getFavouriteIcon());
    }

    private void updateFavouriteIcons(int favouriteIcon) {
        if (mFavourite != null) {
            mFavourite.setIcon(favouriteIcon);
        }
        if (mFloatingActionButton != null) {
            mFloatingActionButton.setImageResource(favouriteIcon);
        }
    }

    private int getFavouriteIcon() {
        return isFavourite() ? R.drawable.ic_heart : R.drawable.ic_heart_outline;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenu = menu;
        getMenuInflater().inflate(R.menu.favourite_menu, menu);
        mFavourite = menu.findItem(R.id.action_favourite);
        mFavourite.setIcon(getFavouriteIcon());
        hideOption(R.id.action_favourite);
        return true;
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

    private void hideOption(int id) {
        MenuItem item = mMenu.findItem(id);
        item.setVisible(false);
    }

    private void showOption(int id) {
        MenuItem item = mMenu.findItem(id);
        item.setVisible(true);
    }

    private void updateFavourite() {

        Set<String> favouritesSet = getFavouriteSet();
        String plantId = String.valueOf(mPlant.getId());

        if (isFavourite(favouritesSet, plantId)) {
            favouritesSet.remove(plantId);
        } else {
            favouritesSet.add(plantId);
        }

        PreferenceManager.getDefaultSharedPreferences(this)
                .edit()
                .putStringSet(PREF_FAVOURITE_PLANTS, favouritesSet)
                .apply();
        WidgetIntentService.startActionUpdatePlants(this);
        updateFavouriteIcons(getFavouriteIcon());
    }

    private boolean isFavourite() {
        Set<String> favouritesSet = getFavouriteSet();
        String plantId = String.valueOf(mPlant.getId());
        return isFavourite(favouritesSet, plantId);
    }

    private boolean isFavourite(Set<String> favouritesSet, String plantId) {
        return favouritesSet.contains(plantId);
    }

    @NonNull
    private Set<String> getFavouriteSet() {
        return PreferenceManager.getDefaultSharedPreferences(this)
                    .getStringSet(PREF_FAVOURITE_PLANTS, new HashSet<String>());
    }
}
