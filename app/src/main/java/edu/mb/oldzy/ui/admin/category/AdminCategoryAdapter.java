package edu.mb.oldzy.ui.admin.category;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import edu.mb.oldzy.data.model.CategoryModel;
import edu.mb.oldzy.databinding.ItemAdminCategoryBinding;
import edu.mb.oldzy.domain.ApiClient;

public class AdminCategoryAdapter extends RecyclerView.Adapter<AdminCategoryAdapter.AdminCategoryViewHolder> {

    private List<CategoryModel> models;
    private final OnItemClickListener listener;

    public AdminCategoryAdapter(List<CategoryModel> models, OnItemClickListener listener) {
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
    public AdminCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAdminCategoryBinding binding = ItemAdminCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AdminCategoryViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminCategoryViewHolder holder, int position) {
        holder.bind(models.get(position));
    }

    @Override
    public int getItemCount() {
        return models != null ? models.size() : 0;
    }

    public static class AdminCategoryViewHolder extends RecyclerView.ViewHolder {
        public AdminCategoryViewHolder(ItemAdminCategoryBinding binding, OnItemClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onItemClickListener = listener;
        }

        private final ItemAdminCategoryBinding binding;
        private final OnItemClickListener onItemClickListener;

        public void bind(CategoryModel model) {
            Glide.with(binding.getRoot())
                    .load(ApiClient.BASE_URL.substring(0, ApiClient.BASE_URL.length() - 1) + model.getImage())
                    .into(binding.ivCategory);

            binding.tvName.setText(model.getName());
            binding.tvDescription.setText(model.getDescription());

            binding.cardViewDetail.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemDetailClick(model);
                }
            });
            binding.getRoot().setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(model);
                }
            });
        }

    }

    public interface OnItemClickListener {
        void onItemClick(CategoryModel model);

        void onItemDetailClick(CategoryModel model);
    }
}
