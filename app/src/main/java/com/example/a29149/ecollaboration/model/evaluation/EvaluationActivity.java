package com.example.a29149.ecollaboration.model.evaluation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a29149.ecollaboration.R;
import com.example.a29149.ecollaboration.util.AnnotationUtil;
import com.example.a29149.ecollaboration.util.GlobalUtil;
import com.example.a29149.ecollaboration.util.annotation.OnClick;
import com.example.a29149.ecollaboration.util.annotation.ViewInject;
import com.example.a29149.ecollaboration.util.log;

/**
 * Created by Administrator on 2016/11/28 0028.
 */

public class EvaluationActivity extends AppCompatActivity {

    //@ViewInject(R.id.task_h_list)
    //private LinearLayout linearLayout;

    @ViewInject(R.id.evaluation_list)
    private LinearLayout mEvaluationNames;

    private Intent intent;
    private int mPosition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_evaluation_layout);
        AnnotationUtil.injectViews(this);
        AnnotationUtil.setClickListener(this);

        intent = getIntent();
        mPosition = intent.getIntExtra("position", -1);

        log.d(this, mPosition);
        //initTaskNode();
        initEvaluationList();
    }

//    private void initTaskNode() {
//        for (int position = 0; position < GlobalUtil.getInstance().getTaskDTOs().get(0).size(); position++) {
//            final int j = position;
//            View convertView = View.inflate(this, R.layout.list_task_detail, null);
//
//            SubTaskView subTaskView = (SubTaskView) convertView.findViewById(R.id.task_container);
//            TextView time = (TextView) convertView.findViewById(R.id.time);
//            TextView content = (TextView) convertView.findViewById(R.id.content);
//
//            time.setText(GlobalUtil.getInstance().getTaskDTOs().get(0).get(position).getTime());
//            content.setText(GlobalUtil.getInstance().getTaskDTOs().get(0).get(position).getContent());
//
//            if (position == 0) {
//                subTaskView.firstNode();
//            }
//            if (position == GlobalUtil.getInstance().getTaskDTOs().get(0).size() - 1) {
//                subTaskView.lastNode();
//            }
//
//            subTaskView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(EvaluationActivity.this, j + " ", Toast.LENGTH_SHORT).show();
//                }
//            });
//
//            linearLayout.addView(convertView);
//        }
//    }

    private void initEvaluationList() {


        //i用来判断一个team的人数

        for (int i = 0; i < GlobalUtil.getInstance().getStudentsBeans().size(); i++) {
            TextView customTitle = new TextView(this);
            customTitle.setPadding(0, 20, 0, 0);
            customTitle.setText(GlobalUtil.getInstance().getStudentsBeans().get(i).getName());
            customTitle.setTextColor(this.getResources().getColor(R.color.colorPrimary));
            customTitle.setTextSize(21);
            customTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            customTitle.setGravity(Gravity.CENTER);

            mEvaluationNames.addView(customTitle);
            for (int j = 0; j < GlobalUtil.getInstance().getProjectAccessTypeDTOs().size(); j++)
            {
                View root = LayoutInflater
                        .from(this)
                        .inflate(R.layout.list_evaluation_real_item, null, false);
                TextView evaluationName = (TextView) root.findViewById(R.id.evaluate_name);
                evaluationName.setText(GlobalUtil.getInstance().getProjectAccessTypeDTOs().get(j).getType());

                mEvaluationNames.addView(root);
            }
        }
    }

    private String[][] getScore(LinearLayout root, int teamMembers) {
        String[][] score = new String[teamMembers][];
        int index = -1;
        int childPos = -1;

        for (int i = 0; i < root.getChildCount(); i++) {
            final View childView = root.getChildAt(i);
            if (childView instanceof EditText) {
                EditText input = (EditText) childView;
                score[index][childPos++] = (input.getText().toString());
            } else {
                index++;
                childPos = 0;
            }
        }
        return score;
    }

    @OnClick(R.id.evaluation_evaluation_bt)
    public void setEvaluationBtListener(View view) {
        log.d(this, getScore(mEvaluationNames, GlobalUtil.getInstance().getStudentsBeans().size()).toString());
    }
}
