package io.monteirodev.comfreyproject.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.monteirodev.comfreyproject.R;
import io.monteirodev.comfreyproject.data.MyMenuOption;
import io.monteirodev.comfreyproject.sync.SyncUtils;
import io.monteirodev.comfreyproject.ui.about.AboutActivity;
import io.monteirodev.comfreyproject.ui.getInvolved.GetInvolvedActivity;
import io.monteirodev.comfreyproject.ui.plants.PlantsActivity;
import io.monteirodev.comfreyproject.ui.recipes.RecipesActivity;

import static io.monteirodev.comfreyproject.data.MyMenuOption.ABOUT;
import static io.monteirodev.comfreyproject.data.MyMenuOption.GET_INVOLVED;
import static io.monteirodev.comfreyproject.data.MyMenuOption.PLANTS;
import static io.monteirodev.comfreyproject.data.MyMenuOption.RECIPES;
import static io.monteirodev.comfreyproject.utils.UiUtils.getDeviceLayoutManager;

public class MainActivity extends AppCompatActivity implements MenuAdapter.MenuClickListener {

    @BindView(R.id.menu_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.loading_view)
    View mLoadingView;

    private MenuAdapter mMenuAdapter;
    private List<MyMenuOption> mMyMenuOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(this))
                        .build());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(getDeviceLayoutManager(this));
        mMenuAdapter = new MenuAdapter(this);
        mRecyclerView.setAdapter(mMenuAdapter);
        mMyMenuOptions = getMyMenuOptions();
        mMenuAdapter.setOptions(mMyMenuOptions);
        SyncUtils.initialise(this);

    }

    private List<MyMenuOption> getMyMenuOptions() {
        List<MyMenuOption> menuOptions = new ArrayList<>();
        menuOptions.add(new MyMenuOption(PLANTS, getString(R.string.plants), R.drawable.plants));
        menuOptions.add(new MyMenuOption(RECIPES, getString(R.string.recipes), R.drawable.recipes));
        menuOptions.add(new MyMenuOption(GET_INVOLVED, getString(R.string.get_involved), R.drawable.get_involved));
        menuOptions.add(new MyMenuOption(ABOUT, getString(R.string.about), R.drawable.about));
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
                Toast.makeText(this, R.string.error_message, Toast.LENGTH_LONG).show();
        }

    }
}
