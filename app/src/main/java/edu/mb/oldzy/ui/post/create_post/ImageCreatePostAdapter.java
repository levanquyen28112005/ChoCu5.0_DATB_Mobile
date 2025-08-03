package edu.mb.oldzy.ui.post.create_post;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.mb.oldzy.R;
import edu.mb.oldzy.data.model.ImagePostModel;
import edu.mb.oldzy.databinding.ItemImagePostBinding;

public class ImageCreatePostAdapter extends RecyclerView.Adapter<ImageCreatePostAdapter.ImageCreatePostViewHolder> {

    private List<ImagePostModel> imagePostModels;
    private final OnImageClickListener onImageClickListener;

    public ImageCreatePostAdapter(List<ImagePostModel> imagePostModels, OnImageClickListener onImageClickListener) {
        this.imagePostModels = imagePostModels;
        this.onImageClickListener = onImageClickListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setImagePostModels(List<ImagePostModel> imagePostModels) {
        this.imagePostModels = imagePostModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ImageCreatePostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemImagePostBinding binding = ItemImagePostBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ImageCreatePostViewHolder(binding, onImageClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageCreatePostViewHolder holder, int position) {
        holder.bind(imagePostModels.get(position));
    }

    @Override
    public int getItemCount() {
        return imagePostModels != null ? imagePostModels.size() : 0;
    }

    public static class ImageCreatePostViewHolder extends RecyclerView.ViewHolder {
        private final ItemImagePostBinding binding;
        private final OnImageClickListener onImageClickListener;

        public ImageCreatePostViewHolder(ItemImagePostBinding binding, OnImageClickListener onImageClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onImageClickListener = onImageClickListener;
        }

        public void bind(ImagePostModel model) {
            if (model.isAddImage()) {
                binding.imageView.setImageResource(R.drawable.icon_add_image);
                binding.cardRemoveImage.setVisibility(View.GONE);
                binding.imageView.setOnClickListener(v -> {
                    if (onImageClickListener != null) {
                        onImageClickListener.onAddImageClick();
                    }
                });
            } else {
                binding.imageView.setImageURI(model.getImageUri());
                binding.cardRemoveImage.setVisibility(View.VISIBLE);
                binding.cardRemoveImage.setOnClickListener(v -> {
                    if (onImageClickListener != null) {
                        onImageClickListener.onRemoveImageClick(model);
                    }
                });
            }
        }
    }

    public interface OnImageClickListener {
        void onAddImageClick();

        void onRemoveImageClick(ImagePostModel model);
    }
}
