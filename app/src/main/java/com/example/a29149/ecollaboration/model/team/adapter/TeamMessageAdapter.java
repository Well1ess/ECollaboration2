package com.example.a29149.ecollaboration.model.team.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a29149.ecollaboration.R;
import com.example.a29149.ecollaboration.dto.MessageDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 29149 on 2017/3/31.
 */

public class TeamMessageAdapter extends BaseAdapter {

    private HashMap<Integer, View> mMap;
    private List<MessageDTO> mMessageDTOs;
    private Context mContext;
    public TeamMessageAdapter(Context context, List<MessageDTO> messageDTOs)
    {
        mMessageDTOs = messageDTOs;
        mContext = context;
        mMap = new HashMap<>();
    }

    public void update(List<MessageDTO> messageDTOs)
    {
        this.mMessageDTOs = messageDTOs;
        this.notifyDataSetChanged();
    }


    public List<MessageDTO> getmMessageDTOs() {
        return mMessageDTOs;
    }

    public HashMap<Integer, View> getmMap() {
        return mMap;
    }

    public void setmMap(HashMap<Integer, View> mMap) {
        this.mMap = mMap;
    }

    @Override
    public int getCount() {
        if (mMessageDTOs == null)
        {
            mMessageDTOs = new ArrayList<>();
        }
        return mMessageDTOs.size();
    }

    @Override
    public Object getItem(int position) {
        if (mMessageDTOs == null)
        {
            mMessageDTOs = new ArrayList<>();
        }
        return mMessageDTOs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView  = mMap.get(position);
        if (convertView == null)
        {
            convertView = LayoutInflater
                    .from(mContext)
                    .inflate(R.layout.list_team_message, parent, false);
            mMap.put(position, convertView);
        }
        TextView creator = (TextView) convertView.findViewById(R.id.msg_creator);
        TextView time = (TextView) convertView.findViewById(R.id.msg_time);
        TextView content = (TextView)  convertView.findViewById(R.id.msg_content);

        creator.setText(mMessageDTOs.get(position).getCreator());
        time.setText(mMessageDTOs.get(position).getTime());
        content.setText(mMessageDTOs.get(position).getContent());
        return convertView;
    }
}
