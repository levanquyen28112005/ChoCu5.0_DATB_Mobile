package edu.mb.oldzy.domain.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import edu.mb.oldzy.domain.ApiClient;
import edu.mb.oldzy.domain.ApiService;
import edu.mb.oldzy.domain.model.BaseResponse;
import edu.mb.oldzy.domain.model.SlideResponse;
import edu.mb.oldzy.domain.model.SlideStatsResponse;
import edu.mb.oldzy.domain.request.CategoryRequest;
import edu.mb.oldzy.domain.request.SlideRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SlideRepository {

    private final ApiService apiService;

    public SlideRepository() {
        apiService = ApiClient.getApiService();
    }

    public LiveData<List<SlideResponse>> getSlides() {
        MutableLiveData<List<SlideResponse>> data = new MutableLiveData<>();

        apiService.getSlides().enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse<List<SlideResponse>>> call,
                                   @NonNull Response<BaseResponse<List<SlideResponse>>> response
            ) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body().getData());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse<List<SlideResponse>>> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<SlideStatsResponse> getSlideStats() {
        MutableLiveData<SlideStatsResponse> data = new MutableLiveData<>();

        apiService.getSlideStats().enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse<SlideStatsResponse>> call,
                                   @NonNull Response<BaseResponse<SlideStatsResponse>> response
            ) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body().getData());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse<SlideStatsResponse>> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<Boolean> addSlide(String token, String description, String image) {
        MutableLiveData<Boolean> data = new MutableLiveData<>();

        apiService.addSlide(token, new SlideRequest(description, image)).enqueue(new Callback<>() {
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

    public LiveData<Boolean> deleteSlide(String token, int id) {
        MutableLiveData<Boolean> data = new MutableLiveData<>();

        apiService.deleteSlide(token, id).enqueue(new Callback<>() {
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
