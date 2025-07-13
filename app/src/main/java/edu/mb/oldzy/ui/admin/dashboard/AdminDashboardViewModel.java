package edu.mb.oldzy.ui.admin.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import edu.mb.oldzy.domain.model.SlideStatsResponse;
import edu.mb.oldzy.domain.repository.SlideRepository;

public class AdminDashboardViewModel extends ViewModel {

    private final SlideRepository slideRepository = new SlideRepository();

    private final MutableLiveData<SlideStatsResponse> slideStatsResponseMutableLiveData = new MutableLiveData<>();

    public LiveData<SlideStatsResponse> getSlideStatsResponseMutableLiveData() {
        return slideStatsResponseMutableLiveData;
    }

    public void getSlideStats() {
        slideRepository.getSlideStats().observeForever(slideStatsResponseMutableLiveData::postValue);
    }
}
