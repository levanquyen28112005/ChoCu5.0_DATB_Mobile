package edu.mb.oldzy.domain.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import edu.mb.oldzy.domain.ApiClient;
import edu.mb.oldzy.domain.ApiService;
import edu.mb.oldzy.domain.model.BaseResponse;
import edu.mb.oldzy.domain.model.CategoryResponse;
import edu.mb.oldzy.domain.model.PostResponse;
import edu.mb.oldzy.domain.request.CategoryRequest;
import edu.mb.oldzy.domain.request.PostRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostRepository {

    private final ApiService apiService;

    public PostRepository() {
        this.apiService = ApiClient.getApiService();
    }

    public LiveData<List<PostResponse>> getAllPost() {
        MutableLiveData<List<PostResponse>> data = new MutableLiveData<>();

        apiService.getAll().enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse<List<PostResponse>>> call,
                                   @NonNull Response<BaseResponse<List<PostResponse>>> response
            ) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body().getData());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse<List<PostResponse>>> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    public LiveData<List<PostResponse>> getAllPendingPosts(String token) {
        MutableLiveData<List<PostResponse>> data = new MutableLiveData<>();

        apiService.getAllPendingPosts(token).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse<List<PostResponse>>> call,
                                   @NonNull Response<BaseResponse<List<PostResponse>>> response
            ) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body().getData());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse<List<PostResponse>>> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    public LiveData<List<PostResponse>> getAllApprovedPosts(String token) {
        MutableLiveData<List<PostResponse>> data = new MutableLiveData<>();

        apiService.getAllApprovedPosts(token).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse<List<PostResponse>>> call,
                                   @NonNull Response<BaseResponse<List<PostResponse>>> response
            ) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body().getData());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse<List<PostResponse>>> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    public LiveData<List<PostResponse>> getAllRejectPosts(String token) {
        MutableLiveData<List<PostResponse>> data = new MutableLiveData<>();

        apiService.getAllRejectedPosts(token).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse<List<PostResponse>>> call,
                                   @NonNull Response<BaseResponse<List<PostResponse>>> response
            ) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body().getData());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse<List<PostResponse>>> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    public LiveData<Boolean> createPost(String token, PostRequest request) {
        MutableLiveData<Boolean> data = new MutableLiveData<>();

        apiService.createPost(token, request).enqueue(new Callback<>() {
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
