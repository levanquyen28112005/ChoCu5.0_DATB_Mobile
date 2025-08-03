package edu.mb.oldzy.ui.admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.Set;

import edu.mb.oldzy.R;
import edu.mb.oldzy.data.model.UserModel;
import edu.mb.oldzy.databinding.ActivityAdminBinding;
import edu.mb.oldzy.ui.MainActivity;

public class AdminActivity extends AppCompatActivity {

    private ActivityAdminBinding binding;
    private NavController navController;
    public AdminTab adminTab = AdminTab.DASHBOARD;

    private int colorSelected;
    private int colorUnselected;

    Set<Integer> noBottomFragments;
    private UserModel userModel;

    public UserModel getUserModel() {
        return userModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        userModel = (UserModel) getIntent().getSerializableExtra("user");

        colorSelected = ContextCompat.getColor(this, R.color.black);
        colorUnselected = ContextCompat.getColor(this, R.color.color8C8C8C);

        if (userModel == null) {
            finish();
        }
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_admin);
        noBottomFragments = Set.of(
                R.id.nav_admin_input_category,
                R.id.nav_admin_slides,
                R.id.nav_admin_create_slide,
                R.id.nav_admin_post
        );

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (noBottomFragments.contains(destination.getId())) {
                binding.layoutMenu.setVisibility(View.GONE);
            } else {
                binding.layoutMenu.setVisibility(View.VISIBLE);
            }
        });

        binding.ivDashboard.setColorFilter(null);
        binding.tvDashboard.setTextColor(colorSelected);

        binding.ivCategory.setColorFilter(colorUnselected);
        binding.tvCategory.setTextColor(colorUnselected);
        binding.ivPost.setColorFilter(colorUnselected);
        binding.tvPost.setTextColor(colorUnselected);

        binding.navAdminCategory.setOnClickListener(v -> {
            switchCategoriesTab();
        });
        binding.navAdminDashboard.setOnClickListener(v -> {
            switchDashboardTab();
        });
        binding.navAdminPost.setOnClickListener(v -> {
            switchPostTab();
        });
    }

    public void switchCategoriesTab() {
        if (adminTab != AdminTab.CATEGORY) {

            binding.ivDashboard.setColorFilter(colorUnselected);
            binding.tvDashboard.setTextColor(colorUnselected);

            binding.ivCategory.setColorFilter(null);
            binding.tvCategory.setTextColor(colorSelected);

            binding.ivPost.setColorFilter(colorUnselected);
            binding.tvPost.setTextColor(colorUnselected);

            adminTab = AdminTab.CATEGORY;
            navController.navigate(R.id.nav_admin_category);
        }
    }

    public void switchPostTab() {
        if (adminTab != AdminTab.POST) {
            binding.ivDashboard.setColorFilter(colorUnselected);
            binding.tvDashboard.setTextColor(colorUnselected);
            binding.ivCategory.setColorFilter(colorUnselected);
            binding.tvCategory.setTextColor(colorUnselected);

            binding.ivPost.setColorFilter(null);
            binding.tvPost.setTextColor(colorSelected);

            adminTab = AdminTab.POST;
            navController.navigate(R.id.nav_admin_post);
        }
    }

    public void switchDashboardTab() {
        if (adminTab != AdminTab.DASHBOARD) {
            binding.ivDashboard.setColorFilter(null);
            binding.tvDashboard.setTextColor(colorSelected);

            binding.ivCategory.setColorFilter(colorUnselected);
            binding.tvCategory.setTextColor(colorUnselected);
            binding.ivPost.setColorFilter(colorUnselected);
            binding.tvPost.setTextColor(colorUnselected);

            adminTab = AdminTab.DASHBOARD;
            navController.navigate(R.id.nav_admin_dashboard);
        }
    }

    public void logout() {
        // clear token
        SharedPreferences sharedPreferences = getSharedPreferences("auth", MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
