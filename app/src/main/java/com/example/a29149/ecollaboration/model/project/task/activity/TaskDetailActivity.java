package com.example.a29149.ecollaboration.model.project.task.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.example.a29149.ecollaboration.R;
import com.example.a29149.ecollaboration.model.project.task.adapter.TaskContainerAdapter;
import com.example.a29149.ecollaboration.util.AnnotationUtil;
import com.example.a29149.ecollaboration.util.log;
import com.example.a29149.ecollaboration.util.annotation.OnClick;
import com.example.a29149.ecollaboration.util.annotation.ViewInject;
import com.example.a29149.ecollaboration.util.GlobalUtil;
import com.example.a29149.ecollaboration.widget.dialog.WarningDisplayDialog;

/**
 * Created by 29149 on 2017/3/20.
 */

public class TaskDetailActivity extends AppCompatActivity {

    @ViewInject(R.id.task_container)
    private ListView taskContainer;

    private TaskContainerAdapter taskContainerAdapter;
    private WarningDisplayDialog.Builder mWarningDialog;

    private int position;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        AnnotationUtil.injectViews(this);
        AnnotationUtil.setClickListener(this);

        Intent intent=getIntent();
        position=intent.getIntExtra("task_index",-1);
        taskContainerAdapter=new TaskContainerAdapter(this, position);

        taskContainer.setAdapter(taskContainerAdapter);
        initWarningDialog();
    }

    @OnClick({R.id.main_menu_team, R.id.main_menu_edit, R.id.main_menu_delete})
    public void teamList(View view)
    {
        log.d(TaskDetailActivity.this, ""+view.getId());
        switch (view.getId())
        {
            case R.id.main_menu_team:
                break;
            case R.id.main_menu_edit:
                Intent intent=new Intent(TaskDetailActivity.this, TaskEditActivity.class);
                intent.putExtra("task_index", position);
                startActivity(intent);
                break;
            case R.id.main_menu_delete:
                mWarningDialog.create().show();
                mWarningDialog.setMsg("您确定要删除此任务吗？");
                break;
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        taskContainerAdapter.updateList();
    }

    private void initWarningDialog()
    {
        mWarningDialog=new WarningDisplayDialog.Builder(this);
        mWarningDialog.setPositiveButton("删      除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                GlobalUtil.getInstance().getMyTask().remove(position);
                dialog.dismiss();
                TaskDetailActivity.this.finish();
            }
        });

        mWarningDialog.setNegativeButton("取      消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

}
