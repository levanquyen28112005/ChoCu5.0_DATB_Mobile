package edu.mb.oldzy.ui.splash;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import edu.mb.oldzy.domain.model.UserResponse;
import edu.mb.oldzy.domain.repository.UserRepository;

public class SplashViewModel extends ViewModel {

    private final UserRepository userRepository = new UserRepository();

    private final MutableLiveData<UserResponse> userResult = new MutableLiveData<>();

    public LiveData<UserResponse> getUserResult() {
        return userResult;
    }

    public void getUserInfo(String token) {
        userRepository.getUserInfo(token).observeForever(userResult::setValue);
    }
}
