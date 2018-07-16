package io.monteirodev.comfreyproject;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.monteirodev.comfreyproject.database.MyMenuOption;

class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private MenuClickListener mMenuClickListener;
    private List<MyMenuOption> mOptions;

    public MenuAdapter(MenuClickListener clickListener) {
        mMenuClickListener = clickListener;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.main_item, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MenuViewHolder holder, int position) {
        holder.option = mOptions.get(position);
        holder.image.setImageResource(holder.option.getImageId());
        holder.title.setText(holder.option.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMenuClickListener.onOptionClick(holder.option.getKey());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (null == mOptions) {
            return 0;
        }
        return mOptions.size();
    }

    public void setOptions(List<MyMenuOption> newOptions) {
        mOptions = newOptions;
        notifyDataSetChanged();
    }

    public interface MenuClickListener {
        void onOptionClick(String option);
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_image_view)
        ImageView image;
        @BindView(R.id.item_text_view)
        TextView title;
        MyMenuOption option;

        public MenuViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
