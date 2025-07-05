package edu.mb.oldzy.ui.admin.category;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import edu.mb.oldzy.databinding.FragmentAdminInputCategoryBinding;
import edu.mb.oldzy.domain.ApiClient;

public class AdminInputCategoryFragment extends Fragment {
    private FragmentAdminInputCategoryBinding binding;
    private AdminCategoryViewModel viewModel;

    ActivityResultLauncher<Intent> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    Uri imageUri = null;
                    if (data != null) {
                        imageUri = data.getData();
                        viewModel.setImageUri(imageUri);
                        binding.ivImage.setImageURI(imageUri);
                    }
                }
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(AdminCategoryViewModel.class);
        binding = FragmentAdminInputCategoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.ivClose.setOnClickListener(v -> {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });
        binding.cardImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent);
        });
        binding.ivSave.setOnClickListener(v -> {
            String name = binding.edtName.getText().toString();
            String description = binding.edtDescription.getText().toString();

            if (TextUtils.isEmpty(name)) {
                binding.edtName.setError("Vui lòng nhập tên danh mục");
                return;
            }
            if (TextUtils.isEmpty(description)) {
                binding.edtDescription.setError("Vui lòng nhập mô tả");
                return;
            }
            binding.layoutLoading.setVisibility(View.VISIBLE);

            SharedPreferences sharedPref = requireContext().getSharedPreferences("auth", Context.MODE_PRIVATE);
            String token = sharedPref.getString("access_token", null);
            if (viewModel.getEditCategory() != null) {
                viewModel.updateCategory(requireContext(), token, name, description);
            } else {
                viewModel.addCategory(requireContext(), token, name, description);
            }
        });
        viewModel.getEditCategoryResult().observe(getViewLifecycleOwner(), result -> {
            if (result == null) return;
            binding.ivRemove.setVisibility(View.VISIBLE);
            binding.edtName.setText(result.getName());
            binding.edtDescription.setText(result.getDescription());
            Glide.with(requireContext())
                    .load(ApiClient.BASE_URL.substring(0, ApiClient.BASE_URL.length() - 1) + result.getImage())
                    .into(binding.ivImage);
            binding.layoutParentCategory.setVisibility(View.GONE);
            binding.tvCategory.setText("Sửa danh mục");
        });
        viewModel.getParentCategoryResult().observe(getViewLifecycleOwner(), result -> {
            if (result != null) {
                binding.layoutParentCategory.setVisibility(View.VISIBLE);
                Glide.with(requireContext())
                        .load(result.getImage())
                        .into(binding.ivImageParent);

                binding.tvNameParent.setText(result.getName());
            } else {
                binding.layoutParentCategory.setVisibility(View.GONE);
            }
        });
        viewModel.getInsertCategoryResult().observe(getViewLifecycleOwner(), result -> {
            if (result == null) return;
            binding.layoutLoading.setVisibility(View.GONE);
            if (result) {
                Toast.makeText(requireContext(), "Thêm danh mục thành công", Toast.LENGTH_SHORT).show();
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
            } else {
                Toast.makeText(requireContext(), "Thêm danh mục thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        viewModel.getUpdateCategoryResult().observe(getViewLifecycleOwner(), result -> {
            if (result == null) return;
            binding.layoutLoading.setVisibility(View.GONE);
            if (result) {
                Toast.makeText(requireContext(), "Sửa danh mục thành công", Toast.LENGTH_SHORT).show();
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
            } else {
                Toast.makeText(requireContext(), "Sửa danh mục thất bại", Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getDeleteCategoryResult().observe(getViewLifecycleOwner(), result -> {
            if (result == null) return;
            binding.layoutLoading.setVisibility(View.GONE);
            if (result) {
                Toast.makeText(requireContext(), "Xoá danh mục thành công", Toast.LENGTH_SHORT).show();
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
            } else {
                Toast.makeText(requireContext(), "Xoá danh mục thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        binding.ivRemove.setOnClickListener(v -> {
            if (viewModel.getEditCategory() == null || TextUtils.isEmpty(viewModel.getEditCategory().getId())) {
                return;
            }
            SharedPreferences sharedPref = requireContext().getSharedPreferences("auth", Context.MODE_PRIVATE);
            String token = sharedPref.getString("access_token", null);
            viewModel.deleteCategory(token, Integer.parseInt(viewModel.getEditCategory().getId()));
        });
        viewModel.setArguments(getArguments());
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}
