package com.example.a29149.ecollaboration.model.team.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a29149.ecollaboration.R;
import com.example.a29149.ecollaboration.dto.TeamDTO;
import com.example.a29149.ecollaboration.httprequest.asynctask.team.TransForCreateTeam;
import com.example.a29149.ecollaboration.util.ActivityCode;
import com.example.a29149.ecollaboration.util.AnnotationUtil;
import com.example.a29149.ecollaboration.util.annotation.OnClick;
import com.example.a29149.ecollaboration.util.annotation.ViewInject;


/**
 * Created by Administrator on 2016/11/28 0028.
 */

public class CreateTeamActivity extends AppCompatActivity {

    @ViewInject(R.id.team_name)
    private EditText teamName;

    @ViewInject(R.id.team_type)
    private EditText teamType;

    @ViewInject(R.id.team_number)
    private EditText teamNumber;

    public Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team);
        AnnotationUtil.injectViews(this);
        AnnotationUtil.setClickListener(this);

        intent = getIntent();
    }

    @OnClick({R.id.create_team, R.id.commit})
    public void createTeamSaveListener(View view) {
        if (teamName.getText().toString().equals("")
                || teamType.getText().toString().equals("")
                || teamNumber.getText().toString().equals("")) {
            Toast.makeText(this, "输入项不能为空", Toast.LENGTH_SHORT).show();
        } else {
            setResult(ActivityCode.EDIT, intent);
            TeamDTO teamDTO = new TeamDTO();
            teamDTO.setTeamName(teamName.getText().toString());
            teamDTO.setDescription(teamType.getText().toString());
            teamDTO.setMemberMax(Integer.parseInt(teamNumber.getText().toString()));

            //TODO:网络传输
            TransForCreateTeam createTeam = new TransForCreateTeam(this);
            createTeam.execute(teamDTO.getTeamName(),
                    teamDTO.getDescription(), teamDTO.getMemberMax() + "");

        }
    }

    @OnClick(R.id.bt_return)
    public void cancelListener(View view) {
        setResult(ActivityCode.NO_CHANGE, intent);
        this.finish();
    }
}
