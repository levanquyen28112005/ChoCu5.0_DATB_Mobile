package edu.mb.oldzy.domain.repository;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import edu.mb.oldzy.domain.ApiClient;
import edu.mb.oldzy.domain.ApiService;
import edu.mb.oldzy.domain.model.FileUploadResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FileUploadRepository {

    private final ApiService apiService;

    public FileUploadRepository() {
        this.apiService = ApiClient.getApiService();
    }

    public LiveData<FileUploadResponse> uploadFile(Context context, String token, Uri uri) throws URISyntaxException {
        MutableLiveData<FileUploadResponse> data = new MutableLiveData<>();

        if (uri == null) return data;
        MultipartBody.Part filePart = prepareFilePart(context, uri);

        apiService.uploadImage(token, filePart).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<FileUploadResponse> call,
                                   @NonNull Response<FileUploadResponse> response
            ) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<FileUploadResponse> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    private MultipartBody.Part prepareFilePart(Context context, Uri uri) {
        ContentResolver contentResolver = context.getContentResolver();
        String mimeType = contentResolver.getType(uri);

        // Đọc nội dung từ URI
        InputStream inputStream;
        try {
            inputStream = contentResolver.openInputStream(uri);
            byte[] inputData = null;
            if (inputStream != null) {
                inputData = getBytes(inputStream);
            }

            RequestBody requestBody = null;
            if (mimeType != null) {
                if (inputData != null) {
                    requestBody = RequestBody.create(
                            MediaType.parse(mimeType),
                            inputData
                    );
                }
            }

            String fileName = getFileName(context, uri);
            if (requestBody != null) {
                return MultipartBody.Part.createFormData("file", fileName, requestBody);
            }
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    private byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    @SuppressLint("Range")
    private String getFileName(Context context, Uri uri) {
        String result = null;
        if ("content".equals(uri.getScheme())) {
            try (Cursor cursor = context.getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }
}
