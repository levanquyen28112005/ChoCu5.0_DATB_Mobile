package edu.mb.oldzy.domain.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import edu.mb.oldzy.domain.ApiClient;
import edu.mb.oldzy.domain.ApiService;
import edu.mb.oldzy.domain.model.BaseResponse;
import edu.mb.oldzy.domain.model.UserResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {

    private final ApiService apiService;

    public UserRepository() {
        apiService = ApiClient.getApiService();
    }

    public LiveData<UserResponse> getUserInfo(String token) {
        MutableLiveData<UserResponse> data = new MutableLiveData<>();

        apiService.getUserInfo(token).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse<UserResponse>> call,
                                   @NonNull Response<BaseResponse<UserResponse>> response
            ) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body().getData());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse<UserResponse>> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    public LiveData<List<UserResponse>> getUsers(String token) {
        MutableLiveData<List<UserResponse>> data = new MutableLiveData<>();

        apiService.getUsers(token).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse<List<UserResponse>>> call,
                                   @NonNull Response<BaseResponse<List<UserResponse>>> response
            ) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body().getData());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse<List<UserResponse>>> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }
}
