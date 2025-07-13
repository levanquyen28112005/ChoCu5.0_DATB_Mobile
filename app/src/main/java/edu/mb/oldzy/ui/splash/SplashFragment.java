package edu.mb.oldzy.ui.splash;

import static androidx.navigation.Navigation.findNavController;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;

import edu.mb.oldzy.R;
import edu.mb.oldzy.databinding.FragmentSplashBinding;
import edu.mb.oldzy.ui.admin.AdminActivity;

public class SplashFragment extends Fragment {

    private FragmentSplashBinding binding;
    private final NavOptions navOptions = new NavOptions.Builder()
            .setPopUpTo(R.id.navigation_splash, true)
            .build();
    private SplashViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(SplashViewModel.class);
        binding = FragmentSplashBinding.inflate(inflater, container, false);

        viewModel.getUserResult().observe(getViewLifecycleOwner(), userResponse -> {
            if (userResponse != null) {
                if ("admin".equals(userResponse.getRole())) {
                    Intent intent = new Intent(requireActivity(), AdminActivity.class);
                    intent.putExtra("user", userResponse.toUserModel());
                    startActivity(intent);
                    requireActivity().finish();
                } else {
                    findNavController(binding.getRoot()).navigate(R.id.action_navigation_splash_to_navigation_home, null, navOptions);
                }
            } else {
                SharedPreferences sharedPref = requireContext().getSharedPreferences("auth", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.remove("access_token");
                editor.apply();
                findNavController(binding.getRoot()).navigate(R.id.action_navigation_splash_to_navigation_home, null, navOptions);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sharedPref = requireContext().getSharedPreferences("auth", Context.MODE_PRIVATE);
        String accessToken = sharedPref.getString("access_token", null);

        if (TextUtils.isEmpty(accessToken)) {
            // home
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                findNavController(binding.getRoot()).navigate(R.id.action_navigation_splash_to_navigation_home, null, navOptions);
            }, 2000);
        } else {
            viewModel.getUserInfo(accessToken);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
