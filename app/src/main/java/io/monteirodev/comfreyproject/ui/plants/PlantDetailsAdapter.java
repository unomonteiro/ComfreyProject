package io.monteirodev.comfreyproject.ui.plants;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.monteirodev.comfreyproject.R;
import io.monteirodev.comfreyproject.data.PlantDetail;
import io.monteirodev.comfreyproject.utils.GlideApp;

import static android.view.View.GONE;
import static com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC;
import static io.monteirodev.comfreyproject.utils.UiUtils.fromHtml;

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
            holder.imageView.setVisibility(View.VISIBLE);
            GlideApp.with(holder.imageView.getContext())
                    .load(detail.getImageUrl())
                    .diskCacheStrategy(AUTOMATIC)
                    .into(holder.imageView);
        }

        holder.titleTextView.setText(detail.getTitle());

        Spanned bodyText = fromHtml(detail.getBody());
        holder.bodyTextView.setText(bodyText, TextView.BufferType.SPANNABLE );
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
        @BindView(R.id.detail_body_text_view)
        TextView bodyTextView;

        DetailViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
