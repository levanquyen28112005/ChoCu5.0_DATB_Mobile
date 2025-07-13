package edu.mb.oldzy.ui.admin.slides;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import edu.mb.oldzy.databinding.FragmentAdminCreateSlideBinding;

public class AdminCreateSlideFragment extends Fragment {

    private FragmentAdminCreateSlideBinding binding;
    private SlidesViewModel viewModel;

    ActivityResultLauncher<Intent> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    Uri imageUri;
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
        viewModel = new ViewModelProvider(this).get(SlidesViewModel.class);
        binding = FragmentAdminCreateSlideBinding.inflate(inflater, container, false);
        viewModel.getInsertResult().observe(getViewLifecycleOwner(), result -> {
            if (result) {
                Toast.makeText(requireContext(), "Thêm slide thành công", Toast.LENGTH_SHORT).show();
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
            } else {
                Toast.makeText(requireContext(), "Thêm slide thất bại", Toast.LENGTH_SHORT).show();
                binding.layoutLoading.setVisibility(View.GONE);
            }
        });
        return binding.getRoot();
    }

    @SuppressLint("IntentReset")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.cardImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent);
        });
        binding.ivClose.setOnClickListener(v -> {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });
        binding.ivSave.setOnClickListener(v -> {
            String description = binding.edtDescription.getText().toString().trim();
            if (description.isEmpty()) {
                binding.edtDescription.setError("Vui lòng nhập mô tả");
                return;
            }
            if (viewModel.getImageUri() == null) {
                binding.edtDescription.setError("Vui lòng chọn ảnh");
                return;
            }
            binding.layoutLoading.setVisibility(View.VISIBLE);

            SharedPreferences sharedPref = requireContext().getSharedPreferences("auth", Context.MODE_PRIVATE);
            String token = sharedPref.getString("access_token", null);

            viewModel.addSlide(requireContext(), token, description);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
