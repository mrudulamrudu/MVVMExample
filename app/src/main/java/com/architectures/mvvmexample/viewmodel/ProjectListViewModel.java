package com.architectures.mvvmexample.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.architectures.mvvmexample.api.APIAdapter;
import com.architectures.mvvmexample.model.Project;

import java.util.List;

public class ProjectListViewModel extends AndroidViewModel {

    private final LiveData<List<Project>> projectListObservable;

    public ProjectListViewModel(@NonNull Application application) {
        super(application);

        projectListObservable = APIAdapter.getInstance().getProjectList("Google");
    }

    public LiveData<List<Project>> getProjectListObservable() {
        return projectListObservable;
    }
}
