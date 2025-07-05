package edu.mb.oldzy.domain.response;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import edu.mb.oldzy.domain.ApiClient;
import edu.mb.oldzy.domain.ApiService;
import edu.mb.oldzy.domain.model.BaseResponse;
import edu.mb.oldzy.domain.model.TokenResponse;
import edu.mb.oldzy.domain.request.RegisterRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterRepository {
    private final ApiService apiService;

    public RegisterRepository() {
        apiService = ApiClient.getApiService();
    }

    public LiveData<TokenResponse> register(String name, String email, String password) {
        MutableLiveData<TokenResponse> data = new MutableLiveData<>();
        RegisterRequest request = new RegisterRequest(email, password, name);
        apiService.register(request).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse<TokenResponse>> call, @NonNull Response<BaseResponse<TokenResponse>> response) {
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
