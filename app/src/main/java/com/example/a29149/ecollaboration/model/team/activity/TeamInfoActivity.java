package com.example.a29149.ecollaboration.model.team.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a29149.ecollaboration.R;
import com.example.a29149.ecollaboration.util.AnnotationUtil;
import com.example.a29149.ecollaboration.util.GlobalUtil;
import com.example.a29149.ecollaboration.util.annotation.ViewInject;
import com.example.a29149.ecollaboration.widget.subtaskview.SubTaskView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 张丽华 on 2016/11/28 0028.
 */

public class TeamInfoActivity extends AppCompatActivity {

    @ViewInject(R.id.task_h_list)
    private LinearLayout linearLayout;

    @ViewInject(R.id.evaluation_captain)
    private TextView captionName;

    @ViewInject(R.id.evaluation_myproject)
    private TextView projectName;

    @ViewInject(R.id.attribute_myproject)
    private TextView projectAttribute;

    @ViewInject(R.id.evaluation_members)
    private TextView members;

    @ViewInject(R.id.title)
    private TextView title;

    private Intent intent;
    private int mPosition;
    private int mProject;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_info);
        AnnotationUtil.injectViews(this);
        AnnotationUtil.setClickListener(this);

        intent = getIntent();
        mPosition = intent.getIntExtra("position", -1);
        mProject = intent.getIntExtra("project", -1);

        for (int position = 0; position < GlobalUtil.getInstance().getMyExeTask().size(); position++) {
            final int j = position;
            View convertView = View.inflate(this, R.layout.list_task_detail, null);

            SubTaskView subTaskView = (SubTaskView) convertView.findViewById(R.id.task_container);
            TextView time = (TextView) convertView.findViewById(R.id.time);
            TextView content = (TextView) convertView.findViewById(R.id.content);

            time.setText(GlobalUtil.getInstance().getMyExeTask().get(position).getTime());
            content.setText(GlobalUtil.getInstance().getMyExeTask().get(position).getContent());

            if (position == 0) {
                subTaskView.firstNode();
            }
            if (position == GlobalUtil.getInstance().getMyExeTask().size() - 1) {
                subTaskView.lastNode();
            }


//            subTaskView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(TeamInfoActivity.this, j+" ", Toast.LENGTH_SHORT).show();
//                }
//            });

            linearLayout.addView(convertView);
        }

        initView();
    }

    public static Date getCurrentTime(int position) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date date = null;
        try {
            date = sdf.parse(GlobalUtil.getInstance().getMyExeTask().get(position).getTargetDate());
        } catch (ParseException e) {
            Log.e("ERROR", "ERRRRR");
        }
        return date;
    }

    public void initView() {
        captionName.setText(GlobalUtil.getInstance().getTeamDTO().get(mPosition).getCreatorId().toString());
        if (mProject != -1) {
            projectName.setText(
                    GlobalUtil.getInstance().getMyExeProjectDTOs().get(mProject).getName());
            projectAttribute.setText(
                    GlobalUtil.getInstance().getMyExeProjectDTOs().get(mProject).getPriority() == 0 ? "工程实践" : "兴趣项目");
        }
        members.setText(mPosition+"");
        title.setText(GlobalUtil.getInstance().getTeamDTO().get(mPosition).getTeamName());
    }

}
