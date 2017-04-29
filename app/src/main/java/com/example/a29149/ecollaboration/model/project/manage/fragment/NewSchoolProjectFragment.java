package com.example.a29149.ecollaboration.model.project.manage.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a29149.ecollaboration.R;
import com.example.a29149.ecollaboration.httprequest.asynctask.project.TransForCreateSchoolProject;
import com.example.a29149.ecollaboration.util.AnnotationUtil;
import com.example.a29149.ecollaboration.util.GlobalUtil;
import com.example.a29149.ecollaboration.util.annotation.OnClick;
import com.example.a29149.ecollaboration.util.annotation.ViewInject;
import com.example.a29149.ecollaboration.util.log;
import com.example.a29149.ecollaboration.widget.dialog.DateSelectedDialog;

import org.json.JSONArray;

import static com.example.a29149.ecollaboration.R.id.project_applyBeforeDate_edit;
import static com.example.a29149.ecollaboration.R.id.project_finishDate_edit;
import static com.example.a29149.ecollaboration.R.id.project_survivalDate_edit;
import static com.example.a29149.ecollaboration.R.id.project_teamMax_edit;


public class NewSchoolProjectFragment extends Fragment {

    @ViewInject(R.id.project_name_edit)
    private EditText mProjectName;

    @ViewInject(project_applyBeforeDate_edit)
    private TextView mApplyBeforeDate;

    @ViewInject(project_finishDate_edit)
    private TextView mFinishDate;

    @ViewInject(project_survivalDate_edit)
    private TextView mSurvivalDate;

    @ViewInject(project_teamMax_edit)
    private EditText mTeamMax;

    @ViewInject(R.id.project_memberMax_edit)
    private EditText mMemberMax;

    @ViewInject(R.id.project_keyWord_edit)
    private EditText mKeyWord;

    @ViewInject(R.id.project_content_edit)
    private EditText mInfo;

    @ViewInject(R.id.project_need_edit)
    private EditText mRequirement;

    @ViewInject(R.id.project_learn_skill_edit)
    private EditText mGain;

    @ViewInject(R.id.evaluation_list)
    private LinearLayout mEvaluationNames;

    private DateSelectedDialog.Builder mDataSelectedDialog;
    private int mDataPos = -1;

    public NewSchoolProjectFragment() {

    }

    public static NewSchoolProjectFragment newInstance() {
        NewSchoolProjectFragment fragment = new NewSchoolProjectFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_school, container, false);
        AnnotationUtil.injectViews(this, view);
        AnnotationUtil.setClickListener(this, view);
        mDataSelectedDialog = new DateSelectedDialog.Builder(getContext());
        mDataSelectedDialog.setNegativeButton("取      消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        mDataSelectedDialog.setPositiveButton("选      择", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                switch (mDataPos)
                {
                    case project_applyBeforeDate_edit:
                        mApplyBeforeDate.setText(mDataSelectedDialog.getDate());
                        break;
                    case project_finishDate_edit:
                        mFinishDate.setText(mDataSelectedDialog.getDate());
                        break;
                    case project_survivalDate_edit:
                        mSurvivalDate.setText(mDataSelectedDialog.getDate());
                        break;
                }
            }
        });
        return view;
    }

    @OnClick({project_applyBeforeDate_edit, project_finishDate_edit, project_survivalDate_edit})
    public void selectDataListener(View view)
    {
        TextView data = (TextView) view;
        mDataSelectedDialog.create().show();
        switch (view.getId())
        {
            case project_applyBeforeDate_edit:
                mDataPos = project_applyBeforeDate_edit;
                break;
            case project_finishDate_edit:
                mDataPos = project_finishDate_edit;
                break;
            case project_survivalDate_edit:
                mDataPos = project_survivalDate_edit;
                break;
        }
    }

    public void startCommitProject()
    {
        if (mProjectName.getText().toString().equals(""))
        {
            Toast.makeText(getContext(), "名称不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mTeamMax.getText().toString().equals(""))
        {
            Toast.makeText(getContext(), "最大团队数不能为空", Toast.LENGTH_SHORT).show();
            return;
        }


        if (mApplyBeforeDate.getText().toString().equals(""))
        {
            Toast.makeText(getContext(), "时间不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mFinishDate.getText().toString().equals(""))
        {
            Toast.makeText(getContext(), "时间不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mSurvivalDate.getText().toString().equals(""))
        {
            Toast.makeText(getContext(), "时间不能为空", Toast.LENGTH_SHORT).show();
            return;
        }


        if (mMemberMax.getText().toString().equals(""))
        {
            Toast.makeText(getContext(), "团队人员不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mKeyWord.getText().toString().equals(""))
        {
            Toast.makeText(getContext(), "关键字不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mInfo.getText().toString().equals(""))
        {
            Toast.makeText(getContext(), "信息不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mRequirement.getText().toString().equals(""))
        {
            Toast.makeText(getContext(), "要求不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mGain.getText().toString().equals(""))
        {
            Toast.makeText(getContext(), "收获不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        TransForCreateSchoolProject schoolProject = new TransForCreateSchoolProject(getActivity());
        schoolProject.execute(
                GlobalUtil.getInstance().getRole(),
                mProjectName.getText().toString(),
                mApplyBeforeDate.getText().toString(),
                mFinishDate.getText().toString(),
                mSurvivalDate.getText().toString(),
                mTeamMax.getText().toString(),
                mMemberMax.getText().toString(),
                mKeyWord.getText().toString(),
                mInfo.getText().toString(),
                mRequirement.getText().toString(),
                mGain.getText().toString(),
                getStrObject().toString());
    }


    @OnClick(R.id.dynamic_add)
    public void setAddEvaluationListener(View view)
    {
        final View root = LayoutInflater
                .from(getContext())
                .inflate(R.layout.list_dynamic_evaluation_item, null, false);
        mEvaluationNames.addView(root);

        ImageView remove = (ImageView) root.findViewById(R.id.remove);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEvaluationNames.removeView(root);
            }
        });

    }

    public JSONArray getStrObject()
    {
        JSONArray jsonArray = new JSONArray();

        for (int pos = 0; pos < mEvaluationNames.getChildCount(); pos++)
        {
            LinearLayout linearLayout = (LinearLayout) mEvaluationNames.getChildAt(pos);
            EditText name = (EditText) linearLayout.findViewById(R.id.evaluate_name);
            if (!name.getText().toString().equals(" "))
            {
                jsonArray.put(name.getText().toString());
            }

        }
        log.d(this, jsonArray.toString());
        return jsonArray;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
