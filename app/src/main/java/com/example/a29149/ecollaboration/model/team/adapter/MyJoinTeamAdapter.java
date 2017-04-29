package com.example.a29149.ecollaboration.model.team.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a29149.ecollaboration.R;
import com.example.a29149.ecollaboration.util.GlobalUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/12/22 0022.
 */

public class MyJoinTeamAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> teamName;

    public MyJoinTeamAdapter(Context context) {
        mContext=context;
    }

    public void updateIcon(List<String> teamName)
    {
        if (this.teamName == null)
            this.teamName = new ArrayList<>();
        this.teamName.addAll(teamName);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(GlobalUtil.getInstance().getTeamDTO()!=null)
        {
            return GlobalUtil.getInstance().getTeamDTO().size();
        }
        return 0;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView= LayoutInflater.from(mContext).inflate(R.layout.list_team_layout, null);

        TextView msg=(TextView)itemView.findViewById(R.id.team_title_s);
        msg.setText(GlobalUtil.getInstance().getTeamDTO().get(position).getTeamName());

        if (teamName!=null&&teamName.contains(msg.getText().toString()))
        {
            itemView.findViewById(R.id.team_list_imageView_icon).setVisibility(View.VISIBLE);
            teamName.remove(msg.getText().toString());
        }
        return itemView;
    }
}
