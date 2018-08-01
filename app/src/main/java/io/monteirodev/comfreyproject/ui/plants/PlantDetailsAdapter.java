package io.monteirodev.comfreyproject.ui.plants;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.monteirodev.comfreyproject.R;
import io.monteirodev.comfreyproject.data.PlantDetail;
import io.monteirodev.comfreyproject.utils.GlideApp;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC;

public class PlantDetailsAdapter extends RecyclerView.Adapter<PlantDetailsAdapter.DetailViewHolder> {

    private List<PlantDetail> mPlantDetails;

    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.plant_details_item, parent, false);
        return new DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder holder, int position) {
        PlantDetail detail = mPlantDetails.get(position);

        if (TextUtils.isEmpty(detail.getImageUrl())) {
            holder.imageView.setVisibility(GONE);
        } else {
            holder.imageView.setVisibility(VISIBLE);
            GlideApp.with(holder.imageView.getContext())
                    .load(detail.getImageUrl())
                    .diskCacheStrategy(AUTOMATIC)
                    .into(holder.imageView);
        }

        if (TextUtils.isEmpty(detail.getTitle())) {
            holder.titleTextView.setVisibility(GONE);
        } else {
            holder.titleTextView.setText(detail.getTitle());
            holder.titleTextView.setVisibility(VISIBLE);
        }

        if (TextUtils.isEmpty(detail.getBody())) {
            holder.bodyWebView.setVisibility(GONE);
        } else {
            holder.bodyWebView.setVisibility(VISIBLE);
            holder.bodyWebView.loadData(detail.getBody(), "text/html", null);
        }
    }

    @Override
    public int getItemCount() {
        if (null == mPlantDetails) {
            return 0;
        }
        return mPlantDetails.size();
    }

    public void setDetailList(List<PlantDetail> newPlantDetails) {
        mPlantDetails = newPlantDetails;
        notifyDataSetChanged();
    }

    public class DetailViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.detail_image_view)
        ImageView imageView;
        @BindView(R.id.detail_title_text_view)
        TextView titleTextView;
        @BindView(R.id.detail_body_web_view)
        WebView bodyWebView;

        DetailViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
