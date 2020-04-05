package com.architectures.mvvmexample.view.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.architectures.mvvmexample.R;
import com.architectures.mvvmexample.databinding.FragmentProjectDetailsBinding;
import com.architectures.mvvmexample.model.Project;
import com.architectures.mvvmexample.viewmodel.ProjectViewModel;

public class ProjectDetailsFragment extends Fragment {

    private static final String KEY_PROJECT_ID = "project_id";

    private FragmentProjectDetailsBinding binding;

    public static ProjectDetailsFragment forProject(String projectID) {
        ProjectDetailsFragment fragment = new ProjectDetailsFragment();
        Bundle args = new Bundle();

        args.putString(KEY_PROJECT_ID, projectID);
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_details, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() == null || getArguments() == null) return;
        ProjectViewModel.Factory factory = new ProjectViewModel.Factory(getActivity()
                .getApplication(), getArguments().getString(KEY_PROJECT_ID));

        ProjectViewModel projectViewModel = ViewModelProviders.of(this, factory).get(ProjectViewModel.class);

        binding.setProjectViewModel(projectViewModel);
        binding.setIsLoading(true);

        observeViewModel(projectViewModel);
    }

    private void observeViewModel(final ProjectViewModel viewModel) {
        viewModel.getProjectObservable().observe(this, new Observer<Project>() {
            @Override
            public void onChanged(@Nullable Project project) {
                if (project != null) {
                    binding.setIsLoading(false);
                    viewModel.setProject(project);
                }
            }
        });
    }
}
