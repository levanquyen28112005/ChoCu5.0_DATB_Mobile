package edu.mb.oldzy.ui.admin.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.stream.Collectors;

import edu.mb.oldzy.data.model.CategoryModel;
import edu.mb.oldzy.domain.model.CategoryResponse;
import edu.mb.oldzy.domain.model.SlideStatsResponse;
import edu.mb.oldzy.domain.repository.CategoryRepository;
import edu.mb.oldzy.domain.repository.SlideRepository;

public class AdminDashboardViewModel extends ViewModel {

    private final CategoryRepository categoryRepository = new CategoryRepository();
    private final SlideRepository slideRepository = new SlideRepository();

    private final MutableLiveData<SlideStatsResponse> slideStatsResponseMutableLiveData = new MutableLiveData<>();

    private final MutableLiveData<List<CategoryModel>> categoriesResult = new MutableLiveData<>();
    public LiveData<List<CategoryModel>> getCategoriesResult() {
        return categoriesResult;
    }

    public LiveData<SlideStatsResponse> getSlideStatsResponseMutableLiveData() {
        return slideStatsResponseMutableLiveData;
    }

    public void getSlideStats() {
        slideRepository.getSlideStats().observeForever(slideStatsResponseMutableLiveData::postValue);
    }

    public void loadCategories() {
        categoryRepository.getAllCategories().observeForever(categoryResponses -> {
            List<CategoryModel> categoryModels = categoryResponses.stream()
                    .map(CategoryResponse::toCategoryModel)
                    .collect(Collectors.toList());

            categoriesResult.postValue(categoryModels);
        });
    }
}
