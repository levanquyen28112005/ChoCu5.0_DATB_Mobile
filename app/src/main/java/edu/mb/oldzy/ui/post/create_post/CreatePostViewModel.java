package edu.mb.oldzy.ui.post.create_post;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import edu.mb.oldzy.data.model.CategoryModel;
import edu.mb.oldzy.data.model.ImagePostModel;
import edu.mb.oldzy.domain.model.CategoryResponse;
import edu.mb.oldzy.domain.model.UserResponse;
import edu.mb.oldzy.domain.repository.CategoryRepository;
import edu.mb.oldzy.domain.repository.FileUploadRepository;
import edu.mb.oldzy.domain.repository.PostRepository;
import edu.mb.oldzy.domain.request.PostRequest;

public class CreatePostViewModel extends ViewModel {

    private final FileUploadRepository fileUploadRepository = new FileUploadRepository();
    private final PostRepository postRepository = new PostRepository();
    private final CategoryRepository categoryRepository = new CategoryRepository();
    private final MutableLiveData<List<CategoryModel>> categoriesResult = new MutableLiveData<>();

    private final MutableLiveData<Boolean> result = new MutableLiveData<>();
    public LiveData<Boolean> getResult() {
        return result;
    }

    public LiveData<List<CategoryModel>> getCategoriesResult() {
        return categoriesResult;
    }

    public List<CategoryModel> getCategories() {
        if (categoriesResult.getValue() == null) return List.of();
        return categoriesResult.getValue();
    }

    public void loadCategories() {
        categoryRepository.getAllCategories().observeForever(categoryResponses -> {
            if (categoryResponses == null || categoryResponses.isEmpty()) return;
            List<CategoryModel> categoryModels = categoryResponses.stream()
                    .map(CategoryResponse::toCategoryModel)
                    .collect(Collectors.toList());

            // updateCategory(categoryModels.get(0));
            categoriesResult.postValue(categoryModels);
        });
    }

    private CategoryModel selectedCategory;
    private boolean isBrandNew = true;
    private UserResponse currentUser;

    public void setCurrentUser(UserResponse currentUser) {
        this.currentUser = currentUser;
    }

    public UserResponse getCurrentUser() {
        return currentUser;
    }

    public CategoryModel getSelectedCategory() {
        return selectedCategory;
    }

    public boolean isBrandNew() {
        return isBrandNew;
    }

    public void updateCategory(CategoryModel categoryModel) {
        this.selectedCategory = categoryModel;
    }

    public void updateBrandNew(boolean isBrandNew) {
        this.isBrandNew = isBrandNew;
    }

    public void uploadImagesThenCreatePost(Context context, String token,
                                           List<ImagePostModel> imagePostModels,
                                           PostRequest postRequest) {

        List<String> uploadedUrls = new ArrayList<>();
        int total = imagePostModels.size();
        AtomicInteger uploadedCount = new AtomicInteger(0);

        if (total == 0) {
            return;
        }

        for (ImagePostModel model : imagePostModels) {
            Uri imageUri = model.getImageUri();
            if (imageUri != null) {
                try {
                    fileUploadRepository.uploadFile(context, token, imageUri).observeForever(response -> {
                        if (response != null && response.getUrl() != null) {
                            uploadedUrls.add(response.getUrl());
                        }

                        // Sau mỗi lần upload
                        if (uploadedCount.incrementAndGet() == total) {
                            postRequest.setImageUrls(uploadedUrls);
                            postRepository.createPost(token, postRequest).observeForever(result::setValue);
                        }
                    });
                } catch (URISyntaxException e) {
                    Log.e("GRT_456", "URISyntaxException in uploadImagesThenCreatePost " + e.getMessage());
                }
            } else {
                // Nếu Uri null vẫn tăng count
                if (uploadedCount.incrementAndGet() == total) {
                    postRequest.setImageUrls(uploadedUrls);
                    postRepository.createPost(token, postRequest).observeForever(result::setValue);
                }
            }
        }
    }
}
