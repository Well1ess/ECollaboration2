package com.example.a29149.ecollaboration.model.team.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.a29149.ecollaboration.R;
import com.example.a29149.ecollaboration.dto.TeamDTO;
import com.example.a29149.ecollaboration.httprequest.asynctask.team.TransForEditTeam;
import com.example.a29149.ecollaboration.util.AnnotationUtil;
import com.example.a29149.ecollaboration.util.annotation.OnClick;
import com.example.a29149.ecollaboration.util.annotation.ViewInject;
import com.example.a29149.ecollaboration.util.GlobalUtil;

public class EditTeamActivity extends AppCompatActivity {

    @ViewInject(R.id.title)
    private TextView mTitle;

    @ViewInject(R.id.team_name)
    private EditText mTeamName;

    @ViewInject(R.id.team_type)
    private EditText mTeamType;

    @ViewInject(R.id.team_number)
    private EditText mTeamNumber;

    private int mPosition;
    private TeamDTO mTeamDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_team);
        AnnotationUtil.injectViews(this);
        AnnotationUtil.setClickListener(this);

        mPosition = getIntent().getIntExtra("position", -1);
        if (mPosition == -1)
            throw new IllegalArgumentException("position error");
        mTeamDTO = GlobalUtil.getInstance().getTeamDTO().get(mPosition);
        initView();
    }

    private void initView() {
        mTitle.setText(mTeamDTO.getTeamName());
        mTeamName.setHint(mTeamDTO.getTeamName());
        mTeamType.setHint(mTeamDTO.getDescription());
        mTeamNumber.setHint(mTeamDTO.getMemberMax().toString());
    }

    @OnClick({R.id.create_team, R.id.commit})
    public void editSaveListener(View view) {
        if (!mTeamName.getText().toString().equals("") && !mTeamName.getText().toString().equals(mTeamDTO.getTeamName())) {
            mTeamDTO.setTeamName(mTeamName.getText().toString());
        }
        if (!mTeamType.getText().toString().equals("") && !mTeamType.getText().toString().equals(mTeamDTO.getDescription())) {
            mTeamDTO.setDescription(mTeamType.getText().toString());
        }
        if (!mTeamNumber.getText().toString().equals("") && !mTeamNumber.getText().toString().equals(mTeamDTO.getMemberMax())) {
            mTeamDTO.setMemberMax(Integer.parseInt(mTeamNumber.getText().toString()));
        }

        TransForEditTeam editTeam = new TransForEditTeam(this);
        editTeam.execute(mTeamDTO.getId() + "", mTeamDTO.getTeamName(),
                mTeamDTO.getDescription(), mTeamDTO.getMemberMax() + "", mPosition + "");
    }

    @OnClick(R.id.bt_return)
    public void cancelListener(View view) {
        this.finish();
    }
}
