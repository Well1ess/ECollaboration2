package com.example.a29149.ecollaboration.model.team.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a29149.ecollaboration.R;
import com.example.a29149.ecollaboration.dto.TeamDTO;
import com.example.a29149.ecollaboration.httprequest.asynctask.team.TransForApplyTeam;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/1/4 0004.
 */

public class TeamSearchListAdapter extends BaseAdapter {
    private List<TeamDTO> teamDTOs;
    private Context context;

    private HashMap<Integer, View> mMap = new HashMap<>();

    public TeamSearchListAdapter(Context context, List<TeamDTO> teamDTOs)
    {
        this.context=context;
        this.teamDTOs=teamDTOs;

    }
    public void update(List<TeamDTO> teamDTOs)
    {
        this.teamDTOs=teamDTOs;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        if (this.teamDTOs==null)
        {
            return 0;
        }
        return teamDTOs.size();
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

        if (mMap.get(position) == null)
        {
            convertView = LayoutInflater
                    .from(context)
                    .inflate(R.layout.list_search, parent, false);
            mMap.put(position, convertView);
        }
        else
        {
            convertView = mMap.get(position);
        }

        TextView tv = (TextView) convertView.findViewById(R.id.team_list_item_name);
        tv.setText(teamDTOs.get(position).getTeamName());

        TextView name = (TextView) convertView.findViewById(R.id.creator);
        name.setText(teamDTOs.get(position).getCreatorId().toString());

        TextView apply = (TextView) convertView.findViewById(R.id.apply);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO：get network
                TransForApplyTeam applyTeam = new TransForApplyTeam();
                applyTeam.execute(teamDTOs.get(position).getId()+"");
            }
        });

        return convertView;
    }
}
