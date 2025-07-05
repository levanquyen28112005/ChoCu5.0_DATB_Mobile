package edu.mb.oldzy.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Arrays;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import dagger.hilt.android.HiltAndroidApp;
import edu.mb.oldzy.R;
import edu.mb.oldzy.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;

    private ImageAdapter adapter;
    private List<Integer> imageList;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private int currentPosition = 0;
    private final long DELAY_MS = 3000; // 3s

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

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageList = Arrays.asList(
                R.drawable.banner_01,
                R.drawable.banner_02,
                R.drawable.banner_03,
                R.drawable.banner_04
        );

        adapter = new ImageAdapter(imageList);
        binding.viewPager2.setAdapter(adapter);

        // Auto-scroll
        handler.postDelayed(runnable, DELAY_MS);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(runnable);
        binding = null;
    }
}