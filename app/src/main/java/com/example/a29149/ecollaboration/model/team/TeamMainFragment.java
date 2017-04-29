package com.example.a29149.ecollaboration.model.team;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a29149.ecollaboration.R;
import com.example.a29149.ecollaboration.dto.ProjectDTO;
import com.example.a29149.ecollaboration.httprequest.asynctask.team.TransForGetMyJoinTeamInfo;
import com.example.a29149.ecollaboration.httprequest.asynctask.team.TransForTargetTeamsTask;
import com.example.a29149.ecollaboration.mainactivity.MainActivity;
import com.example.a29149.ecollaboration.model.team.activity.ApplyTeamActivity;
import com.example.a29149.ecollaboration.model.team.activity.CreateTeamActivity;
import com.example.a29149.ecollaboration.model.team.activity.TeamDetailActivity;
import com.example.a29149.ecollaboration.model.team.adapter.MyJoinTeamAdapter;
import com.example.a29149.ecollaboration.util.ActivityCode;
import com.example.a29149.ecollaboration.util.AnnotationUtil;
import com.example.a29149.ecollaboration.util.GlobalUtil;
import com.example.a29149.ecollaboration.util.annotation.OnClick;
import com.example.a29149.ecollaboration.util.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class TeamMainFragment extends Fragment{

    @ViewInject(R.id.team_list)
    private ListView mJoinedTeamList;
    private MyJoinTeamAdapter joinTeamAdapter;


    private int mPosition = -1;
    public TeamMainFragment() {
    }

    public static TeamMainFragment newInstance() {
        TeamMainFragment fragment = new TeamMainFragment();
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
        View view = inflater.inflate(R.layout.fragment_team_main, container, false);
        AnnotationUtil.injectViews(this, view);
        AnnotationUtil.setClickListener(this, view);

        joinTeamAdapter = new MyJoinTeamAdapter(getContext());
        mJoinedTeamList.setAdapter(joinTeamAdapter);
        mJoinedTeamList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPosition = position;
                /**
                 * TODO:此时要与后台传输数据
                 */
                MainActivity.shapeLoadingDialog.show();
                TransForGetMyJoinTeamInfo transForGetMyJoinTeamInfo = new TransForGetMyJoinTeamInfo();
                transForGetMyJoinTeamInfo.execute(GlobalUtil.getInstance().getTeamDTO().get(position).getId()+"");
            }
        });

        return view;
    }


    @OnClick({R.id.create_team_bt})
    public void createTeamListener(View view)
    {
        Intent createTeamIntent = new Intent(getActivity(), CreateTeamActivity.class);
        startActivityForResult(createTeamIntent, ActivityCode.TEAM_MAIN_TO_CREATE_TEAM);
    }

    @OnClick({R.id.join_team_bt})
    public void joinTeamListener(View v)
    {
        Intent applyTeamIntent = new Intent(getActivity(), ApplyTeamActivity.class);
        startActivity(applyTeamIntent);
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void getCreateTeam(CreateTeamEvent createTeamEvent)
    {
        if (createTeamEvent.isSuccess())
            joinTeamAdapter.notifyDataSetChanged();
    }


    @Subscribe(threadMode = ThreadMode.MainThread)
    public void getTeamInfo(GetTeamInfoEvent getTeamInfoEvent)
    {
        MainActivity.shapeLoadingDialog.dismiss();
        if (getTeamInfoEvent.isSuccess())
        {
            List<String> strings = new ArrayList<>();
            for (ProjectDTO projectDTO:GlobalUtil.getInstance().getMyExeProjectDTOs())
                strings.add(projectDTO.getName());

            TextView customTitle= new TextView(getContext());
            customTitle.setPadding(0, 20, 0, 0);
            customTitle.setText("请选择项目");
            customTitle.setTextColor(getResources().getColor(R.color.colorPrimary));
            customTitle.setTextSize(21);
            customTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            customTitle.setGravity(Gravity.CENTER);

            //主要用于获取团队下的项目
            ArrayAdapter<String> teamProjectItem=new ArrayAdapter<String>(getContext(),
                    R.layout.dialog_team_project_item, strings);
            AlertDialog alertDialog=new AlertDialog.Builder(getContext())
                    .setCustomTitle(customTitle)
                    .setAdapter(teamProjectItem, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                            TransForTargetTeamsTask transForTargetTeamsTask = new TransForTargetTeamsTask();
                            transForTargetTeamsTask.execute(
                                    GlobalUtil.getInstance().getMyExeProjectDTOs().get(which).getId()+"");

                            Intent intent = new Intent(getActivity(), TeamDetailActivity.class);
                            intent.putExtra("position", mPosition);
                            intent.putExtra("project",which);
                            startActivityForResult(intent, ActivityCode.TEAM_MAIN_TO_TEAM_DETAIL);

                        }
                    })
                    .create();

            alertDialog.show();
        }else
        {
            //以-1进去
            Toast.makeText(getContext(), "为执行该团队", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
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
        switch (resultCode)
        {
            case ActivityCode.EDIT:
                joinTeamAdapter.notifyDataSetChanged();
                break;
            case ActivityCode.NO_CHANGE:
                break;
        }
    }
}
