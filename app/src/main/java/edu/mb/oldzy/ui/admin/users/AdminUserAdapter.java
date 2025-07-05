package edu.mb.oldzy.ui.admin.users;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import edu.mb.oldzy.R;
import edu.mb.oldzy.data.model.CategoryModel;
import edu.mb.oldzy.data.model.UserModel;
import edu.mb.oldzy.databinding.ItemAdminCategoryBinding;
import edu.mb.oldzy.databinding.ItemAdminUserBinding;

public class AdminUserAdapter extends RecyclerView.Adapter<AdminUserAdapter.AdminCategoryViewHolder> {

    private List<UserModel> models;
    private final OnItemClickListener listener;

    public AdminUserAdapter(List<UserModel> models, OnItemClickListener listener) {
        this.models = models;
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDataSource(List<UserModel> models) {
        this.models = models;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdminCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAdminUserBinding binding = ItemAdminUserBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
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
        public AdminCategoryViewHolder(ItemAdminUserBinding binding, OnItemClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onItemClickListener = listener;
        }

        private final ItemAdminUserBinding binding;
        private final OnItemClickListener onItemClickListener;

        public void bind(UserModel model) {
            if (TextUtils.isEmpty(model.getAvatar())) {
                binding.ivAvatar.setImageResource(R.drawable.avatar_default_svgrepo_com);
            } else {
                Glide.with(binding.getRoot())
                        .load(model.getAvatar())
                        .into(binding.ivAvatar);
            }

            binding.tvName.setText(model.getFullName());
            binding.tvEmail.setText(model.getEmail());
            binding.tvCreateTimed.setText("Tham gia " + model.getCreatedDate());
            binding.tvRole.setText("Vai trò: " + model.getRole());

            binding.cardViewDetail.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    // onItemClickListener.onItemDetailClick(model);
                }
            });
            binding.getRoot().setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    // onItemClickListener.onItemClick(model);
                }
            });
        }

    }

    public interface OnItemClickListener {
        void onItemClick(CategoryModel model);

        void onItemDetailClick(CategoryModel model);
    }
}
