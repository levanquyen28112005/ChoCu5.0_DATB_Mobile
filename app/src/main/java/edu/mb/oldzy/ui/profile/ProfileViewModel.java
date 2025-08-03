package edu.mb.oldzy.ui.profile;

import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import edu.mb.oldzy.domain.model.UserResponse;
import edu.mb.oldzy.domain.repository.UserRepository;

public class ProfileViewModel extends ViewModel {

    private final UserRepository userRepository = new UserRepository();

    private final MutableLiveData<UserResponse> userResult = new MutableLiveData<>();
    private final MutableLiveData<String> accessToken = new MutableLiveData<>();

    public LiveData<String> getAccessToken() {
        return accessToken;
    }

    public LiveData<UserResponse> getUserResult() {
        return userResult;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken.postValue(accessToken);
        if (!TextUtils.isEmpty(accessToken)) {
            getUserInfo(accessToken);
        }
    }

    public void getUserInfo(String token) {
        userRepository.getUserInfo(token).observeForever(userResult::setValue);
    }
}
