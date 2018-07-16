package io.monteirodev.comfreyproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.monteirodev.comfreyproject.database.MyMenuOption;

import static io.monteirodev.comfreyproject.database.MyMenuOption.ABOUT;
import static io.monteirodev.comfreyproject.database.MyMenuOption.GET_INVOLVED;
import static io.monteirodev.comfreyproject.database.MyMenuOption.PLANTS;
import static io.monteirodev.comfreyproject.database.MyMenuOption.RECIPES;

public class MainActivity extends AppCompatActivity implements MenuAdapter.MenuClickListener {

    @BindView(R.id.menu_recicler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.loading_view)
    View mLoadingView;

    private MenuAdapter mMenuAdapter;
    private List<MyMenuOption> mMyMenuOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMenuAdapter = new MenuAdapter(this);
        mRecyclerView.setAdapter(mMenuAdapter);
        mMyMenuOptions = getMyMenuOptions();
        mMenuAdapter.setOptions(mMyMenuOptions);

    }

    private List<MyMenuOption> getMyMenuOptions() {
        List<MyMenuOption> menuOptions = new ArrayList<>();
        menuOptions.add(new MyMenuOption(PLANTS, getString(R.string.plants), R.drawable.plants));
        menuOptions.add(new MyMenuOption(RECIPES, getString(R.string.recipes), R.drawable.recipes));
        menuOptions.add(new MyMenuOption(GET_INVOLVED, getString(R.string.get_involved), R.drawable.place_holder_image));
        menuOptions.add(new MyMenuOption(ABOUT, getString(R.string.about), R.drawable.place_holder_image));
        return menuOptions;
    }

    @Override
    public void onOptionClick(String option) {
        switch (option) {
            case PLANTS:
                startActivity(new Intent(this, PlantsActivity.class));
                break;
            case RECIPES:
                startActivity(new Intent(this, RecipesActivity.class));
                break;
            case GET_INVOLVED:
                startActivity(new Intent(this, GetInvolvedActivity.class));
                break;
            case ABOUT:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            default:
                Toast.makeText(this, "Oops", Toast.LENGTH_LONG).show();
        }

    }
}
