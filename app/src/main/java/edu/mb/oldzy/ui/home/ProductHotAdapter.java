package edu.mb.oldzy.ui.home;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import edu.mb.oldzy.databinding.ItemProductHotBinding;
import edu.mb.oldzy.domain.ApiClient;
import edu.mb.oldzy.domain.model.PostResponse;

public class ProductHotAdapter extends RecyclerView.Adapter<ProductHotAdapter.ProductHotViewHolder> {
    private List<PostResponse> posts;

    public ProductHotAdapter(List<PostResponse> posts) {
        this.posts = posts;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDataSource(List<PostResponse> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductHotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductHotBinding binding = ItemProductHotBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductHotViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHotViewHolder holder, int position) {
        PostResponse model = posts.get(position);
        holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    static class ProductHotViewHolder extends RecyclerView.ViewHolder {
        private final ItemProductHotBinding binding;

        public ProductHotViewHolder(ItemProductHotBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(PostResponse model) {
            Glide.with(binding.ivPost.getRootView())
                    .load(ApiClient.BASE_URL.substring(0, ApiClient.BASE_URL.length() - 1) + model.getImages().get(0).getImageUrl())
                    .into(binding.ivPost);

            binding.tvTitlePost.setText(model.getTitle());
            binding.tvPrice.setText(model.getPrice() + " đ");
            binding.tvLocation.setText(model.getSellerAddress());
        }
    }
}
