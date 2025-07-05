package edu.mb.oldzy.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import edu.mb.oldzy.domain.model.TokenResponse;
import edu.mb.oldzy.domain.model.UserResponse;
import edu.mb.oldzy.domain.repository.UserRepository;
import edu.mb.oldzy.domain.response.LoginRepository;

public class LoginViewModel extends ViewModel {
    private final LoginRepository repository = new LoginRepository();
    private final UserRepository userRepository = new UserRepository();
    private final MutableLiveData<TokenResponse> loginResult = new MutableLiveData<>();
    private final MutableLiveData<UserResponse> userResult = new MutableLiveData<>();

    public LiveData<TokenResponse> getLoginResult() {
        return loginResult;
    }

    public LiveData<UserResponse> getUserResult() {
        return userResult;
    }

    public void login(String email, String password) {
        repository.login(email, password).observeForever(loginResult::setValue);
    }

    public void getUserInfo(String token) {
        userRepository.getUserInfo(token).observeForever(userResult::setValue);
    }
}
