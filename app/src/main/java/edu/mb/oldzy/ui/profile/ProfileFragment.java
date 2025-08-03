package edu.mb.oldzy.ui.profile;

import static androidx.navigation.Navigation.findNavController;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import edu.mb.oldzy.R;
import edu.mb.oldzy.databinding.FragmentProfileBinding;
import edu.mb.oldzy.ui.MainActivity;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private ProfileViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        observerViewModel();
        return binding.getRoot();
    }

    private void observerViewModel() {
        viewModel.getAccessToken().observe(getViewLifecycleOwner(), s -> {
            if (TextUtils.isEmpty(s)) {
                binding.cardViewLogout.setVisibility(View.GONE);
                binding.tvLogout.setVisibility(View.GONE);
                binding.tvLoginOrRegister.setVisibility(View.VISIBLE);
                binding.layoutInfo.setVisibility(View.GONE);
            } else {
                binding.cardViewLogout.setVisibility(View.VISIBLE);
                binding.tvLogout.setVisibility(View.VISIBLE);
                binding.tvLoginOrRegister.setVisibility(View.GONE);
                binding.layoutInfo.setVisibility(View.VISIBLE);
            }
        });
        viewModel.getUserResult().observe(getViewLifecycleOwner(), userResponse -> {
            binding.tvFullName.setText(userResponse.getFullName());
            binding.tvUserId.setText(userResponse.getEmail());

            if (TextUtils.isEmpty(userResponse.getAvatar())) {
                binding.ivAvatar.setImageResource(R.drawable.avatar_default_svgrepo_com);
            } else {
                Glide.with(binding.getRoot())
                        .load(userResponse.getAvatar())
                        .into(binding.ivAvatar);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sharedPref = requireContext().getSharedPreferences("auth", Context.MODE_PRIVATE);
        viewModel.setAccessToken(sharedPref.getString("access_token", null));

        binding.tvLoginOrRegister.setOnClickListener(v -> {
            findNavController(binding.getRoot()).navigate(R.id.action_navigation_profile_to_navigation_login);
        });
        binding.tvLogout.setOnClickListener(v -> {
            // clear token
            SharedPreferences sharedPreferences = requireContext().getSharedPreferences("auth", Context.MODE_PRIVATE);
            sharedPreferences.edit().clear().apply();

            viewModel.setAccessToken(null);
        });
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}
