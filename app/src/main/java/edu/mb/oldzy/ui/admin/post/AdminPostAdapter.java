package edu.mb.oldzy.ui.admin.post;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import edu.mb.oldzy.databinding.ItemAdminPostBinding;
import edu.mb.oldzy.domain.ApiClient;
import edu.mb.oldzy.domain.model.PostResponse;

public class AdminPostAdapter extends RecyclerView.Adapter<AdminPostAdapter.AdminViewHolder> {

    private List<PostResponse> posts;

    public AdminPostAdapter(List<PostResponse> posts) {
        this.posts = posts;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDataSource(List<PostResponse> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAdminPostBinding binding = ItemAdminPostBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AdminViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminViewHolder holder, int position) {
        PostResponse post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class AdminViewHolder extends RecyclerView.ViewHolder {

        private final ItemAdminPostBinding binding;

        public AdminViewHolder(ItemAdminPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(PostResponse postResponse) {
            // bind data to view
            Glide.with(binding.ivCategory.getRootView())
                    .load(ApiClient.BASE_URL.substring(0, ApiClient.BASE_URL.length() - 1) + postResponse.getImages().get(0).getImageUrl())
                    .into(binding.ivCategory);

            binding.tvName.setText(postResponse.getTitle());
            binding.tvDescription.setText(postResponse.getDescription());
            binding.tvCreateBy.setText("Tạo bởi: " + postResponse.getCreateBy());
            binding.tvCategory.setText("Danh mục: " + postResponse.getCategory().getName());
        }
    }
}
