package com.example.a29149.ecollaboration.model.project.task;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.a29149.ecollaboration.R;
import com.example.a29149.ecollaboration.model.project.task.activity.TaskDetailActivity;
import com.example.a29149.ecollaboration.model.project.task.adapter.TaskParentList;
import com.example.a29149.ecollaboration.util.AnnotationUtil;
import com.example.a29149.ecollaboration.util.annotation.ViewInject;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class TaskFragment extends Fragment {

    @ViewInject(R.id.task_parent_list)
    private ExpandableListView expandableListView;

    private TaskParentList taskParentListAdapter;

    public TaskFragment() {

    }

    public static TaskFragment newInstance(String param1, String param2) {
        TaskFragment fragment = new TaskFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        AnnotationUtil.injectViews(TaskFragment.this, view);
        AnnotationUtil.setClickListener(TaskFragment.this, view);

        //设置适配器
        taskParentListAdapter = new TaskParentList(getContext());
        expandableListView.setAdapter(taskParentListAdapter);

        for (int i = 0; i < taskParentListAdapter.getGroupCount(); i++) {
            if (taskParentListAdapter.getGroup(i) != null) {
                expandableListView.expandGroup(i);
            }
        }

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Intent intent = new Intent(getActivity(), TaskDetailActivity.class);
                intent.putExtra("task_index", groupPosition);
                startActivity(intent);
                return true;
            }
        });


        return view;
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void updateTaskList(GetTaskListEvent getTaskListEvent) {
        if (getTaskListEvent.isSuccess())
            taskParentListAdapter.updateList();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        taskParentListAdapter.updateList();
    }

}
