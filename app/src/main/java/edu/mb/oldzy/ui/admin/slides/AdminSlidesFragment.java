package edu.mb.oldzy.ui.admin.slides;

import android.app.AlertDialog;
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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import edu.mb.oldzy.R;
import edu.mb.oldzy.databinding.FragmentAdminSlidesBinding;

public class AdminSlidesFragment extends Fragment {

    private FragmentAdminSlidesBinding binding;
    private SlidesViewModel viewModel;
    private SlideAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(SlidesViewModel.class);
        binding = FragmentAdminSlidesBinding.inflate(inflater, container, false);
        viewModel.getSlidesResult().observe(getViewLifecycleOwner(), response -> {
            binding.layoutLoading.setVisibility(View.GONE);
            adapter.setDataSource(response);
        });
        viewModel.getInsertResult().observe(getViewLifecycleOwner(), response -> {
            binding.layoutLoading.setVisibility(View.GONE);
            viewModel.loadSlides();
        });
        viewModel.getDeleteResult().observe(getViewLifecycleOwner(), response -> {
            binding.layoutLoading.setVisibility(View.GONE);
            viewModel.loadSlides();
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.layoutLoading.setVisibility(View.VISIBLE);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new SlideAdapter(viewModel.getSlidesResult().getValue(), slideModel -> {
            SharedPreferences sharedPref = requireContext().getSharedPreferences("auth", Context.MODE_PRIVATE);
            String token = sharedPref.getString("access_token", null);

            new AlertDialog.Builder(requireContext())
                    .setTitle("Xác nhận xoá")
                    .setMessage("Bạn có chắc chắn muốn xoá slide này không?")
                    .setPositiveButton("Xoá", (dialog, which) -> {
                        viewModel.deleteCategory(token, slideModel.getId());
                    })
                    .setNegativeButton("Huỷ", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .show();
        });
        binding.cardViewAddSlide.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_admin);
            navController.navigate(R.id.action_nav_admin_slides_to_nav_admin_create_slide);
        });
        binding.recyclerView.setAdapter(adapter);
        viewModel.loadSlides();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
