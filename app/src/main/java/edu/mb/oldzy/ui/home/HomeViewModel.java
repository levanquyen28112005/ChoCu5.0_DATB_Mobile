package edu.mb.oldzy.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.stream.Collectors;

import edu.mb.oldzy.data.model.SlideModel;
import edu.mb.oldzy.domain.model.SlideResponse;
import edu.mb.oldzy.domain.repository.SlideRepository;


public class HomeViewModel extends ViewModel {

    private final SlideRepository slideRepository = new SlideRepository();

    private final MutableLiveData<List<SlideModel>> slidesResult = new MutableLiveData<>();

    public LiveData<List<SlideModel>> getSlidesResult() {
        return slidesResult;
    }

    public void loadSlides() {
        slideRepository.getSlides().observeForever(responses -> {
            List<SlideModel> slideModels = responses.stream()
                    .map(SlideResponse::toSlideModel)
                    .collect(Collectors.toList());

            slidesResult.postValue(slideModels);
        });
    }
}