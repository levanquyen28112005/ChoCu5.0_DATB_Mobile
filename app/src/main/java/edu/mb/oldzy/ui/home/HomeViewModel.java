package edu.mb.oldzy.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.stream.Collectors;

import edu.mb.oldzy.data.model.CategoryModel;
import edu.mb.oldzy.data.model.SlideModel;
import edu.mb.oldzy.domain.model.CategoryResponse;
import edu.mb.oldzy.domain.model.PostResponse;
import edu.mb.oldzy.domain.model.SlideResponse;
import edu.mb.oldzy.domain.repository.CategoryRepository;
import edu.mb.oldzy.domain.repository.PostRepository;
import edu.mb.oldzy.domain.repository.SlideRepository;

public class HomeViewModel extends ViewModel {

    private final SlideRepository slideRepository = new SlideRepository();
    private final CategoryRepository categoryRepository = new CategoryRepository();
    private final PostRepository postRepository = new PostRepository();

    private final MutableLiveData<List<CategoryModel>> categoriesResult = new MutableLiveData<>();
    private final MutableLiveData<List<SlideModel>> slidesResult = new MutableLiveData<>();

    private final MutableLiveData<List<PostResponse>> hotPosts = new MutableLiveData<>();

    public LiveData<List<PostResponse>> getHotPosts() {
        return hotPosts;
    }

    public void loadPosts() {
        postRepository.getAllPost().observeForever(hotPosts::setValue);
    }

    public LiveData<List<SlideModel>> getSlidesResult() {
        return slidesResult;
    }
    public LiveData<List<CategoryModel>> getCategoriesResult() {
        return categoriesResult;
    }

    public void loadSlides() {
        slideRepository.getSlides().observeForever(responses -> {
            if (responses == null || responses.isEmpty()) return;
            List<SlideModel> slideModels = responses.stream()
                    .map(SlideResponse::toSlideModel)
                    .collect(Collectors.toList());

            slidesResult.postValue(slideModels);
        });
    }

    public void loadCategories() {
        categoryRepository.getAllCategories().observeForever(categoryResponses -> {
            if (categoryResponses == null || categoryResponses.isEmpty()) return;
            List<CategoryModel> categoryModels = categoryResponses.stream()
                    .map(CategoryResponse::toCategoryModel)
                    .collect(Collectors.toList());

            categoriesResult.postValue(categoryModels);
        });
    }
}