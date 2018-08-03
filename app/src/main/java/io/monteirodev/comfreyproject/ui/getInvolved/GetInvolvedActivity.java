package io.monteirodev.comfreyproject.ui.getInvolved;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.monteirodev.comfreyproject.R;
import io.monteirodev.comfreyproject.data.GetInvolvedDetail;
import io.monteirodev.comfreyproject.ui.DetailsAdapter;

public class GetInvolvedActivity extends AppCompatActivity {

    @BindView(R.id.get_involved_recycler_view)
    RecyclerView mRecyclerView;

    private DetailsAdapter mDetailsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_involved);
        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDetailsAdapter = new DetailsAdapter();
        mRecyclerView.setAdapter(mDetailsAdapter);

        setupViewModel();
    }

    private void setupViewModel() {
        GetInvolvedViewModel model =
                ViewModelProviders.of(this).get(GetInvolvedViewModel.class);
        model.getDetails().observe(this, new Observer<List<GetInvolvedDetail>>() {
            @Override
            public void onChanged(@Nullable List<GetInvolvedDetail> details) {
                mDetailsAdapter.setDetailList(details);
            }
        });
    }
}
