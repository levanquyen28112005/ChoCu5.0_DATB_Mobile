package edu.mb.oldzy.ui.admin.dashboard;

import android.annotation.SuppressLint;
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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import edu.mb.oldzy.R;
import edu.mb.oldzy.databinding.FragmentAdminDashboardBinding;
import edu.mb.oldzy.ui.admin.AdminActivity;
import edu.mb.oldzy.ui.admin.AdminTab;

public class AdminDashboardFragment extends Fragment {

    private FragmentAdminDashboardBinding binding;
    private AdminDashboardViewModel viewModel;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(AdminDashboardViewModel.class);
        binding = FragmentAdminDashboardBinding.inflate(inflater, container, false);
        viewModel.getSlideStatsResponseMutableLiveData().observe(getViewLifecycleOwner(), slideStatsResponse -> {
            binding.tvSlideCount.setText(String.valueOf(slideStatsResponse.getTotalSlides()));
            binding.tvLatestCreatedAt.setText("Cập nhật lần cuối: " + slideStatsResponse.getLatestCreatedAt());
        });
        viewModel.getCategoriesResult().observe(getViewLifecycleOwner(), response -> {
            binding.tvCountCategory.setText(response.size() + "");
            binding.tvCategoryNewLast7days.setText("+1 trong 7 ngày qua");
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.tvAdmin.setText(((AdminActivity) requireActivity()).getUserModel().getFullName());
        viewModel.loadCategories();
        viewModel.getSlideStats();
        binding.cardViewUser.setOnClickListener(v -> {
            ((AdminActivity) requireActivity()).adminTab = AdminTab.USERS;
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_admin);
            navController.navigate(R.id.action_nav_admin_dashboard_to_nav_admin_users);
        });
        binding.btnLogout.setOnClickListener(v -> {
            ((AdminActivity) requireActivity()).logout();
        });
        binding.cardViewSlide.setOnClickListener(b -> {
            ((AdminActivity) requireActivity()).adminTab = AdminTab.SLIDES;
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_admin);
            navController.navigate(R.id.action_nav_admin_dashboard_to_nav_admin_slides);
        });
        binding.cardViewCategory.setOnClickListener(v -> {
            ((AdminActivity) requireActivity()).switchCategoriesTab();
        });
        binding.cardViewPost.setOnClickListener(v -> {
            ((AdminActivity) requireActivity()).switchPostTab();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AdminActivity) requireActivity()).switchDashboardTab();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
