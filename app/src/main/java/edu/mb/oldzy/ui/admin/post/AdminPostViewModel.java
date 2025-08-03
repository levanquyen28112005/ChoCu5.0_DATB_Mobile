package edu.mb.oldzy.ui.admin.post;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.mb.oldzy.domain.model.PostResponse;
import edu.mb.oldzy.domain.repository.PostRepository;

public class AdminPostViewModel extends ViewModel {

    private final PostRepository postRepository = new PostRepository();

    private final MutableLiveData<List<PostResponse>> pendingPosts = new MutableLiveData<>();

    public LiveData<List<PostResponse>> getPendingPosts() {
        return pendingPosts;
    }

    public void loadPosts(String token) {
        postRepository.getAllPost().observeForever(pendingPosts::setValue);
    }

    public void loadPostPending(String token) {
        postRepository.getAllPendingPosts(token).observeForever(pendingPosts::setValue);
    }

    public void loadPostApproved(String token) {
        postRepository.getAllApprovedPosts(token).observeForever(pendingPosts::setValue);
    }

    public void loadPostReject(String token) {
        postRepository.getAllRejectPosts(token).observeForever(pendingPosts::setValue);
    }
}
