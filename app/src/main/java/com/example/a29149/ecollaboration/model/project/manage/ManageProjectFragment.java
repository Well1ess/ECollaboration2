package com.example.a29149.ecollaboration.model.project.manage;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.a29149.ecollaboration.R;
import com.example.a29149.ecollaboration.model.project.manage.activity.ProjectDetailActivity;
import com.example.a29149.ecollaboration.model.project.manage.adapter.ProjectListAdapter;
import com.example.a29149.ecollaboration.util.ActivityCode;
import com.example.a29149.ecollaboration.util.AnnotationUtil;
import com.example.a29149.ecollaboration.util.GlobalUtil;
import com.example.a29149.ecollaboration.util.annotation.ViewInject;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class ManageProjectFragment extends Fragment {

    @ViewInject(R.id.project_list)
    private ListView projectList;
    private ProjectListAdapter projectListAdapter;

    public ManageProjectFragment() {

    }

    public static ManageProjectFragment newInstance() {
        ManageProjectFragment fragment = new ManageProjectFragment();

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
        View view = inflater.inflate(R.layout.fragment_manage_project, container, false);
        AnnotationUtil.injectViews(this, view);
        AnnotationUtil.setClickListener(this, view);

        projectListAdapter = new ProjectListAdapter(getContext());
        projectList.setAdapter(projectListAdapter);
        projectList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(getActivity(), ProjectDetailActivity.class);

                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                bundle.putString("ProjectID", GlobalUtil.getInstance().getProjectDTOs().get(position).getId() + "");
                bundle.putString("ProjectTitle", GlobalUtil.getInstance().getProjectDTOs().get(position).getName());
                bundle.putString("ProjectTime", GlobalUtil.getInstance().getProjectDTOs().get(position).getCreateDate());
                bundle.putString("ProjectContent", GlobalUtil.getInstance().getProjectDTOs().get(position).getInfo());
                bundle.putString("NeedSkill", GlobalUtil.getInstance().getProjectDTOs().get(position).getRequirement());
                bundle.putString("LearnSkill", GlobalUtil.getInstance().getProjectDTOs().get(position).getGain());

                i.putExtras(bundle);

                startActivityForResult(i,
                        ActivityCode.MANAGE_PROJECT_TO_PROJECT_DETAIL,
                        ActivityOptions.makeSceneTransitionAnimation(getActivity(), view, "sharedView").toBundle());
            }
        });

        return view;
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void updateAdapter(UpdateProjectListEvent updateProjectListEvent) {
        if (updateProjectListEvent.isSuccess())
            projectListAdapter.updateList();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case ActivityCode.EDIT:
                projectListAdapter.updateList();
                break;
            case ActivityCode.NO_CHANGE:
                break;
        }
    }
}
