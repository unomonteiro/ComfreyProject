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

import com.google.android.gms.common.util.CollectionUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.monteirodev.comfreyproject.R;
import io.monteirodev.comfreyproject.data.PlantDetail;
import io.monteirodev.comfreyproject.data.Recipe;
import io.monteirodev.comfreyproject.utils.GlideApp;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC;

public class PlantDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int DETAIL_VIEW_TYPE = 0;
    private static final int RECIPE_VIEW_TYPE = 1;

    final private RecipeClickListener mRecipeClickListener;
    private List<PlantDetail> mDetails;
    private Recipe mRecipe;

    PlantDetailsAdapter(RecipeClickListener recipeClickListener) {
        mRecipeClickListener = recipeClickListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId;

        switch (viewType) {

            case DETAIL_VIEW_TYPE:
                layoutId = R.layout.details_item;
                break;

            case RECIPE_VIEW_TYPE:
                layoutId = R.layout.recipes_item;
                break;

            default:
                throw new IllegalArgumentException("Invalid view type, value of " + viewType);
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);

        switch (viewType) {
            case DETAIL_VIEW_TYPE: {
                return new PlantDetailViewHolder(view);
            }
            case RECIPE_VIEW_TYPE: {
                return new PlantRecipeViewHolder(view);
            }
            default:
                throw new IllegalArgumentException("Invalid view type, value of " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case DETAIL_VIEW_TYPE: {
                PlantDetailViewHolder detailHolder = (PlantDetailViewHolder) holder;
                PlantDetail detail = mDetails.get(position);

                if (TextUtils.isEmpty(detail.getImageUrl())) {
                    detailHolder.imageView.setVisibility(GONE);
                } else {
                    detailHolder.imageView.setVisibility(VISIBLE);
                    GlideApp.with(detailHolder.imageView.getContext())
                            .load(detail.getImageUrl())
                            .diskCacheStrategy(AUTOMATIC)
                            .into(detailHolder.imageView);
                }

                if (TextUtils.isEmpty(detail.getTitle())) {
                    detailHolder.titleTextView.setVisibility(GONE);
                } else {
                    detailHolder.titleTextView.setText(detail.getTitle());
                    detailHolder.titleTextView.setVisibility(VISIBLE);
                }

                if (TextUtils.isEmpty(detail.getBody())) {
                    detailHolder.bodyWebView.setVisibility(GONE);
                } else {
                    detailHolder.bodyWebView.setVisibility(VISIBLE);
                    detailHolder.bodyWebView.loadData(detail.getBody(), "text/html", null);
                }
                break;
            }
            case RECIPE_VIEW_TYPE: {
                PlantRecipeViewHolder recipeHolder = (PlantRecipeViewHolder) holder;
                String imageUrl = mRecipe.getImageUrl();
                if (TextUtils.isEmpty(imageUrl)) {
                    recipeHolder.recipeImage.setImageResource(R.drawable.wide_image_placeholder);
                } else {
                    GlideApp.with(recipeHolder.recipeImage.getContext())
                            .load(imageUrl)
                            .diskCacheStrategy(AUTOMATIC)
                            .into(recipeHolder.recipeImage);
                }
                recipeHolder.recipeName.setText(mRecipe.getName());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mRecipeClickListener.onRecipeClick(mRecipe);
                    }
                });
                break;
            }
            default:
                throw new IllegalArgumentException("Invalid view type, value of " + viewType);
        }
    }

    @Override
    public int getItemCount() {
        int totalItems = 0;
        if (!CollectionUtils.isEmpty(mDetails)) {
            totalItems += mDetails.size();
        }
        if (mRecipe != null) {
            totalItems++;
        }
        return totalItems;
    }

    @Override
    public int getItemViewType(int position) {
        if (!CollectionUtils.isEmpty(mDetails) && position < mDetails.size()) {
            return DETAIL_VIEW_TYPE;
        } else {
            return RECIPE_VIEW_TYPE;
        }
    }

    public void setDetails(List<PlantDetail> newPlantDetails) {
        mDetails = newPlantDetails;
    }

    public void setRecipe(Recipe newRecipe) {
        mRecipe = newRecipe;
    }

    public interface RecipeClickListener {
        void onRecipeClick(Recipe recipe);
    }

    public class PlantDetailViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.detail_image_view)
        ImageView imageView;
        @BindView(R.id.detail_title_text_view)
        TextView titleTextView;
        @BindView(R.id.detail_body_web_view)
        WebView bodyWebView;

        PlantDetailViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class PlantRecipeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_text_view)
        TextView recipeName;
        @BindView(R.id.item_image_view)
        ImageView recipeImage;

        PlantRecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

