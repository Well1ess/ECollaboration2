package com.example.a29149.ecollaboration.model.message.historyapply;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.a29149.ecollaboration.R;
import com.example.a29149.ecollaboration.dto.ApplicationDTO;
import com.example.a29149.ecollaboration.dto.Apply;
import com.example.a29149.ecollaboration.httprequest.asynctask.infoandmessage.projectResponse.TransForGetAcceptApplyProject;
import com.example.a29149.ecollaboration.httprequest.asynctask.infoandmessage.projectResponse.TransForGetRefuseApplyProject;
import com.example.a29149.ecollaboration.httprequest.asynctask.infoandmessage.teamResponse.TransForGetAcceptApplyTeam;
import com.example.a29149.ecollaboration.httprequest.asynctask.infoandmessage.teamResponse.TransForGetRefuseApplyTeam;
import com.example.a29149.ecollaboration.model.message.historyapply.adapter.ApplyListAdapter;
import com.example.a29149.ecollaboration.util.AnnotationUtil;
import com.example.a29149.ecollaboration.util.annotation.ViewInject;
import com.example.a29149.ecollaboration.widget.dialog.ApplyInfoDialog;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;


public class HistoryApplyFragment extends Fragment {

    @ViewInject(R.id.history_apply_list)
    private ListView mHistoryApplyList;
    private ApplyListAdapter applyAdapter;
    private List<Apply> applyList;

    private List<ApplicationDTO> applicationDTOs;

    private ApplyInfoDialog.Builder mInfoDialog;
    private int position = 0;

    public HistoryApplyFragment() {
    }

    public static HistoryApplyFragment newInstance() {
        HistoryApplyFragment fragment = new HistoryApplyFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_histrory_apply, container, false);
        AnnotationUtil.injectViews(this, view);
        AnnotationUtil.setClickListener(this, view);

        initView();
        initInfoDialog();

        return view;
    }

    /**
     * 组件初始化
     */
    private void initView() {

        applyList = new ArrayList<>();
        applyAdapter = new ApplyListAdapter(getContext(), applyList);
        mHistoryApplyList.setAdapter(applyAdapter);

        mHistoryApplyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mInfoDialog.create().show();

                HistoryApplyFragment.this.position = position;
                mInfoDialog.setApplyContent(applyList.get(position).getContent());
                mInfoDialog.setApplyKind(applyList.get(position).getFlag());
                mInfoDialog.setApplyTarget(applyList.get(position).getTitle());
                mInfoDialog.setApplyCreator(applyList.get(position).getApplyMan());
            }
        });
    }

    /**
     * 初始化InfoDialog
     */
    private void initInfoDialog() {

        mInfoDialog = new ApplyInfoDialog.Builder(getContext());
        mInfoDialog.setPositiveButton("接     受", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (applyList.get(position).getFlag().equals("团队")) {
                    //TODO:网络连接
                    TransForGetAcceptApplyTeam acceptApplyTeam = new TransForGetAcceptApplyTeam();
                    acceptApplyTeam.execute(applicationDTOs.get(position).getId() + "");
                } else {
                    TransForGetAcceptApplyProject acceptApplyProject = new TransForGetAcceptApplyProject();
                    acceptApplyProject.execute(applicationDTOs.get(position).getId() + "");
                }

                applyList.remove(position);
                applyAdapter.update(applyList);
            }
        });
        mInfoDialog.setNegativeButton("拒     绝", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (applyList.get(position).getFlag().equals("团队")) {
                    //TODO:网络连接
                    TransForGetRefuseApplyTeam refuseApplyTeam = new TransForGetRefuseApplyTeam();
                    refuseApplyTeam.execute(applicationDTOs.get(position).getId() + "");
                } else {
                    TransForGetRefuseApplyProject refuseApplyProject = new TransForGetRefuseApplyProject();
                    refuseApplyProject.execute(applicationDTOs.get(position).getId() + "");
                }

                applyList.remove(position);
                applyAdapter.update(applyList);
            }
        });
    }

    /**
     * 与数据库通信
     */
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEventMainThread(GetApplyEvent getApplyEvent) {
        if (getApplyEvent.isSuccess() && getApplyEvent.getApplicationDTOs() != null) {
            //与后台数据交互
            applyList = new ArrayList<>();
            applyList.clear();
            for (int i = 0; i < getApplyEvent.getApplicationDTOs().size(); i++) {
                applyList.add(new Apply(getApplyEvent.getApplicationDTOs().get(i),
                        getApplyEvent.getName().get(i)));
            }
            applicationDTOs = getApplyEvent.getApplicationDTOs();
            applyAdapter.update(applyList);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }
}
