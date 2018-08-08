package io.monteirodev.comfreyproject.ui;

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
import io.monteirodev.comfreyproject.data.Detail;
import io.monteirodev.comfreyproject.ui.about.AboutActivity;
import io.monteirodev.comfreyproject.utils.GlideApp;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.DetailViewHolder>{

    private List<? extends Detail> mDetails;
    private DetailClickListener mDetailClickListener;

    public DetailsAdapter() {

    }

    public DetailsAdapter(DetailClickListener detailClickListener) {
        mDetailClickListener = detailClickListener;
    }

    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.details_item, parent, false);
        return new DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder holder, int position) {
        final Detail detail = mDetails.get(position);

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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDetailClickListener != null) {
                    mDetailClickListener.onClick(detail);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (null == mDetails) {
            return 0;
        }
        return mDetails.size();
    }

    public void setDetailList(List<? extends Detail> newDetails) {
        mDetails = newDetails;
        notifyDataSetChanged();
    }

    public interface DetailClickListener {
        void onClick(Detail detail);
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
