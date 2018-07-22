package io.monteirodev.comfreyproject.ui.plants;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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

public class PlantDetailsFragment extends Fragment {

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

    private Plant mPlant;
    private Unbinder unbinder;


    public PlantDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_plant_details, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        if (mPlant != null) {
            mExpertNameTextView.setText(mPlant.getExpertName());
            mExpertImageView.setImageResource(R.drawable.profile_placeholder);
            mExpertBodyTextView.setText(R.string.lorem_expert);
            mInfoBodyTextView.setText(R.string.lorem_m);
            mBenefitsBodyTextView.setText(R.string.lorem_m);
            mCareBodyTextView.setText(R.string.lorem_l);
        }

        return rootView;
    }

    @Override
    public void onDestroyView() {
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
        super.onDestroyView();
    }

    public void setPlant(Plant plant) {
        mPlant = plant;
    }
}
