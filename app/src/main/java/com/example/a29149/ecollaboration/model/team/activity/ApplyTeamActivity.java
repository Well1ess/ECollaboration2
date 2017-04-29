package com.example.a29149.ecollaboration.model.team.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a29149.ecollaboration.R;
import com.example.a29149.ecollaboration.dto.TeamDTO;
import com.example.a29149.ecollaboration.httprequest.asynctask.team.TransForSearchTeam;
import com.example.a29149.ecollaboration.mainactivity.MainActivity;
import com.example.a29149.ecollaboration.model.team.adapter.TeamSearchListAdapter;
import com.example.a29149.ecollaboration.util.AnnotationUtil;
import com.example.a29149.ecollaboration.util.annotation.OnClick;
import com.example.a29149.ecollaboration.util.annotation.ViewInject;
import com.example.a29149.ecollaboration.widget.MyEditText;
import com.example.a29149.ecollaboration.widget.dialog.DisplayTeamInfoDialog;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class ApplyTeamActivity extends AppCompatActivity {

    @ViewInject(R.id.key_value)
    private MyEditText keyValue;

    @ViewInject(R.id.project_search)
    private ListView searchResult;

    public TeamSearchListAdapter searchListAdapter;
    public List<TeamDTO> teamDTOArrayList=new ArrayList<>();

    public DisplayTeamInfoDialog.Builder teamInfoDialog;

    public ApplyTeamActivity() {
    }

    public static ApplyTeamActivity newInstance() {
        ApplyTeamActivity fragment = new ApplyTeamActivity();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_team);
        AnnotationUtil.injectViews(this);
        AnnotationUtil.setClickListener(this);
        EventBus.getDefault().register(this);

        teamInfoDialog = new DisplayTeamInfoDialog.Builder(this);
        teamInfoDialog.create();

        searchListAdapter = new TeamSearchListAdapter(this, teamDTOArrayList);
        searchResult.setAdapter(searchListAdapter);
        searchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                teamInfoDialog.setTeamName(teamDTOArrayList.get(position).getTeamName());
                teamInfoDialog.setTeamInfo(teamDTOArrayList.get(position).getDescription());
                teamInfoDialog.setTeamNum(teamDTOArrayList.get(position).getMemberMax()+"");

                teamInfoDialog.getDialog().show();
            }
        });
    }


    @OnClick(R.id.project_search_bt)
    public void searchBtListener(View view)
    {
        if(keyValue.getText().toString().isEmpty())
        {
            Toast.makeText(ApplyTeamActivity.this, "关键字不能为空！", Toast.LENGTH_SHORT).show();
        }
        else
        {
            MainActivity.shapeLoadingDialog.show();

            //TODO: get network
            TransForSearchTeam searchTeam = new TransForSearchTeam();
            searchTeam.execute(keyValue.getText().toString());

            searchListAdapter.update(teamDTOArrayList);

        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEventMainThread(SearchTeamResultListEvent searchTeamResultListEvent)
    {
        teamDTOArrayList = searchTeamResultListEvent.getTeamDTOs();
        searchListAdapter.update(teamDTOArrayList);
    }

    @OnClick(R.id.bt_return)
    public void btReturnListener(View view)
    {
        ApplyTeamActivity.this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
