package com.example.a29149.ecollaboration.model.message.historyapply.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a29149.ecollaboration.R;
import com.example.a29149.ecollaboration.dto.Apply;

import java.util.List;

/**
 * Created by Administrator on 2016/12/28 0028.
 */

public class ApplyListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Apply> applyList;

    public ApplyListAdapter(Context context, List<Apply> applyList) {
        mContext = context;
        this.applyList = applyList;
    }

    public void update(List<Apply> applyList)
    {
        this.applyList = applyList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(applyList!=null)
        {
            return applyList.size();
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
        MyViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_history_apply, null);
            holder = new MyViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }

        holder.icon.setText(applyList.get(position).getFlag());
        holder.title.setText(applyList.get(position).getTitle());
        holder.msg.setText(applyList.get(position).getContent());
        return convertView;
    }

    private static class MyViewHolder
    {
        public TextView icon;
        public TextView title;
        public TextView msg;
        public TextView time;

        MyViewHolder(View view) {
            icon = (TextView) view.findViewById(R.id.icon);
            title = (TextView) view.findViewById(R.id.apply_project_title);
            msg = (TextView) view.findViewById(R.id.apply_msg);
            time = (TextView) view.findViewById(R.id.apply_time);
        }
    }
}
