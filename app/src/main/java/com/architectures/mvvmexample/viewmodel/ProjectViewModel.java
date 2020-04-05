package com.architectures.mvvmexample.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.architectures.mvvmexample.api.APIAdapter;
import com.architectures.mvvmexample.model.Project;

public class ProjectViewModel extends AndroidViewModel {

    private final LiveData<Project> projectObservable;
    private String projectId;

    public ObservableField<Project> project = new ObservableField<>();

    public ProjectViewModel(@NonNull Application application, String projectId) {
        super(application);

        this.projectId = projectId;

        projectObservable = APIAdapter.getInstance().getProjectDetails("Google", projectId);
    }

    public LiveData<Project> getProjectObservable() {
        return projectObservable;
    }

    public void setProject(Project project) {
        this.project.set(project);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String projectID;

        public Factory(@NonNull Application application, String projectID) {
            this.application = application;
            this.projectID = projectID;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new ProjectViewModel(application, projectID);
        }
    }
}
