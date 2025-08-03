package edu.mb.oldzy.domain;

import java.util.List;

import edu.mb.oldzy.domain.model.BaseResponse;
import edu.mb.oldzy.domain.model.CategoryResponse;
import edu.mb.oldzy.domain.model.FileUploadResponse;
import edu.mb.oldzy.domain.model.PostResponse;
import edu.mb.oldzy.domain.model.SlideResponse;
import edu.mb.oldzy.domain.model.SlideStatsResponse;
import edu.mb.oldzy.domain.model.TokenResponse;
import edu.mb.oldzy.domain.model.UserResponse;
import edu.mb.oldzy.domain.request.CategoryRequest;
import edu.mb.oldzy.domain.request.LoginRequest;
import edu.mb.oldzy.domain.request.PostRequest;
import edu.mb.oldzy.domain.request.RegisterRequest;
import edu.mb.oldzy.domain.request.SlideRequest;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {

    @POST("common/register")
    Call<BaseResponse<TokenResponse>> register(@Body RegisterRequest param);

    @POST("common/login")
    Call<BaseResponse<TokenResponse>> login(@Body LoginRequest request);

    @GET("common/my")
    Call<BaseResponse<UserResponse>> getUserInfo(@Header("Authorization") String token);

    @GET("common/admin/users")
    Call<BaseResponse<List<UserResponse>>> getUsers(@Header("Authorization") String token);

    @GET("category/all")
    Call<BaseResponse<List<CategoryResponse>>> getAllCategories();

    @POST("category/add")
    Call<BaseResponse<String>> addCategory(@Header("Authorization") String token, @Body CategoryRequest request);

    @POST("category/update")
    Call<BaseResponse<String>> updateCategory(@Header("Authorization") String token, @Body CategoryRequest request);

    @DELETE("category/delete/{id}")
    Call<BaseResponse<String>> deleteCategory(@Header("Authorization") String token, @Path("id") int id);

    @Multipart
    @POST("files/upload")
    Call<FileUploadResponse> uploadImage(@Header("Authorization") String token, @Part MultipartBody.Part file);

    @GET("slide/all")
    Call<BaseResponse<List<SlideResponse>>> getSlides();

    @GET("slide/stats")
    Call<BaseResponse<SlideStatsResponse>> getSlideStats();

    @POST("slide/add")
    Call<BaseResponse<String>> addSlide(@Header("Authorization") String token, @Body SlideRequest request);

    @DELETE("slide/delete/{id}")
    Call<BaseResponse<String>> deleteSlide(@Header("Authorization") String token, @Path("id") int id);

    @POST("post/create")
    Call<BaseResponse<String>> createPost(@Header("Authorization") String token, @Body PostRequest request);

    @GET("post/pending")
    Call<BaseResponse<List<PostResponse>>> getAllPendingPosts(@Header("Authorization") String token);

    @GET("post/all")
    Call<BaseResponse<List<PostResponse>>> getAll();

    @GET("post/approved")
    Call<BaseResponse<List<PostResponse>>> getAllApprovedPosts(@Header("Authorization") String token);

    @GET("post/rejected")
    Call<BaseResponse<List<PostResponse>>> getAllRejectedPosts(@Header("Authorization") String token);
}
