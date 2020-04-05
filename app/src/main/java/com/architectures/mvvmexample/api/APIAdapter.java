package com.architectures.mvvmexample.api;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.architectures.mvvmexample.model.Project;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIAdapter {

    private static APIAdapter apiAdapter;
    private APIService apiService;


    private APIAdapter() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIService.HTTPS_API_GITHUB_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(APIService.class);
    }

    public synchronized static APIAdapter getInstance() {
        if (apiAdapter == null) {
            apiAdapter = new APIAdapter();
        }
        return apiAdapter;
    }

    public LiveData<List<Project>> getProjectList(String userId) {
        final MutableLiveData<List<Project>> list = new MutableLiveData<>();

        apiService.getProjectList(userId).enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                list.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {
                list.setValue(null);
            }
        });
        return list;
    }

    public LiveData<Project> getProjectDetails(String userID, String projectName) {
        final MutableLiveData<Project> data = new MutableLiveData<>();

        apiService.getProjectDetails(userID, projectName).enqueue(new Callback<Project>() {
            @Override
            public void onResponse(Call<Project> call, Response<Project> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Project> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
