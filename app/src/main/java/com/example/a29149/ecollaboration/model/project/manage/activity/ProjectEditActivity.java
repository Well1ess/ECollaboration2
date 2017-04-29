package com.example.a29149.ecollaboration.model.project.manage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.a29149.ecollaboration.R;
import com.example.a29149.ecollaboration.dto.ProjectDTO;
import com.example.a29149.ecollaboration.httprequest.asynctask.project.TransForEditSchoolProject;
import com.example.a29149.ecollaboration.util.ActivityCode;
import com.example.a29149.ecollaboration.util.AnnotationUtil;
import com.example.a29149.ecollaboration.util.annotation.OnClick;
import com.example.a29149.ecollaboration.util.annotation.ViewInject;
import com.example.a29149.ecollaboration.util.GlobalUtil;


//TODO:网络数据更新
public class ProjectEditActivity extends AppCompatActivity {

    @ViewInject(R.id.title)
    private TextView title;

    @ViewInject(R.id.project_name_edit)
    private TextView projectName;

    @ViewInject(R.id.project_need_edit)
    private TextView projectRequirement;

    @ViewInject(R.id.project_content_edit)
    private TextView projectInfo;

    @ViewInject(R.id.project_learn_skill_edit)
    private TextView projectGain;

    @ViewInject(R.id.project_applyBeforeDate_edit)
    private TextView beforeDate;

    @ViewInject(R.id.project_finishDate_edit)
    private TextView finishDate;

    @ViewInject(R.id.project_survivalDate_edit)
    private TextView survivalDate;

    @ViewInject(R.id.project_teamMax_edit)
    private TextView teamMax;

    @ViewInject(R.id.project_memberMax_edit)
    private TextView memberMax;

    @ViewInject(R.id.project_keyWord_edit)
    private TextView keyWord;


    private Intent intent;
    private int mPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_edit);
        AnnotationUtil.injectViews(this);
        AnnotationUtil.setClickListener(this);

        intent = getIntent();
        mPosition = intent.getIntExtra("position", -1);

        initView();
    }

    public void initView()
    {
        if (mPosition != -1)
        {
            ProjectDTO projectDTO = GlobalUtil.getInstance().getProjectDTOs().get(mPosition);


            title.setText(projectDTO.getName());
            projectName.setHint(projectDTO.getName());
            projectRequirement.setHint(projectDTO.getRequirement());
            projectGain.setHint(projectDTO.getGain());
            projectInfo.setHint(projectDTO.getInfo());
        }

    }

    @OnClick({R.id.commit, R.id.insert_project_button})
    public void changeProjectListener(View view)
    {
        ProjectDTO projectDTO = GlobalUtil.getInstance().getProjectDTOs().get(mPosition);

        Log.d("China", projectName.getText()+" ");
        if (!projectName.getText().equals(projectDTO.getName())
                && !projectName.getText().toString().equals(""))
        {
            projectDTO.setName(projectName.getText().toString());
        }

        if (!projectRequirement.getText().equals(projectDTO.getRequirement())
                && !projectRequirement.getText().toString().equals(""))
        {
            projectDTO.setRequirement(projectRequirement.getText().toString());
        }

        if (!projectGain.getText().equals(projectDTO.getGain())
                && !projectGain.getText().toString().equals(""))
        {
            projectDTO.setGain(projectGain.getText().toString());
        }

        if (!projectInfo.getText().equals(projectDTO.getInfo())
                && !projectInfo.getText().toString().equals(""))
        {
            projectDTO.setInfo(projectInfo.getText().toString());
        }

        setResult(ActivityCode.EDIT, intent);
        //TODO:网络传输
        TransForEditSchoolProject editSchoolProject = new TransForEditSchoolProject(this);
        editSchoolProject.execute(projectDTO.getId()+"",
                projectDTO.getName(),
                projectDTO.getApplyBeforeDate(),
                projectDTO.getFinishDate(),
                projectDTO.getSurvivalDate(),
                projectDTO.getTeamMax()+"",
                projectDTO.getMemberMax()+"",
                projectDTO.getKeyWord(),
                projectDTO.getInfo(),
                projectDTO.getRequirement(),
                projectDTO.getGain(),
                projectDTO.getPriority()+"");
    }

    @OnClick(R.id.bt_return)
    public void cancelListener(View view)
    {
        setResult(ActivityCode.NO_CHANGE, intent);
        this.finish();
    }
}
