package edu.mb.oldzy.ui.admin.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Collections;

import edu.mb.oldzy.R;
import edu.mb.oldzy.data.model.CategoryModel;
import edu.mb.oldzy.databinding.FragmentAdminCategoryBinding;

public class AdminCategoryFragment extends Fragment implements AdminCategoryAdapter.OnItemClickListener {
    private FragmentAdminCategoryBinding binding;
    private AdminCategoryViewModel viewModel;
    private AdminCategoryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(AdminCategoryViewModel.class);
        binding = FragmentAdminCategoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new AdminCategoryAdapter(Collections.emptyList(), this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setClipToPadding(false);
        binding.recyclerView.setPadding(0, 0, 0, 100);

        // register observer
        viewModel.getCategoriesResult().observe(getViewLifecycleOwner(), response -> {
            binding.layoutLoading.setVisibility(View.GONE);
            adapter.setDataSource(response);
        });
        binding.cardViewAddCategory.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_admin);
            navController.navigate(R.id.action_nav_admin_category_to_nav_admin_input_category);
        });

        // call API get list categories
        binding.layoutLoading.setVisibility(View.VISIBLE);
        viewModel.loadCategories();
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    @Override
    public void onItemClick(CategoryModel model) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("editCategory", model);
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_admin);
        navController.navigate(R.id.action_nav_admin_category_to_nav_admin_input_category, bundle);
    }

    @Override
    public void onItemDetailClick(CategoryModel model) {
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("parent_category", model);
//        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_admin);
//        navController.navigate(R.id.action_nav_admin_category_to_nav_admin_sub_category, bundle);
    }
}
