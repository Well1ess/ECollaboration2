package com.example.a29149.ecollaboration.model.evaluation;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a29149.ecollaboration.R;
import com.example.a29149.ecollaboration.httprequest.asynctask.evaluation.TransForGetAccess;
import com.example.a29149.ecollaboration.httprequest.asynctask.evaluation.TransForGetTeamByProject;
import com.example.a29149.ecollaboration.model.evaluation.adapter.ImageAdapter;
import com.example.a29149.ecollaboration.util.AnnotationUtil;
import com.example.a29149.ecollaboration.util.GlobalUtil;
import com.example.a29149.ecollaboration.util.annotation.ViewInject;
import com.example.a29149.ecollaboration.util.log;
import com.example.a29149.ecollaboration.widget.GalleryView;
import com.example.a29149.ecollaboration.widget.toast.CustomToast;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class EvaluationMainFragment extends Fragment {


    @ViewInject(R.id.gallery_Title)
    private TextView teamTitle;

    @ViewInject(R.id.mygallery)
    private GalleryView teamGallery;

    private ImageAdapter adapter;

    private int projectId;

    public EvaluationMainFragment() {

    }

    public static EvaluationMainFragment newInstance(String param1, String param2) {
        EvaluationMainFragment fragment = new EvaluationMainFragment();
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
        View view = inflater.inflate(R.layout.fragment_evaluation_main, container, false);
        AnnotationUtil.injectViews(this, view);
        AnnotationUtil.setClickListener(this, view);
        initRes();
        return view;
    }

    private void initRes() {

        adapter = new ImageAdapter(getContext());
        teamGallery.setAdapter(adapter);


        teamGallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                teamTitle.setText(GlobalUtil.getInstance().getProjectDTOs().get(position).getName());
                projectId = GlobalUtil.getInstance().getProjectDTOs().get(position).getId();
                if (position != 0)
                    GlobalUtil.getInstance().setIndexChild(1);
                else
                    GlobalUtil.getInstance().setIndexChild(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        teamGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //TODO:网络传输
                TransForGetTeamByProject getTeamByProject = new TransForGetTeamByProject();
                projectId = GlobalUtil.getInstance().getProjectDTOs().get(position).getId();
                getTeamByProject.execute(projectId + "");
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void getManagerTeam(GetTeachersMTeamEvent getTeachersMTeamEvent) {
        if (getTeachersMTeamEvent.isSuccess()) {
            if (GlobalUtil.getInstance().getmManagerTeams().size() == 0) {
                new CustomToast(getContext()).setContent("该项目没有团队执行").setTime(Toast.LENGTH_SHORT).show();
            } else {
                TextView customTitle = new TextView(getContext());
                customTitle.setPadding(0, 20, 0, 0);
                customTitle.setText("请选择团队");
                customTitle.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));
                customTitle.setTextSize(21);
                customTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                customTitle.setGravity(Gravity.CENTER);

                ArrayList<String> strings = new ArrayList<>();

                for (int i = 0; i < GlobalUtil.getInstance().getmManagerTeams().size(); i++) {
                    strings.add(GlobalUtil.getInstance().getmManagerTeams().get(i).getTeamName());
                }

                ArrayAdapter<String> teamProjectItem = new ArrayAdapter<String>(getContext(),
                        R.layout.dialog_team_project_item,
                        strings);

                AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                        .setCustomTitle(customTitle)
                        .setAdapter(teamProjectItem, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                log.d(EvaluationMainFragment.this, GlobalUtil.getInstance().getmManagerTeams().get(which).getId());
                                log.d(EvaluationMainFragment.this, projectId);

                                //TODO:网络传输
                                TransForGetAccess getAccess = new TransForGetAccess(getActivity());
                                getAccess.execute(projectId+"",
                                        GlobalUtil.getInstance().getmManagerTeams().get(which).getId()+"");

                            }
                        }).create();
                alertDialog.show();
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }
}
