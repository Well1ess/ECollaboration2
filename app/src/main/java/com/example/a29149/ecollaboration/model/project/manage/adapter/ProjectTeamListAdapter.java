package com.example.a29149.ecollaboration.model.project.manage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a29149.ecollaboration.R;
import com.example.a29149.ecollaboration.util.GlobalUtil;

/**
 * Created by Administrator on 2016/12/26 0026.
 */

public class ProjectTeamListAdapter extends BaseAdapter
{

    private Context mContext;

    public ProjectTeamListAdapter(Context context)
    {
        this.mContext = context;
    }

    public void update()
    {
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return GlobalUtil.getInstance().getTeamDTO().size();
    }

    @Override
    public Object getItem(int position) {
        return GlobalUtil.getInstance().getTeamDTO().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder = null;
        if (convertView == null)
        {
            convertView = LayoutInflater
                    .from(mContext)
                    .inflate(R.layout.gridview_project_team_item_layout, parent, false);
            myViewHolder = new MyViewHolder(convertView);
            convertView.setTag(myViewHolder);
        }
        else
        {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }
        myViewHolder.tv.setText(GlobalUtil.getInstance().getTeamDTO().get(position).getTeamName());
        return convertView;
    }

    static class MyViewHolder
    {

        TextView tv;

        public MyViewHolder(View view)
        {
            tv = (TextView) view.findViewById(R.id.id_num);
        }
    }
}

