package edu.mb.oldzy.ui.admin.users;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import edu.mb.oldzy.data.model.CategoryModel;
import edu.mb.oldzy.data.model.UserModel;
import edu.mb.oldzy.databinding.FragmentAdminUsersBinding;
import edu.mb.oldzy.domain.model.UserResponse;

public class AdminUsersFragment extends Fragment implements AdminUserAdapter.OnItemClickListener {
    private FragmentAdminUsersBinding binding;

    private AdminUsersViewModel viewModel;
    private AdminUserAdapter adminUserAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(AdminUsersViewModel.class);
        binding = FragmentAdminUsersBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getUsersResult().observe(getViewLifecycleOwner(), users -> {
            if (users == null) return;
            List<UserModel> userModels = users.stream()
                    .map(UserResponse::toUserModel)
                    .collect(Collectors.toList());
            adminUserAdapter.setDataSource(userModels);
        });
        adminUserAdapter = new AdminUserAdapter(Collections.emptyList(), this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(adminUserAdapter);

        SharedPreferences sharedPref = requireContext().getSharedPreferences("auth", Context.MODE_PRIVATE);
        String token = sharedPref.getString("access_token", null);
        viewModel.getUsers(token);
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    @Override
    public void onItemClick(CategoryModel model) {

    }

    @Override
    public void onItemDetailClick(CategoryModel model) {

    }
}
