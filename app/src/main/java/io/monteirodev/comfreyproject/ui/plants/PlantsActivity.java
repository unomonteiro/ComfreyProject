package io.monteirodev.comfreyproject.ui.plants;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.monteirodev.comfreyproject.R;
import io.monteirodev.comfreyproject.data.Plant;

import static io.monteirodev.comfreyproject.ui.plants.PlantDetailsActivity.PLANT_ID_KEY;

public class PlantsActivity extends AppCompatActivity implements PlantsAdapter.PlantClickListener {

    @BindView(R.id.plants_recicler_view)
    RecyclerView mRecyclerView;

    private PlantsAdapter mPlantsAdapter;
    private List<Plant> mPlantList;
    private List<Plant> mPlants;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plants);
        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPlantsAdapter = new PlantsAdapter(this);
        mRecyclerView.setAdapter(mPlantsAdapter);
        mPlantList = getPlants();
        mPlantsAdapter.setPlantList(mPlantList);
    }

    public List<Plant> getPlants() {
        List<Plant> newPlants = new ArrayList<>();
        newPlants.add(new Plant(1, "Couve", "https://www.dropbox.com/s/vd0cbsiraddueix/couve_galega.jpg?dl=0", "Belmira", null, "Some long information about the plant to be filled in later"));
        newPlants.add(new Plant(1, "Couve", null, "Belmira", null, "Some long information about the plant to be filled in later"));
        return newPlants;
    }

    @Override
    public void onPlantClick(Plant plant) {
        Intent detailIntent = new Intent(this, PlantDetailsActivity.class);
        detailIntent.putExtra(PLANT_ID_KEY, plant.getId());
        startActivity(detailIntent);
    }
}
