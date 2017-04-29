package com.example.a29149.ecollaboration.model.evaluation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a29149.ecollaboration.R;
import com.example.a29149.ecollaboration.dto.ProjectDTO;
import com.example.a29149.ecollaboration.util.GlobalUtil;
import com.example.a29149.ecollaboration.widget.GalleryView;

/**
 * Created by 寻乐人 on 2017/4/14.
 */

public class ImageAdapter extends BaseAdapter
{
    private Context mContext;

    public ImageAdapter(Context context)
    {
        mContext = context;
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
        if (convertView == null)
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_gallery_team_item_layout, null);
        convertView.setLayoutParams(new GalleryView.LayoutParams(400, 500));
        TextView evaluation_team_title = (TextView) convertView.findViewById(R.id.evaluation_team_title);
        TextView evaluation_team_project = (TextView) convertView.findViewById(R.id.evaluation_team_project);
        TextView captain_team = (TextView) convertView.findViewById(R.id.captain_team);

        ProjectDTO projectDTO = GlobalUtil.getInstance().getProjectDTOs().get(position);
        evaluation_team_title.setText(projectDTO.getName());
        evaluation_team_project.setText(projectDTO.getInfo());
        captain_team.setText(projectDTO.getPriority()==0?"工程实践":"兴趣项目");
        return convertView;
    }
}
