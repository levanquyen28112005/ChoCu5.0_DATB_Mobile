package edu.mb.oldzy.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import edu.mb.oldzy.data.model.CategoryModel;
import edu.mb.oldzy.data.model.SlideModel;
import edu.mb.oldzy.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment implements CategoryAdapter.OnItemClickListener {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;

    private ImageAdapter adapter;
    private List<String> imageList;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private int currentPosition = 0;
    private final long DELAY_MS = 3000; // 3s

    private CategoryAdapter categoryAdapter;
    private ProductHotAdapter productHotAdapter;

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (currentPosition == imageList.size()) {
                currentPosition = 0;
                binding.viewPager2.setCurrentItem(currentPosition, false);
            } else {
                binding.viewPager2.setCurrentItem(currentPosition++, true);
            }
            handler.postDelayed(this, DELAY_MS);
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        viewModel.getSlidesResult().observe(getViewLifecycleOwner(), response -> {
            if (response.isEmpty()) {
                binding.viewPager2.setVisibility(View.GONE);
            } else {
                binding.viewPager2.setVisibility(View.VISIBLE);
                imageList = response.stream()
                        .map(SlideModel::getImage)
                        .collect(Collectors.toList());
                adapter = new ImageAdapter(imageList);
                binding.viewPager2.setAdapter(adapter);

                // Auto-scroll
                handler.postDelayed(runnable, DELAY_MS);
            }
        });
        viewModel.getCategoriesResult().observe(getViewLifecycleOwner(), response ->
                categoryAdapter.setDataSource(response));

        viewModel.getHotPosts().observe(getViewLifecycleOwner(), response -> {
            productHotAdapter.setDataSource(response);
        });
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.loadSlides();
        viewModel.loadCategories();
        viewModel.loadPosts();

        productHotAdapter = new ProductHotAdapter(Collections.emptyList());
        binding.recyclerViewHot.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        binding.recyclerViewHot.setHasFixedSize(true);
        binding.recyclerViewHot.setAdapter(productHotAdapter);
        binding.recyclerViewHot.setClipToPadding(false);
        binding.recyclerViewHot.setPadding(0, 0, 0, 500);

        categoryAdapter = new CategoryAdapter(Collections.emptyList(), this);
        binding.recyclerViewCategory.setLayoutManager(new GridLayoutManager(requireContext(), 2, GridLayoutManager.HORIZONTAL, false));
        binding.recyclerViewCategory.setHasFixedSize(true);
        binding.recyclerViewCategory.setAdapter(categoryAdapter);

        binding.viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            private boolean isUserScrolling = false;

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);

                if (state == ViewPager2.SCROLL_STATE_DRAGGING) {
                    isUserScrolling = true;
                    stopAutoScroll();
                } else if (state == ViewPager2.SCROLL_STATE_IDLE && isUserScrolling) {
                    isUserScrolling = false;
                    currentPosition = binding.viewPager2.getCurrentItem();
                    startAutoScroll();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        startAutoScroll();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopAutoScroll();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(runnable);
        binding = null;
    }

    private void startAutoScroll() {
        handler.postDelayed(runnable, DELAY_MS);
    }

    private void stopAutoScroll() {
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onItemClick(CategoryModel model) {
        // TODO
    }
}