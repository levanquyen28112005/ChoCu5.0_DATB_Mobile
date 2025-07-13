package edu.mb.oldzy.ui.admin.slides;

import android.content.Context;
import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import edu.mb.oldzy.data.model.SlideModel;
import edu.mb.oldzy.domain.model.SlideResponse;
import edu.mb.oldzy.domain.repository.FileUploadRepository;
import edu.mb.oldzy.domain.repository.SlideRepository;

public class SlidesViewModel extends ViewModel {

    private final SlideRepository slideRepository = new SlideRepository();
    private final FileUploadRepository fileUploadRepository = new FileUploadRepository();

    private final MutableLiveData<List<SlideModel>> slidesResult = new MutableLiveData<>();
    private final MutableLiveData<Boolean> insertResult = new MutableLiveData<>();
    private final MutableLiveData<Boolean> deleteResult = new MutableLiveData<>();

    public LiveData<Boolean> getInsertResult() {
        return insertResult;
    }

    public LiveData<Boolean> getDeleteResult() {
        return deleteResult;
    }

    public LiveData<List<SlideModel>> getSlidesResult() {
        return slidesResult;
    }

    private Uri imageUri;

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public void loadSlides() {
        slideRepository.getSlides().observeForever(responses -> {
            List<SlideModel> slideModels = responses.stream()
                    .map(SlideResponse::toSlideModel)
                    .collect(Collectors.toList());

            slidesResult.postValue(slideModels);
        });
    }

    public void addSlide(Context context, String token, String description) {
        if (imageUri != null) {
            try {
                fileUploadRepository.uploadFile(context, token, imageUri).observeForever(
                        fileUploadResponse -> {
                            slideRepository.addSlide(token, description, fileUploadResponse.getUrl())
                                    .observeForever(insertResult::setValue);
                        });
            } catch (URISyntaxException exception) {
                insertResult.postValue(false);
            }
        }
    }

    public void deleteCategory(String token, int id) {
        slideRepository.deleteSlide(token, id).observeForever(deleteResult::setValue);
    }
}
