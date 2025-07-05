package edu.mb.oldzy.ui.register;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import edu.mb.oldzy.domain.model.TokenResponse;
import edu.mb.oldzy.domain.response.RegisterRepository;

public class RegisterViewModel extends ViewModel {

    private final RegisterRepository repository = new RegisterRepository();
    private final MutableLiveData<TokenResponse> registerResult = new MutableLiveData<>();

    public LiveData<TokenResponse> getRegisterResult() {
        return registerResult;
    }

    public void register(String name, String email, String password) {
        repository.register(name, email, password).observeForever(registerResult::setValue);
    }
}
