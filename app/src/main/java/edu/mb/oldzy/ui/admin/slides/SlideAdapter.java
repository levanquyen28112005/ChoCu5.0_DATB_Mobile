package edu.mb.oldzy.ui.admin.slides;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import edu.mb.oldzy.data.model.SlideModel;
import edu.mb.oldzy.databinding.ItemSlideBinding;
import edu.mb.oldzy.domain.ApiClient;

public class SlideAdapter extends RecyclerView.Adapter<SlideAdapter.SlideViewHolder> {

    private List<SlideModel> models;
    private final OnSlideItemClick onSlideItemClick;

    public SlideAdapter(List<SlideModel> models, OnSlideItemClick onSlideItemClick) {
        this.models = models;
        this.onSlideItemClick = onSlideItemClick;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDataSource(List<SlideModel> models) {
        this.models = models;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SlideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSlideBinding binding = ItemSlideBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SlideViewHolder(binding, onSlideItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull SlideViewHolder holder, int position) {
        holder.bind(models.get(position));
    }

    @Override
    public int getItemCount() {
        return models != null ? models.size() : 0;
    }

    public interface OnSlideItemClick {
        void onItemClick(SlideModel slideModel);
    }

    public static class SlideViewHolder extends RecyclerView.ViewHolder {
        private final ItemSlideBinding binding;
        private final OnSlideItemClick onSlideItemClick;

        public SlideViewHolder(@NonNull ItemSlideBinding binding, OnSlideItemClick onSlideItemClick) {
            super(binding.getRoot());
            this.binding = binding;
            this.onSlideItemClick = onSlideItemClick;
        }

        public void bind(SlideModel model) {
            Glide.with(binding.getRoot())
                    .load(ApiClient.BASE_URL.substring(0, ApiClient.BASE_URL.length() - 1) + model.getImage())
                    .into(binding.ivSlide);

            binding.tvDescription.setText(model.getDescription());
            binding.getRoot().setOnClickListener(v -> onSlideItemClick.onItemClick(model));
        }
    }
}
