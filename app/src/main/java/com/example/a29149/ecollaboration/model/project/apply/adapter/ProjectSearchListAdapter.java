package com.example.a29149.ecollaboration.model.project.apply.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a29149.ecollaboration.R;
import com.example.a29149.ecollaboration.dto.ProjectDTO;
import com.example.a29149.ecollaboration.httprequest.asynctask.project.TransForApplyProject;
import com.example.a29149.ecollaboration.util.GlobalUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/1/4 0004.
 */

public class ProjectSearchListAdapter extends BaseAdapter {
    private List<ProjectDTO> projectDTOs;
    private Context context;

    private AlertDialog alertDialog;
    private Integer mProjectId;

    private HashMap<Integer, View> mMap = new HashMap<>();


    public ProjectSearchListAdapter(Context context, List<ProjectDTO> projectDTOs) {
        this.context = context;
        this.projectDTOs = projectDTOs;

        TextView customTitle = new TextView(context);
        customTitle.setPadding(0, 20, 0, 0);
        customTitle.setText("请选择团队");
        customTitle.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        customTitle.setTextSize(21);
        customTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        customTitle.setGravity(Gravity.CENTER);

        ArrayList<String> strings = new ArrayList<>();

        for (int i = 0; i < GlobalUtil.getInstance().getTeamDTO().size(); i++) {
            strings.add(GlobalUtil.getInstance().getTeamDTO().get(i).getTeamName());
        }

        ArrayAdapter<String> teamProjectItem = new ArrayAdapter<String>(context,
                R.layout.dialog_team_project_item,
                strings);

        alertDialog = new AlertDialog.Builder(context)
                .setCustomTitle(customTitle)
                .setAdapter(teamProjectItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TransForApplyProject applyProject = new TransForApplyProject();
                        applyProject.execute(GlobalUtil.getInstance().getTeamDTO().get(which).getId() + "",
                                mProjectId + "");
                        dialog.dismiss();
                    }
                }).create();

    }

    public void update(List<ProjectDTO> projectBeen) {
        this.projectDTOs = projectBeen;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        if (this.projectDTOs == null) {
            return 0;
        }
        return projectDTOs.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (mMap.get(position) == null) {
            convertView = LayoutInflater
                    .from(context)
                    .inflate(R.layout.list_search, parent, false);
            mMap.put(position, convertView);
        } else {
            convertView = mMap.get(position);
        }

        TextView tv = (TextView) convertView.findViewById(R.id.team_list_item_name);
        tv.setText(projectDTOs.get(position).getName());

        TextView name = (TextView) convertView.findViewById(R.id.creator);
        name.setText(projectDTOs.get(position).getCreatorUserVOId() + "");

        TextView apply = (TextView) convertView.findViewById(R.id.apply);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProjectId = projectDTOs.get(position).getId();
                alertDialog.show();
            }
        });

        return convertView;
    }
}
