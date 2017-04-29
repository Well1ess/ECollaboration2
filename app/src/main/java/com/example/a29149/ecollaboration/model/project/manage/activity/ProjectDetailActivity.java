package com.example.a29149.ecollaboration.model.project.manage.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.a29149.ecollaboration.R;
import com.example.a29149.ecollaboration.dto.ProjectDTO;
import com.example.a29149.ecollaboration.model.project.manage.adapter.ProjectTeamListAdapter;
import com.example.a29149.ecollaboration.util.ActivityCode;
import com.example.a29149.ecollaboration.util.AnnotationUtil;
import com.example.a29149.ecollaboration.util.annotation.OnClick;
import com.example.a29149.ecollaboration.util.annotation.ViewInject;
import com.example.a29149.ecollaboration.util.GlobalUtil;
import com.example.a29149.ecollaboration.widget.dialog.WarningDisplayDialog;


//TODO:删除时，数据更新
public class ProjectDetailActivity extends AppCompatActivity {

    @ViewInject(R.id.mytitle)
    private TextView title;

    @ViewInject(R.id.project_time)
    private TextView projectTime;

    @ViewInject(R.id.project_need_skill)
    private TextView projectRequirement;

    @ViewInject(R.id.project_learn_skill)
    private TextView projectGain;

    @ViewInject(R.id.project_content_msg)
    private TextView projectInfo;

    @ViewInject(R.id.project_team_list)
    private GridView projectTeamList;
    private ProjectTeamListAdapter adapter;

    @ViewInject(R.id.project_team_name)
    private TextView projectTeamName;

    @ViewInject(R.id.project_team_captain)
    private TextView projectTeamCaptain;

    @ViewInject(R.id.project_team_numbers)
    private TextView projectTeamNumbers;

    @ViewInject(R.id.project_team_numbers_info)
    private TextView projectTeamNumbersInfo;

    private WarningDisplayDialog.Builder mWarningDialog;
    private int position;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_info);
        AnnotationUtil.setClickListener(this);
        AnnotationUtil.injectViews(this);

        initData();
        initWarningDialog();

        //result初始化，默认为未修改
        setResult(ActivityCode.NO_CHANGE, intent);

        adapter = new ProjectTeamListAdapter(this);
        projectTeamList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                projectTeamName.setText(GlobalUtil.
                        getInstance().getTeamDTO().get(position).getTeamName());
                projectTeamCaptain.setText(GlobalUtil.
                        getInstance().getTeamDTO().get(position).getCreatorId().toString());
                projectTeamNumbersInfo.setText(GlobalUtil.
                        getInstance().getTeamDTO().get(position).getDescription());
            }
        });

        projectTeamList.setAdapter(adapter);
        fixListViewHeight(projectTeamList);

    }

    private void initData()
    {
        intent = getIntent();
        Bundle data = intent.getExtras();

        position = data.getInt("position");
        title.setText(data.getString("ProjectTitle"));
        projectTime.setText(data.getString("ProjectTime"));
        projectRequirement.setText(data.getString("NeedSkill"));
        projectGain.setText(data.getString("LearnSkill"));
        projectInfo.setText(data.getString("ProjectContent"));
    }

    private void initWarningDialog()
    {
        mWarningDialog=new WarningDisplayDialog.Builder(this);
        mWarningDialog.setPositiveButton("删      除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setResult(ActivityCode.EDIT, intent);
                GlobalUtil.getInstance().getProjectDTOs().remove(position);
                dialog.dismiss();
                ProjectDetailActivity.this.finish();
            }
        });

        mWarningDialog.setNegativeButton("取      消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    public void fixListViewHeight(GridView listView) {
        // 如果没有设置数据适配器，则ListView没有子项，返回。  
        ListAdapter listAdapter = listView.getAdapter();
        int totalHeight = 0;
        if (listAdapter == null) {
            return;
        }
        for (int index = 0, len = listAdapter.getCount(); index < len; index++) {
            View listViewItem = listAdapter.getView(index, null, listView);
            // 计算子项View 的宽高   
            listViewItem.measure(0, 0);
            // 计算所有子项的高度和
            totalHeight += listViewItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // listView.getDividerHeight()获取子项间分隔符的高度   
        // params.height设置ListView完全显示需要的高度    

        params.height = totalHeight+ (listView.getVerticalSpacing() * (listAdapter.getCount() + 4));
        listView.setLayoutParams(params);
    }

    @OnClick({R.id.main_menu_edit, R.id.main_menu_delete})
    public void teamListListener(View view)
    {
        switch (view.getId())
        {
            case R.id.main_menu_edit:
                Intent intent = new Intent(ProjectDetailActivity.this, ProjectEditActivity.class);
                intent.putExtra("position", position);

                startActivityForResult(intent, ActivityCode.PROJECT_DETAIL_TO_PROJECT_EDIT);

                setResult(ActivityCode.EDIT, this.intent);
                break;
            case R.id.main_menu_delete:
                mWarningDialog.create().show();
                mWarningDialog.setMsg("您确定要删除此项目吗？");
                break;
        }
    }

    @OnClick(R.id.bt_return)
    public void btReturnListener(View view)
    {
        this.onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode)
        {
            case ActivityCode.EDIT:
                ProjectDTO projectDTO = GlobalUtil.getInstance().getProjectDTOs().get(position);

                title.setText(projectDTO.getName());
                projectTime.setText(projectDTO.getCreateDate());
                projectRequirement.setText(projectDTO.getRequirement());
                projectGain.setText(projectDTO.getGain());
                projectInfo.setText(projectDTO.getInfo());

                break;
            case ActivityCode.NO_CHANGE:
                break;
        }
    }

}
