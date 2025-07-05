package edu.mb.oldzy.domain.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import edu.mb.oldzy.domain.ApiClient;
import edu.mb.oldzy.domain.ApiService;
import edu.mb.oldzy.domain.model.BaseResponse;
import edu.mb.oldzy.domain.model.CategoryResponse;
import edu.mb.oldzy.domain.request.CategoryRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository {

    private final ApiService apiService;

    public CategoryRepository() {
        this.apiService = ApiClient.getApiService();
    }

    public LiveData<List<CategoryResponse>> getAllCategories() {
        MutableLiveData<List<CategoryResponse>> data = new MutableLiveData<>();

        apiService.getAllCategories().enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse<List<CategoryResponse>>> call,
                                   @NonNull Response<BaseResponse<List<CategoryResponse>>> response
            ) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body().getData());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse<List<CategoryResponse>>> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    public LiveData<Boolean> addCategory(String token, String name, String description, Long parentId, String image) {
        MutableLiveData<Boolean> data = new MutableLiveData<>();

        apiService.addCategory(token, new CategoryRequest(name, description, parentId, image)).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse<String>> call,
                                   @NonNull Response<BaseResponse<String>> response
            ) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body().getCode().equals("0"));
                } else {
                    data.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse<String>> call, @NonNull Throwable t) {
                data.setValue(false);
            }
        });

        return data;
    }

    public LiveData<Boolean> updateCategory(String token, String id, String name, String description, Long parentId, String image) {
        MutableLiveData<Boolean> data = new MutableLiveData<>();

        apiService.updateCategory(token, new CategoryRequest(Integer.parseInt(id), name, description, parentId, image)).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse<String>> call,
                                   @NonNull Response<BaseResponse<String>> response
            ) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body().getCode().equals("0"));
                } else {
                    data.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse<String>> call, @NonNull Throwable t) {
                data.setValue(false);
            }
        });

        return data;
    }

    public LiveData<Boolean> deleteCategory(String token, int id) {
        MutableLiveData<Boolean> data = new MutableLiveData<>();

        apiService.deleteCategory(token, id).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse<String>> call,
                                   @NonNull Response<BaseResponse<String>> response
            ) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body().getCode().equals("0"));
                } else {
                    data.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse<String>> call, @NonNull Throwable t) {
                data.setValue(false);
            }
        });

        return data;
    }
}
