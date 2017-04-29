package com.example.a29149.ecollaboration.model.team.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a29149.ecollaboration.R;
import com.example.a29149.ecollaboration.dto.MessageDTO;
import com.example.a29149.ecollaboration.model.team.adapter.TeamMessageAdapter;
import com.example.a29149.ecollaboration.util.ActivityCode;
import com.example.a29149.ecollaboration.util.AnnotationUtil;
import com.example.a29149.ecollaboration.util.GlobalUtil;
import com.example.a29149.ecollaboration.util.SQLiteDBUtil;
import com.example.a29149.ecollaboration.util.annotation.OnClick;
import com.example.a29149.ecollaboration.util.annotation.ViewInject;
import com.example.a29149.ecollaboration.widget.dialog.WarningDisplayDialog;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/28 0028.
 */

public class TeamDetailActivity extends AppCompatActivity{

    @ViewInject(R.id.title)
    private TextView mTitle;

    @ViewInject(R.id.message_container)
    private ListView mMessageList;
    private TeamMessageAdapter mMessageAdapter;

    @ViewInject(R.id.edit_panel)
    private AppBarLayout mEditPanel;

    @ViewInject(R.id.tab_main_delete)
    private RadioGroup mRadioGroup;

    @ViewInject(R.id.allselected)
    private TextView mAllSelectedBt;

    private WarningDisplayDialog.Builder mWarningDialog = new WarningDisplayDialog.Builder(this);

    private boolean mCheckBoxVisible = false;
    private boolean mAllSelected = false;
    private Intent intent;
    private int mPosition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_detail);
        AnnotationUtil.injectViews(this);
        AnnotationUtil.setClickListener(this);

        intent = getIntent();
        mPosition = intent.getIntExtra("position", -1);
        initView();

        mMessageAdapter = new TeamMessageAdapter(this, SQLiteDataToList("青葱岁月", "ECollaboration"));
        mMessageList.setAdapter(mMessageAdapter);
        mMessageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox checkBox = (CheckBox)mMessageAdapter.getmMap().get(position).findViewById(R.id.selected);
                if (checkBox.getVisibility() != View.GONE)
                {
                    if (checkBox.isChecked())
                    {
                        checkBox.setChecked(false);
                    }
                    else
                    {
                        checkBox.setChecked(true);
                    }
                }
            }
        });
        mMessageList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                if (!mCheckBoxVisible)
                {
                    displayOpenAnim();

                    View root = mMessageAdapter.getmMap().get(position);
                    root.findViewById(R.id.selected).setVisibility(View.VISIBLE);

                    CheckBox checkBox = (CheckBox)root.findViewById(R.id.selected);
                    checkBox.setChecked(true);

                }
                return true;
            }
        });
        mMessageList.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                mMessageList.setSelection(mMessageList.getCount() - 1);
            }
        });

        mWarningDialog.setNegativeButton("取      消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        mWarningDialog.setPositiveButton("删      除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mPosition == -1)
                {
                    Toast.makeText(TeamDetailActivity.this, "未知错误！", Toast.LENGTH_SHORT).show();
                }else
                {
                    //TODO:删除队伍
                    setResult(ActivityCode.EDIT, intent);
                    GlobalUtil.getInstance().getTeamDTO().remove(mPosition);
                    TeamDetailActivity.this.finish();
                }
            }
        });
        mWarningDialog.create();

    }

    public List<MessageDTO> SQLiteDataToList(String team, String project)
    {
        team = "404";
        project = "USTCzzz";
        List<MessageDTO> messageDTOs = new ArrayList<>();
        int size = SQLiteDBUtil.getSqLiteDBUtil().updateListByTeamNameAndProjectName(team, project);
        for (int i=0; i < size; i++)
        {
            messageDTOs.add(new MessageDTO(SQLiteDBUtil.getSqLiteDBUtil().getName().get(i),
                    SQLiteDBUtil.getSqLiteDBUtil().getContent().get(i),
                    SQLiteDBUtil.getSqLiteDBUtil().getTime().get(i),
                    SQLiteDBUtil.getSqLiteDBUtil().getId().get(i)));
        }
        return messageDTOs;
    }

    public void displayOpenAnim()
    {
        mEditPanel.setVisibility(View.INVISIBLE);
        mRadioGroup.setVisibility(View.INVISIBLE);

        for (int i =0; i<mMessageAdapter.getmMap().size(); i++)
        {
            View root = mMessageAdapter.getmMap().get(i);
            View checkbox = root.findViewById(R.id.selected);
            checkbox.setVisibility(View.VISIBLE);
        }

        mEditPanel.setVisibility(View.VISIBLE);
        mRadioGroup.setVisibility(View.VISIBLE);
        ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(mEditPanel,
                "translationY",
                -mEditPanel.getHeight(),
                0);
        objectAnimator.setDuration(150);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.start();

        ObjectAnimator rgObjectAnimator=ObjectAnimator.ofFloat(mRadioGroup,
                "translationY",
                mRadioGroup.getHeight(),
                0);
        rgObjectAnimator.setDuration(150);
        rgObjectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        rgObjectAnimator.start();

        mCheckBoxVisible = true;
    }

    public void displayCloseAnim()
    {

        ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(mEditPanel, "translationY", 0, -mEditPanel.getHeight());
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.start();
        objectAnimator.setDuration(150);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mEditPanel.setVisibility(View.GONE);
                for (int i =0; i<mMessageAdapter.getmMap().size(); i++)
                {
                    View root = mMessageAdapter.getmMap().get(i);
                    CheckBox checkBox = (CheckBox) root.findViewById(R.id.selected);
                    checkBox.setVisibility(View.GONE);
                    checkBox.setChecked(false);
                }
                mRadioGroup.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        ObjectAnimator rgObjectAnimator=ObjectAnimator.ofFloat(mRadioGroup,
                "translationY",
                0,
                mRadioGroup.getHeight());
        rgObjectAnimator.setDuration(150);
        rgObjectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        rgObjectAnimator.start();

        mCheckBoxVisible = false;
    }

    public void initView()
    {
        mTitle.setText(GlobalUtil.getInstance().getTeamDTO().get(mPosition).getTeamName());
    }

    @OnClick(R.id.allselected)
    public void allSelectedListener(View view)
    {
        if (mAllSelected)
        {
            for (int i =0; i<mMessageAdapter.getmMap().size(); i++)
            {
                View root = mMessageAdapter.getmMap().get(i);
                CheckBox checkBox = (CheckBox) root.findViewById(R.id.selected);
                checkBox.setChecked(false);
                mAllSelectedBt.setText("全选");
            }
            mAllSelected = false;
        }
        else
        {
            for (int i =0; i<mMessageAdapter.getmMap().size(); i++)
            {
                View root = mMessageAdapter.getmMap().get(i);
                CheckBox checkBox = (CheckBox) root.findViewById(R.id.selected);
                checkBox.setChecked(true);
                mAllSelectedBt.setText("不选");
            }
            mAllSelected = true;
        }
    }

    @OnClick(R.id.bt_cancel)
    public void cancelListener(View view)
    {
        displayCloseAnim();
    }

    @OnClick(R.id.bt_return)
    public void finishListener(View view)
    {
        this.finish();
    }

    @OnClick({R.id.main_menu_team, R.id.main_menu_edit, R.id.main_menu_delete})
    public void menuListener(View view)
    {
        switch (view.getId())
        {
            case R.id.main_menu_team:
                if (mPosition != -1)
                {
                    Intent teamInfo = new Intent(this, TeamInfoActivity.class);

                    teamInfo.putExtra("position", mPosition);
                    teamInfo.putExtra("project",this.intent.getIntExtra("project",-1));
                    startActivity(teamInfo);
                }else
                {
                    Toast.makeText(this, "团队信息不完整", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.main_menu_edit:
                //TODO:修改队伍
                if (mPosition != -1)
                {
                    setResult(ActivityCode.EDIT, intent);
                    Intent teamEdit = new Intent(this, EditTeamActivity.class);
                    teamEdit.putExtra("position", mPosition);
                    startActivity(teamEdit);

                }else
                {
                    Toast.makeText(this, "团队信息不完整", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.main_menu_delete:
                if (mPosition != -1)
                {
                    mWarningDialog.getDialog().show();
                    mWarningDialog.setMsg("您确定要删除该队伍吗？");
                }else
                {
                    Toast.makeText(this, "团队信息不完整", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    @OnClick(R.id.main_menu_edit_delete)
    public void msgDeleteListener(View view)
    {
        for (int i =0; i<mMessageAdapter.getmMap().size(); i++)
        {
            View root = mMessageAdapter.getmMap().get(i);
            CheckBox checkBox = (CheckBox) root.findViewById(R.id.selected);
            if (checkBox.isChecked())
            {
                SQLiteDBUtil.getSqLiteDBUtil().delete(
                        mMessageAdapter.getmMessageDTOs().get(i).getId());
            }
        }
        mMessageAdapter.update(SQLiteDataToList("青葱岁月", "ECollaboration"));
        displayCloseAnim();
    }

    @Override
    public void onBackPressed() {
        if (!mCheckBoxVisible)
            super.onBackPressed();
        else
        {
            displayCloseAnim();
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        initView();
    }
}
