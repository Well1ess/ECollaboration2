package com.example.a29149.ecollaboration.model.project.manage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a29149.ecollaboration.R;
import com.example.a29149.ecollaboration.util.GlobalUtil;
import com.example.a29149.ecollaboration.util.log;

/**
 * Created by 29149 on 2017/3/27.
 */

public class ProjectListAdapter extends BaseAdapter {

    private Context mContext;
    public ProjectListAdapter(Context context)
    {
        this.mContext = context;
    }

    public void updateList()
    {
        this.notifyDataSetChanged();
        log.d(this, GlobalUtil.getInstance().getProjectDTOs().size());
    }

    @Override
    public int getCount() {
        return GlobalUtil.getInstance().getProjectDTOs().size();
    }

    @Override
    public Object getItem(int position) {
        return GlobalUtil.getInstance().getProjectDTOs().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null)
        {
            convertView = View.inflate(mContext, R.layout.list_project_layout, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(GlobalUtil.getInstance().getProjectDTOs().get(position).getName());
        viewHolder.content.setText(GlobalUtil.getInstance().getProjectDTOs().get(position).getInfo());
        return convertView;
    }

    static class ViewHolder
    {
        public TextView title;
        public TextView content;

        ViewHolder(View view)
        {
            title = (TextView)view.findViewById(R.id.project_title);
            content = (TextView)view.findViewById(R.id.project_content);
        }

    }
}
