package com.example.a29149.ecollaboration.model.team.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a29149.ecollaboration.R;
import com.example.a29149.ecollaboration.dto.TaskDTO;
import com.example.a29149.ecollaboration.util.GlobalUtil;
import com.example.a29149.ecollaboration.widget.subtaskview.SubTaskView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by 29149 on 2017/3/20.
 */

public class TeamProjectTaskContainerAdapter extends BaseAdapter {

    private List<TaskDTO> taskDTOs;
    private Context context;
    private int position;

    private HashMap<Integer, View> container;

    public TeamProjectTaskContainerAdapter(Context context, int position)
    {
        this.context=context;
        this.position=position;
        this.taskDTOs= GlobalUtil.getInstance().getMyTask();
        container = new HashMap<>();
    }

    public void updateList()
    {
        this.taskDTOs= GlobalUtil.getInstance().getMyTask();
        this.notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return taskDTOs.get(position);
    }

    @Override
    public int getCount() {
        if (taskDTOs==null)
        {
            return 0;
        }
        return taskDTOs.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (container.get(position) == null)
        {
            convertView = View.inflate(context ,R.layout.list_task_detail, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
            container.put(position, convertView);
        }
        else
        {
            viewHolder = (ViewHolder) container.get(position).getTag();
        }

        viewHolder.time.setText(taskDTOs.get(position).getTime());
        viewHolder.content.setText(taskDTOs.get(position).getContent());
        if (position == 0)
        {
            viewHolder.subTaskView.firstNode();
        }
        if (position == getCount() - 1)
        {
            viewHolder.subTaskView.lastNode();
        }

        return convertView;
    }

    private class ViewHolder
    {
        public SubTaskView subTaskView;
        public TextView time;
        public TextView content;

        public ViewHolder(View v)
        {
            subTaskView=(SubTaskView)v.findViewById(R.id.task_container);
            time=(TextView)v.findViewById(R.id.time);
            content=(TextView)v.findViewById(R.id.content);
        }
    }
}
