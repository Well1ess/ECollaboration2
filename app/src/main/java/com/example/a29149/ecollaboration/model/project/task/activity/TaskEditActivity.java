package com.example.a29149.ecollaboration.model.project.task.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a29149.ecollaboration.R;
import com.example.a29149.ecollaboration.dto.TaskDTO;
import com.example.a29149.ecollaboration.httprequest.asynctask.task.TransForCreateTask;
import com.example.a29149.ecollaboration.model.project.task.adapter.TaskEditContainerAdapter;
import com.example.a29149.ecollaboration.util.AnnotationUtil;
import com.example.a29149.ecollaboration.util.GlobalUtil;
import com.example.a29149.ecollaboration.util.TimeSort;
import com.example.a29149.ecollaboration.util.annotation.OnClick;
import com.example.a29149.ecollaboration.util.annotation.ViewInject;
import com.example.a29149.ecollaboration.widget.dialog.InsertNewTaskDialog;

import java.util.ArrayList;

public class TaskEditActivity extends AppCompatActivity {

    private int position;
    private int insertPosition;

    @ViewInject(R.id.task_container)
    private ListView taskEditContainer;

    Integer whichProjectId;

    private TaskEditContainerAdapter taskEditContainerAdapter;

    private InsertNewTaskDialog.Builder insertDialog = new InsertNewTaskDialog.Builder(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_edit);
        AnnotationUtil.injectViews(this);
        AnnotationUtil.setClickListener(this);

        Intent intent = getIntent();
        position = intent.getIntExtra("task_index", -1);

        taskEditContainerAdapter = new TaskEditContainerAdapter(this, position);
        taskEditContainer.setAdapter(taskEditContainerAdapter);

        initDialog();
    }

    @OnClick(R.id.target_project)
    public void setTargetProject(View view) {
        final TextView projectName = (TextView) view;
        TextView customTitle = new TextView(this);
        customTitle.setPadding(0, 20, 0, 0);
        customTitle.setText("请选择项目");
        customTitle.setTextColor(this.getResources().getColor(R.color.colorPrimary));
        customTitle.setTextSize(21);
        customTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        customTitle.setGravity(Gravity.CENTER);

        ArrayList<String> strings = new ArrayList<>();

        for (int i = 0; i < GlobalUtil.getInstance().getProjectDTOs().size(); i++) {
            strings.add(GlobalUtil.getInstance().getProjectDTOs().get(i).getName());
        }

        ArrayAdapter<String> teamProjectItem = new ArrayAdapter<String>(this,
                R.layout.dialog_team_project_item,
                strings);

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setCustomTitle(customTitle)
                .setAdapter(teamProjectItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        whichProjectId = GlobalUtil.getInstance().getProjectDTOs().get(which).getId();
                        projectName.setText(GlobalUtil.getInstance().getProjectDTOs().get(which).getName());
                        dialog.dismiss();
                    }
                }).create();
        alertDialog.show();
    }

    @OnClick(R.id.bt_return)
    public void setCancelListener(View view) {
        TaskEditActivity.this.finish();
    }

    @OnClick(R.id.commit)
    public void setCommitListener(View view) {

        TransForCreateTask createTask = new TransForCreateTask(this);
        createTask.execute(whichProjectId + "", taskEditContainerAdapter.getList().get(0).getContent(),
                taskEditContainerAdapter.getList().get(0).getBeginDate(),
                taskEditContainerAdapter.getList().get(0).getTargetDate());

        //GlobalUtil.getInstance().getMyTask().addAll(taskEditContainerAdapter.getList());
    }

    @OnClick(R.id.insert_task_button)
    public void insertTaskListener(View view) {
        if (taskEditContainerAdapter.getList().size() < 1)
            insertDialog.create().show();
    }

    private void initDialog() {

        insertDialog.setNegativeButton("取      消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        insertDialog.setPositiveButton("保      存", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (taskEditContainerAdapter.getList().size() < 1) {
                    if (insertDialog.getStartTime().equals("")) {
                        Toast.makeText(TaskEditActivity.this, "开始时间不能为空", Toast.LENGTH_SHORT).show();
                    } else {
                        if (insertDialog.getEndTime().equals("")) {
                            Toast.makeText(TaskEditActivity.this, "截至时间不能为空", Toast.LENGTH_SHORT).show();
                        } else {
                            if (insertDialog.getContent().equals("")) {
                                Toast.makeText(TaskEditActivity.this, "内容不能为空", Toast.LENGTH_SHORT).show();
                            } else {
                                if (!TimeSort.TimeA_XiaoYv_TimeB(insertDialog.getStartTime(),
                                        insertDialog.getEndTime())) {
                                    Toast.makeText(TaskEditActivity.this, "开始时间不能小于截至时间", Toast.LENGTH_SHORT).show();
                                } else {
                                    TaskDTO taskDTO = new TaskDTO();
                                    taskDTO.setBeginDate(insertDialog.getStartTime().toString());
                                    taskDTO.setTargetDate(insertDialog.getEndTime().toString());
                                    taskDTO.setContent(insertDialog.getContent().toString());

                                    insertPosition = TimeSort.IndexOfTime(taskEditContainerAdapter.getList(),
                                            insertDialog.getStartTime().toString());
                                    Log.d("MyTimePos", insertPosition + " ");

                                    if (insertPosition == taskEditContainerAdapter.getList().size()) {
                                        taskEditContainerAdapter.getList().add(taskDTO);
                                        taskEditContainerAdapter.updateList();
                                        dialog.dismiss();
                                    } else {
                                        if (TimeSort.TimeA_XiaoYv_TimeB(taskDTO.getTargetDate(),
                                                taskEditContainerAdapter.getList().get(insertPosition).getBeginDate())) {
                                            taskEditContainerAdapter.getList().add(insertPosition, taskDTO);
                                            taskEditContainerAdapter.updateList();
                                            dialog.dismiss();
                                        } else {
                                            Toast.makeText(TaskEditActivity.this,
                                                    "计划之间不能有重叠！",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
    }
}
