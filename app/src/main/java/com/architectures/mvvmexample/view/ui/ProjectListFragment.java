package com.architectures.mvvmexample.view.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.architectures.mvvmexample.R;
import com.architectures.mvvmexample.callbacks.OnProjectClickCallback;
import com.architectures.mvvmexample.databinding.FragmentProjectlistBinding;
import com.architectures.mvvmexample.model.Project;
import com.architectures.mvvmexample.view.adapter.ProjectListAdapter;
import com.architectures.mvvmexample.viewmodel.ProjectListViewModel;

import java.util.List;

public class ProjectListFragment extends Fragment {

    public static final String TAG = ProjectListFragment.class.getSimpleName();
    private ProjectListAdapter projectListAdapter;
    private FragmentProjectlistBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_projectlist, container, false);
        projectListAdapter = new ProjectListAdapter(projectClickCallback);

        binding.projectList.setAdapter(projectListAdapter);
        binding.setIsLoading(true);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final ProjectListViewModel viewModel =
                ViewModelProviders.of(this).get(ProjectListViewModel.class);

        observeViewModel(viewModel);
    }

    private void observeViewModel(ProjectListViewModel viewModel) {
        viewModel.getProjectListObservable().observe(this, new Observer<List<Project>>() {
            @Override
            public void onChanged(@Nullable List<Project> projects) {
                if (projects != null) {
                    binding.setIsLoading(false);
                    projectListAdapter.setProjectList(projects);
                }
            }
        });
    }

    private final OnProjectClickCallback projectClickCallback = new OnProjectClickCallback() {
        @Override
        public void onProjectClick(Project project) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                ((ProjectsListActivity) getActivity()).show(project);
            }
        }
    };
}
