package edu.mb.oldzy.ui.admin.users;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.mb.oldzy.domain.model.UserResponse;
import edu.mb.oldzy.domain.repository.UserRepository;

public class AdminUsersViewModel extends ViewModel {
    private final UserRepository userRepository = new UserRepository();

    private final MutableLiveData<List<UserResponse>> usersResult = new MutableLiveData<>();

    public LiveData<List<UserResponse>> getUsersResult() {
        return usersResult;
    }


    public void getUsers(String token) {
        userRepository.getUsers(token).observeForever(usersResult::setValue);
    }

}
