package edu.mb.oldzy.domain.response;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import edu.mb.oldzy.domain.ApiClient;
import edu.mb.oldzy.domain.ApiService;
import edu.mb.oldzy.domain.model.BaseResponse;
import edu.mb.oldzy.domain.model.TokenResponse;
import edu.mb.oldzy.domain.request.LoginRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {
    private final ApiService apiService;

    public LoginRepository() {
        apiService = ApiClient.getApiService();
    }

    public LiveData<TokenResponse> login(String email, String password) {
        MutableLiveData<TokenResponse> data = new MutableLiveData<>();

        apiService.login(new LoginRequest(email, password)).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse<TokenResponse>> call,
                                   @NonNull Response<BaseResponse<TokenResponse>> response
            ) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body().getData());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse<TokenResponse>> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }
}
