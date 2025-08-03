package edu.mb.oldzy.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import java.util.Set;

import edu.mb.oldzy.R;
import edu.mb.oldzy.databinding.ActivityMainBinding;
import edu.mb.oldzy.domain.model.UserResponse;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    Set<Integer> noBottomFragments;

    public UserResponse user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        noBottomFragments = Set.of(
                R.id.navigation_splash,
                R.id.navigation_create_post,
                R.id.navigation_login,
                R.id.navigation_register,
                R.id.navigation_created_post
        );

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (noBottomFragments.contains(destination.getId())) {
                binding.navView.setVisibility(View.GONE);
                binding.ivCreatePost.setVisibility(View.GONE);
            } else {
                binding.navView.setVisibility(View.VISIBLE);
                binding.ivCreatePost.setVisibility(View.VISIBLE);
            }
        });
        binding.navView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (navController.getCurrentDestination() == null) {
                return true;
            }
            if (itemId == navController.getCurrentDestination().getId()) {
                return true;
            }
            return NavigationUI.onNavDestinationSelected(item, navController);
        });
        binding.navView.setOnItemReselectedListener(item -> {
            // do nothing
        });

        binding.ivCreatePost.setOnClickListener(v -> {
            SharedPreferences sharedPref = getSharedPreferences("auth", Context.MODE_PRIVATE);
            String token = sharedPref.getString("access_token", null);
            if (token == null) {
                navController.navigate(R.id.navigation_login);
                return;
            }
            navController.navigate(R.id.navigation_create_post);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}