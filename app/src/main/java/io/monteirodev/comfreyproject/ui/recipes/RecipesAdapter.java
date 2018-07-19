package io.monteirodev.comfreyproject.ui.recipes;

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
import io.monteirodev.comfreyproject.data.Recipe;
import io.monteirodev.comfreyproject.utils.GlideApp;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {

    private final RecipesClickListener mClickListener;
    private List<Recipe> mRecipes;

    public RecipesAdapter(RecipesClickListener clickListener) {
        mClickListener = clickListener;
    }


    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recipes_item, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecipeViewHolder holder, int position) {
        final Recipe recipe = mRecipes.get(position);
        holder.itemView.setTag(recipe.getId());
        holder.recipeName.setText(recipe.getName());
        String imageUrl = recipe.getImage();
        if (TextUtils.isEmpty(imageUrl)) {
            holder.recipeImage.setImageResource(R.drawable.wide_image_placeholder);
        } else {
            GlideApp.with(holder.recipeImage.getContext())
                    .load(imageUrl)
                    .into(holder.recipeImage);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onRecipeClick(recipe);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (null == mRecipes) {
            return 0;
        }
        return mRecipes.size();
    }

    public void setRecipes(List<Recipe> newRecipes) {
        mRecipes = newRecipes;
        notifyDataSetChanged();
    }

    public interface RecipesClickListener {
        void onRecipeClick(Recipe recipe);
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_text_view)
        TextView recipeName;
        @BindView(R.id.item_image_view)
        ImageView recipeImage;
        Recipe recipe;

        RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
