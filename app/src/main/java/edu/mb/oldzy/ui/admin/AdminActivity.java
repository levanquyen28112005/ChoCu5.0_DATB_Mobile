package edu.mb.oldzy.ui.admin;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.Set;

import edu.mb.oldzy.R;
import edu.mb.oldzy.data.model.UserModel;
import edu.mb.oldzy.databinding.ActivityAdminBinding;

public class AdminActivity extends AppCompatActivity {

    private ActivityAdminBinding binding;
    private NavController navController;
    public AdminTab adminTab = AdminTab.DASHBOARD;

    Set<Integer> noBottomFragments;
    private UserModel userModel;

    public UserModel getUserModel() {
        return userModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        userModel = (UserModel) getIntent().getSerializableExtra("user");
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
                R.id.nav_admin_create_slide
        );

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (noBottomFragments.contains(destination.getId())) {
                binding.layoutMenu.setVisibility(View.GONE);
            } else {
                binding.layoutMenu.setVisibility(View.VISIBLE);
            }
        });

        binding.navAdminCategory.setOnClickListener(v -> {
            if (adminTab != AdminTab.CATEGORY) {
                adminTab = AdminTab.CATEGORY;
                navController.navigate(R.id.nav_admin_category);
            }
        });
        binding.navAdminDashboard.setOnClickListener(v -> {
            if (adminTab != AdminTab.DASHBOARD) {
                adminTab = AdminTab.DASHBOARD;
                navController.navigate(R.id.nav_admin_dashboard);
            }
        });
        binding.navAdminPost.setOnClickListener(v -> {
            if (adminTab != AdminTab.POST) {
                adminTab = AdminTab.POST;
                navController.navigate(R.id.nav_admin_post);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
