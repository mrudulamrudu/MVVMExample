package com.architectures.mvvmexample.view.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.architectures.mvvmexample.R;
import com.architectures.mvvmexample.model.Project;

public class ProjectsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);
        if (savedInstanceState == null) {
            ProjectListFragment fragment = new ProjectListFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame, fragment, ProjectListFragment.TAG).commit();
        }

    }

    public void show(Project project) {
        ProjectDetailsFragment projectFragment = ProjectDetailsFragment.forProject(project.name);

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("project")
                .replace(R.id.frame,
                        projectFragment, null).commit();
    }

}
