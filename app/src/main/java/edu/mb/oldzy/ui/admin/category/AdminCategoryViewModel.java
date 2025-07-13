package edu.mb.oldzy.ui.admin.category;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import edu.mb.oldzy.data.model.CategoryModel;
import edu.mb.oldzy.domain.model.CategoryResponse;
import edu.mb.oldzy.domain.repository.CategoryRepository;
import edu.mb.oldzy.domain.repository.FileUploadRepository;

public class AdminCategoryViewModel extends ViewModel {

    private final CategoryRepository categoryRepository = new CategoryRepository();
    private final FileUploadRepository fileUploadRepository = new FileUploadRepository();

    private final MutableLiveData<List<CategoryModel>> categoriesResult = new MutableLiveData<>();
    private final MutableLiveData<Boolean> insertCategoryResult = new MutableLiveData<>();
    private final MutableLiveData<Boolean> updateCategoryResult = new MutableLiveData<>();
    private final MutableLiveData<Boolean> deleteCategoryResult = new MutableLiveData<>();

    public LiveData<List<CategoryModel>> getCategoriesResult() {
        return categoriesResult;
    }

    public LiveData<Boolean> getInsertCategoryResult() {
        return insertCategoryResult;
    }

    public LiveData<Boolean> getUpdateCategoryResult() {
        return updateCategoryResult;
    }

    public LiveData<Boolean> getDeleteCategoryResult() {
        return deleteCategoryResult;
    }

    private final MutableLiveData<CategoryModel> parentCategoryResult = new MutableLiveData<>();

    public LiveData<CategoryModel> getParentCategoryResult() {
        return parentCategoryResult;
    }

    private final MutableLiveData<CategoryModel> editCategoryResult = new MutableLiveData<>();

    public LiveData<CategoryModel> getEditCategoryResult() {
        return editCategoryResult;
    }

    public void loadCategories() {
        categoryRepository.getAllCategories().observeForever(categoryResponses -> {
            List<CategoryModel> categoryModels = categoryResponses.stream()
                    .map(CategoryResponse::toCategoryModel)
                    .collect(Collectors.toList());

            categoriesResult.postValue(categoryModels);
        });
    }

    // region : Input category
    private CategoryModel parentCategory;
    private CategoryModel editCategory;

    public CategoryModel getParentCategory() {
        return parentCategory;
    }

    public CategoryModel getEditCategory() {
        return editCategory;
    }

    public void setArguments(Bundle bundle) {
        if (bundle != null && bundle.containsKey("parentCategory")) {
            parentCategory = (CategoryModel) bundle.getSerializable("parentCategory");
            parentCategoryResult.postValue(parentCategory);
        } else if (bundle != null && bundle.containsKey("editCategory")) {
            editCategory = (CategoryModel) bundle.getSerializable("editCategory");
            editCategoryResult.postValue(editCategory);
        } else {
            parentCategory = null;
            editCategory = null;
            parentCategoryResult.postValue(null);
            editCategoryResult.postValue(null);
        }
    }
    // endregion

    private Uri imageUri;

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public void addCategory(Context context, String token, String name, String description) {
        if (imageUri != null) {
            try {
                fileUploadRepository.uploadFile(context, token, imageUri).observeForever(
                        fileUploadResponse -> {
                            categoryRepository.addCategory(token, name, description, null, fileUploadResponse.getUrl())
                                    .observeForever(insertCategoryResult::setValue);
                        });
            } catch (URISyntaxException exception) {
                insertCategoryResult.postValue(false);
            }
        } else {
            categoryRepository.addCategory(token, name, description, null, null)
                    .observeForever(insertCategoryResult::setValue);
        }
    }

    public void updateCategory(Context context, String token, String name, String description) {
        Long parentId = null;
        if (!TextUtils.isEmpty(editCategory.getParentId())) {
            parentId = Long.parseLong(editCategory.getParentId());
        }
        if (imageUri != null) {
            try {
                Long finalParentId = parentId;
                fileUploadRepository.uploadFile(context, token, imageUri).observeForever(
                        fileUploadResponse -> {
                            categoryRepository.updateCategory(token, editCategory.getId(), name, description, finalParentId, fileUploadResponse.getUrl())
                                    .observeForever(updateCategoryResult::setValue);
                        });
            } catch (URISyntaxException exception) {
                updateCategoryResult.postValue(false);
            }
        } else {
            categoryRepository.updateCategory(token, editCategory.getId(), name, description, parentId, editCategory.getImage())
                    .observeForever(updateCategoryResult::setValue);
        }
    }

    public void deleteCategory(String token, int id) {
        categoryRepository.deleteCategory(token, id).observeForever(deleteCategoryResult::setValue);
    }
}
