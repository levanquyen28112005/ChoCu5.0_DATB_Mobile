package edu.mb.oldzy.ui.home;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import edu.mb.oldzy.data.model.CategoryModel;
import edu.mb.oldzy.databinding.ItemCategoryBinding;
import edu.mb.oldzy.domain.ApiClient;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<CategoryModel> models;
    private final OnItemClickListener listener;

    public CategoryAdapter(List<CategoryModel> models, OnItemClickListener listener) {
        this.models = models;
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDataSource(List<CategoryModel> models) {
        this.models = models;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryBinding binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CategoryViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.bind(models.get(position));
    }

    @Override
    public int getItemCount() {
        return models != null ? models.size() : 0;
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        public CategoryViewHolder(ItemCategoryBinding binding, OnItemClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onItemClickListener = listener;
        }

        private final ItemCategoryBinding binding;
        private final OnItemClickListener onItemClickListener;

        public void bind(CategoryModel model) {
            Glide.with(binding.getRoot())
                    .load(ApiClient.BASE_URL.substring(0, ApiClient.BASE_URL.length() - 1) + model.getImage())
                    .into(binding.ivCategory);

            binding.tvName.setText(model.getName());
            binding.getRoot().setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(model);
                }
            });
        }

    }

    public interface OnItemClickListener {
        void onItemClick(CategoryModel model);
    }
}
