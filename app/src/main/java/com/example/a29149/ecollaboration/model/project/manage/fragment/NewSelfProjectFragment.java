package com.example.a29149.ecollaboration.model.project.manage.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.a29149.ecollaboration.R;
import com.example.a29149.ecollaboration.util.AnnotationUtil;
import com.example.a29149.ecollaboration.util.annotation.OnClick;
import com.example.a29149.ecollaboration.util.annotation.ViewInject;

public class NewSelfProjectFragment extends Fragment {

    @ViewInject(R.id.evaluation_list)
    private LinearLayout mEvaluationNames;

    public NewSelfProjectFragment() {
    }

    public static NewSelfProjectFragment newInstance() {
        NewSelfProjectFragment fragment = new NewSelfProjectFragment();
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

        return view;
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
