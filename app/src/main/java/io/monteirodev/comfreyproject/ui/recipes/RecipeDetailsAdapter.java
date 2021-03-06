package io.monteirodev.comfreyproject.ui.recipes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.monteirodev.comfreyproject.R;
import io.monteirodev.comfreyproject.data.Ingredient;
import io.monteirodev.comfreyproject.data.Step;
import io.monteirodev.comfreyproject.utils.RecipeUtils;

import static io.monteirodev.comfreyproject.utils.RecipeUtils.fromHtml;

public class RecipeDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int INGREDIENTS_VIEW_TYPE = 0;
    private static final int STEP_VIEW_TYPE = 1;

    private List<Ingredient> mIngredients;
    private List<Step> mSteps;

    public RecipeDetailsAdapter() {
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId;

        switch (viewType) {

            case INGREDIENTS_VIEW_TYPE:
                layoutId = R.layout.recipe_ingredients_item;
                break;

            case STEP_VIEW_TYPE:
                layoutId = R.layout.recipe_step_item;
                break;

            default:
                throw new IllegalArgumentException("Invalid view type, value of " + viewType);
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        view.setFocusable(true);

        switch (viewType) {
            case INGREDIENTS_VIEW_TYPE: {
                return new IngredientsViewHolder(view);
            }
            case STEP_VIEW_TYPE: {
                return new StepViewHolder(view);
            }
            default:
                throw new IllegalArgumentException("Invalid view type, value of " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        int viewType = getItemViewType(position);
        switch (viewType) {
            case INGREDIENTS_VIEW_TYPE: {
                IngredientsViewHolder ingredientsViewHolder = (IngredientsViewHolder) holder;
                Spanned ingredientsText = null;
                if (hasIngredientList() && context != null) {
                    String prefix = "- ";
                    List<String> ingredientsStrings = RecipeUtils.
                            getIngredientsString(context, mIngredients, prefix);
                    ingredientsText = fromHtml(
                            TextUtils.join("<br>",
                                    ingredientsStrings));
                }
                ingredientsViewHolder.mIngredientListTextView.setText(ingredientsText, TextView.BufferType.SPANNABLE);
                break;
            }
            case STEP_VIEW_TYPE: {
                StepViewHolder stepViewHolder = (StepViewHolder) holder;
                final int adjustedPosition = position + (hasIngredientList() ? -1 : 0);
                stepViewHolder.itemView.setTag(adjustedPosition);

                Step step = mSteps.get(adjustedPosition);
                int stepOrder = step.getId();
                String description = step.getDescription();
                String stepText = description;
                if (stepOrder > 0) {
                    stepText = context.getString(R.string.number_step, stepOrder, description);
                }
                stepViewHolder.mStepTextView.setText(stepText);

                break;
            }
            default:
                throw new IllegalArgumentException("Invalid view type, value of " + viewType);
        }
    }

    @Override
    public int getItemCount() {
        int totalItems = 0;
        if (hasIngredientList()) {
            totalItems++;
        }
        totalItems += getStepsCount();
        return totalItems;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && hasIngredientList()) {
            return INGREDIENTS_VIEW_TYPE;
        } else {
            return STEP_VIEW_TYPE;
        }
    }

    public void setIngredients(List<Ingredient> newIngredients) {
        mIngredients = newIngredients;
        notifyDataSetChanged();
    }

    public void setSteps(List<Step> newSteps) {
        mSteps = newSteps;
        notifyDataSetChanged();
    }

    private boolean hasIngredientList() {
        return mIngredients != null && mIngredients.size() > 0;
    }

    private int getStepsCount() {
        if (mSteps == null) {
            return 0;
        }
        return mSteps.size();
    }

    public interface StepClickListener {
        void onStepClick(int stepIndex);
    }

    class IngredientsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ingredients_title_text_view)
        TextView mIngredientsTitleTextView;
        @BindView(R.id.ingredient_list_text_view)
        TextView mIngredientListTextView;

        IngredientsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class StepViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.step_body_text_view)
        TextView mStepTextView;

        StepViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
