package com.example.a29149.ecollaboration.model.project.task.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a29149.ecollaboration.R;
import com.example.a29149.ecollaboration.dto.TaskDTO;
import com.example.a29149.ecollaboration.util.GlobalUtil;
import com.example.a29149.ecollaboration.widget.subtaskview.SubTaskView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 29149 on 2017/3/20.
 */

public class TaskEditContainerAdapter extends BaseAdapter {

    private int position;
    private List<TaskDTO> taskDTOs;
    private Context context;

    public TaskEditContainerAdapter(Context context, int position) {
        this.context = context;
        this.position = position;
        if (position != -1) {
            taskDTOs = new ArrayList<>();
            taskDTOs.addAll(GlobalUtil.getInstance().getMyTask());
        } else {
            taskDTOs = new ArrayList<>();
        }
    }

    public void updateList() {
        this.notifyDataSetChanged();

    }

    public List<TaskDTO> getList() {
        if (taskDTOs == null)
            taskDTOs = new ArrayList<>();
        return taskDTOs;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        if (taskDTOs == null) {
            return 0;
        }
        return taskDTOs.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.list_task_detail_edit, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.time.setText(taskDTOs.get(position).getTime());
        viewHolder.content.setText(taskDTOs.get(position).getContent());
        viewHolder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskDTOs.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    private static class ViewHolder {
        public View root;
        public SubTaskView subTaskView;
        public TextView time;
        public TextView content;
        public ImageView remove;

        public ViewHolder(View v) {
            subTaskView = (SubTaskView) v.findViewById(R.id.task_container);
            time = (TextView) v.findViewById(R.id.time);
            content = (TextView) v.findViewById(R.id.content);
            remove = (ImageView) v.findViewById(R.id.remove);
        }
    }
}
