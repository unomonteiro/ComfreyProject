package io.monteirodev.comfreyproject.ui.plants;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.monteirodev.comfreyproject.R;
import io.monteirodev.comfreyproject.data.Plant;
import io.monteirodev.comfreyproject.data.database.AppDatabase;

public class PlantDetailsFragment extends Fragment {

    private static final String KEY_PLANT_ID = "key_plant_id";

    @BindView(R.id.expert_image_view)
    ImageView mExpertImageView;
    @BindView(R.id.expert_title_text_view)
    TextView mExpertNameTextView;
    @BindView(R.id.expert_body_text_view)
    TextView mExpertBodyTextView;

    @BindView(R.id.info_body_text_view)
    TextView mInfoBodyTextView;
    @BindView(R.id.benefits_body_text_view)
    TextView mBenefitsBodyTextView;
    @BindView(R.id.care_body_text_view)
    TextView mCareBodyTextView;

    private int mPlantId = -1;
    private Unbinder unbinder;
    private AppDatabase mDb;


    public PlantDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_plant_details, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        if (savedInstanceState != null) {
            mPlantId = savedInstanceState.getInt(KEY_PLANT_ID, -1);
        }

        setupViewModel();

        return rootView;
    }

    private void setupViewModel() {
        FragmentActivity activity = getActivity();
        if (activity != null && mPlantId != -1) {
            mDb = AppDatabase.getInstance(activity.getApplicationContext());
            PlantDetailsViewModelFactory factory = new PlantDetailsViewModelFactory(mDb, mPlantId);
            PlantDetailsViewModel plantDetailsViewModel =
                    ViewModelProviders.of(this, factory).get(PlantDetailsViewModel.class);
            subscribeToModel(plantDetailsViewModel);
        }
    }

    private void subscribeToModel(final PlantDetailsViewModel model) {
        model.getPlant().observe(this, new Observer<Plant>() {
            @Override
            public void onChanged(@Nullable Plant plant) {
                model.getPlant().removeObserver(this);
                if (plant != null) {
                    populateUI(plant);
                }
            }
        });
    }

    private void populateUI(@NonNull Plant plant) {
        mExpertNameTextView.setText(plant.getExpertName());
        mExpertImageView.setImageResource(R.drawable.profile_placeholder);
        mExpertBodyTextView.setText(R.string.lorem_expert);
        mInfoBodyTextView.setText(R.string.lorem_m);
        mBenefitsBodyTextView.setText(R.string.lorem_m);
        mCareBodyTextView.setText(R.string.lorem_l);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_PLANT_ID, mPlantId);
    }

    @Override
    public void onDestroyView() {
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
        super.onDestroyView();
    }

    public void setPlantId(int plantId) {
        mPlantId = plantId;
    }
}
