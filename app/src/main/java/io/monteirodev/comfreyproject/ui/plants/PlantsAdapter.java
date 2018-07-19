package io.monteirodev.comfreyproject.ui.plants;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
import io.monteirodev.comfreyproject.data.Plant;
import io.monteirodev.comfreyproject.utils.GlideApp;

public class PlantsAdapter extends RecyclerView.Adapter<PlantsAdapter.PlantViewHolder> {

    private PlantClickListener mPlantClickListener;
    private List<Plant> mPlants;

    PlantsAdapter(PlantClickListener plantClickListener) {
        mPlantClickListener = plantClickListener;
    }

    @NonNull
    @Override
    public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.main_item, parent, false);
        return new PlantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PlantViewHolder holder, int position) {
        final Plant plant = mPlants.get(position);
        if (TextUtils.isEmpty(plant.getImageUrl())) {
            holder.imageView.setImageResource(R.drawable.wide_image_placeholder);
        } else {
            GlideApp.with(holder.imageView.getContext())
            .load(plant.getImageUrl())
            .into(holder.imageView);
        }
        holder.titleView.setText(plant.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlantClickListener.onPlantClick(plant);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (null == mPlants) {
            return 0;
        }
        return mPlants.size();
    }

    public void setPlantList(List<Plant> newPlant) {
        mPlants = newPlant;
        notifyDataSetChanged();
    }

    public interface PlantClickListener {
        void onPlantClick(Plant plant);
    }

    class PlantViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_image_view)
        ImageView imageView;
        @BindView(R.id.item_text_view)
        TextView titleView;
        Plant plant;

        PlantViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
