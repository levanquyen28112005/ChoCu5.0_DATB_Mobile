package edu.mb.oldzy.ui.post.create_post;

import static androidx.navigation.Navigation.findNavController;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import edu.mb.oldzy.R;
import edu.mb.oldzy.data.model.CategoryModel;
import edu.mb.oldzy.data.model.ImagePostModel;
import edu.mb.oldzy.databinding.FragmentCreatePostBinding;
import edu.mb.oldzy.domain.model.UserResponse;
import edu.mb.oldzy.domain.request.PostRequest;
import edu.mb.oldzy.ui.MainActivity;

public class CreatePostFragment extends Fragment implements ImageCreatePostAdapter.OnImageClickListener {

    private FragmentCreatePostBinding binding;
    private ImageCreatePostAdapter imageCreatePostAdapter;
    private ActivityResultLauncher<Intent> pickImagesLauncher;
    private final List<ImagePostModel> imagePostModels = new ArrayList<>();
    private CreatePostViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(CreatePostViewModel.class);
        binding = FragmentCreatePostBinding.inflate(inflater, container, false);
        pickImagesLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data == null) return;
                        if (data.getData() != null) {
                            imagePostModels.add(new ImagePostModel(data.getData()));
                        } else if (data.getClipData() != null) {
                            ClipData clipData = data.getClipData();
                            for (int i = 0; i < clipData.getItemCount(); i++) {
                                Uri imageUri = clipData.getItemAt(i).getUri();
                                imagePostModels.add(new ImagePostModel(imageUri));
                            }
                        }
                        imageCreatePostAdapter.setImagePostModels(imagePostModels);
                    }
                }
        );
        viewModel.getResult().observe(getViewLifecycleOwner(), result -> {
            if (result == null) return;
            binding.layoutLoading.setVisibility(View.GONE);
            if (result) {
                findNavController(binding.getRoot()).navigate(R.id.action_navigation_create_post_to_navigation_created_post);
            } else {
                Toast.makeText(requireContext(), "Đăng bài thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imagePostModels.add(new ImagePostModel(true));
        imageCreatePostAdapter = new ImageCreatePostAdapter(imagePostModels, this);

        binding.ivBack.setOnClickListener(v -> {
            findNavController(binding.getRoot()).popBackStack();
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerView.setAdapter(imageCreatePostAdapter);

        binding.opvCondition.addListener(v -> {
            showConditionDialog(requireContext());
        });
        binding.opvCategory.addListener(v -> {
            showCategoryDialog(requireContext(), viewModel.getCategories());
        });
        binding.btnPost.setOnClickListener(v -> {
            View currentFocus = requireView().findFocus();
            if (currentFocus != null) {
                currentFocus.clearFocus();

                InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
            }

            if (viewModel.getSelectedCategory() == null) {
                Toast.makeText(requireContext(), "Vui lòng chọn danh mục", Toast.LENGTH_SHORT).show();
                return;
            }

            String price = binding.opvPrice.getValue();
            if (price.isEmpty()) {
                Toast.makeText(requireContext(), "Vui lòng nhập giá", Toast.LENGTH_SHORT).show();
                binding.opvPrice.focus();
                return;
            }
            String title = binding.opvTitle.getValue();
            if (title.isEmpty()) {
                Toast.makeText(requireContext(), "Vui lòng nhập tiêu đề", Toast.LENGTH_SHORT).show();
                binding.opvTitle.focus();
                return;
            }
            String description = binding.edtDescription.getText().toString().trim();
            if (description.isEmpty()) {
                Toast.makeText(requireContext(), "Vui lòng nhập mô tả", Toast.LENGTH_SHORT).show();
                binding.edtDescription.requestFocus();
                return;
            }
            if (imagePostModels.size() < 2) {
                Toast.makeText(requireContext(), "Vui lòng chọn ít nhất 2 hình ảnh", Toast.LENGTH_SHORT).show();
                return;
            }
            if (viewModel.getCurrentUser() == null) {
                Toast.makeText(requireContext(), "Vui lòng đăng nhập", Toast.LENGTH_SHORT).show();
                return;
            }
            String phone = binding.opvSellerPhone.getValue();
            if (phone.isEmpty()) {
                Toast.makeText(requireContext(), "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
                binding.opvSellerPhone.focus();
                return;
            }
            if (phone.length() < 10) {
                Toast.makeText(requireContext(), "Số điện thoại phải có ít nhất 10 số", Toast.LENGTH_SHORT).show();
                binding.opvSellerPhone.focus();
                return;
            }
            String address = binding.opvSellerAddress.getValue();
            if (address.isEmpty()) {
                Toast.makeText(requireContext(), "Vui lòng nhập địa chỉ", Toast.LENGTH_SHORT).show();
                binding.opvSellerAddress.focus();
                return;
            }

            PostRequest postRequest = new PostRequest(
                    Integer.parseInt(viewModel.getSelectedCategory().getId()),
                    viewModel.isBrandNew(),
                    binding.opvBrand.getValue(),
                    binding.opvColor.getValue(),
                    binding.opvOther.getValue(),
                    price,
                    title,
                    description,
                    phone,
                    address,
                    viewModel.getCurrentUser().getUsername()
            );
            SharedPreferences sharedPref = requireContext().getSharedPreferences("auth", Context.MODE_PRIVATE);
            String token = sharedPref.getString("access_token", null);

            binding.layoutLoading.setVisibility(View.VISIBLE);
            viewModel.uploadImagesThenCreatePost(
                    requireContext(),
                    token,
                    imagePostModels,
                    postRequest
            );
        });

        UserResponse userResponse = ((MainActivity) requireActivity()).user;
        if (userResponse == null) {
            return;
        }
        viewModel.setCurrentUser(userResponse);
        binding.opvSellerName.setInput(userResponse.getFullName());
        viewModel.loadCategories();
    }

    private void showConditionDialog(Context context) {
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_condition_select, null);

        RadioGroup radioGroup = dialogView.findViewById(R.id.radioGroup);
        RadioButton radioNew = dialogView.findViewById(R.id.radioNew);
        RadioButton radioUsed = dialogView.findViewById(R.id.radioUsed);

        String currentCondition = binding.opvCondition.getSubTitle();
        if ("Hàng mới".equals(currentCondition)) {
            radioNew.setChecked(true);
        } else if ("Đã qua sử dụng".equals(currentCondition)) {
            radioUsed.setChecked(true);
        }
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Lựa chọn tình trạng")
                .setView(dialogView)
                .create();

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            String selectedCondition = "";

            if (checkedId == R.id.radioNew) {
                selectedCondition = "Hàng mới";
            } else if (checkedId == R.id.radioUsed) {
                selectedCondition = "Đã qua sử dụng";
            }
            viewModel.updateBrandNew(checkedId == R.id.radioNew);
            binding.opvCondition.setSubtitle(selectedCondition);
            dialog.dismiss();
        });

        dialog.show();
    }

    private void showCategoryDialog(Context context, List<CategoryModel> categoryList) {
        String[] itemNames = new String[categoryList.size()];
        for (int i = 0; i < categoryList.size(); i++) {
            itemNames[i] = categoryList.get(i).getName();
        }

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Chọn danh mục")
                .setItems(itemNames, (dialogInterface, which) -> {
                    CategoryModel selectedCategory = categoryList.get(which);
                    viewModel.updateCategory(selectedCategory);
                    binding.opvCategory.setSubtitle(selectedCategory.getName());
                    dialogInterface.dismiss();
                })
                .create();

        dialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onAddImageClick() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        pickImagesLauncher.launch(Intent.createChooser(intent, "Select Pictures"));
    }

    @Override
    public void onRemoveImageClick(ImagePostModel model) {
        imagePostModels.remove(model);
        imageCreatePostAdapter.setImagePostModels(imagePostModels);
    }
}
