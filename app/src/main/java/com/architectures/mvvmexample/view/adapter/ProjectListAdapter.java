package com.architectures.mvvmexample.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.architectures.mvvmexample.R;
import com.architectures.mvvmexample.callbacks.OnProjectClickCallback;
import com.architectures.mvvmexample.databinding.ProjectListViewItemBinding;
import com.architectures.mvvmexample.model.Project;

import java.util.List;
import java.util.Objects;

public class ProjectListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Project> list;
    private OnProjectClickCallback callback;

    public ProjectListAdapter(OnProjectClickCallback callback) {
        this.callback = callback;
    }

    public void setProjectList(final List<Project> projectList) {
        if (this.list == null) {
            this.list = projectList;
            notifyItemRangeInserted(0, projectList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {

                @Override
                public int getOldListSize() {
                    return ProjectListAdapter.this.list.size();
                }

                @Override
                public int getNewListSize() {
                    return projectList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return ProjectListAdapter.this.list.get(oldItemPosition).id ==
                            projectList.get(newItemPosition).id;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Project project = projectList.get(newItemPosition);
                    Project old = projectList.get(oldItemPosition);
                    return project.id == old.id
                            && Objects.equals(project.git_url, old.git_url);
                }
            });
            this.list = projectList;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProjectListViewItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.project_list_view_item,
                        parent, false);
        binding.setCallback(callback);
        return new ProjectListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProjectListViewHolder viewHolder = (ProjectListViewHolder) holder;
        viewHolder.binding.setProject(list.get(position));
        viewHolder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    private class ProjectListViewHolder extends RecyclerView.ViewHolder {

        private ProjectListViewItemBinding binding;

        ProjectListViewHolder(ProjectListViewItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
