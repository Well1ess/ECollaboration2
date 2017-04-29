package com.example.a29149.ecollaboration.model.message.newmessage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.a29149.ecollaboration.R;
import com.example.a29149.ecollaboration.model.message.newmessage.sort.SortModel;

import java.util.List;

/**
 * Created by Administrator on 2016/12/14 0014.
 */

public class SortAdapter extends BaseAdapter implements SectionIndexer {

    private List<SortModel> list = null;
    private Context mContext;

    public SortAdapter(Context mContext, List<SortModel> list){
        this.mContext = mContext;
        this.list = list;
    }
    public void updateListView(List<SortModel> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.list_item_sort, null);
        TextView tvTitle = (TextView) linearLayout.findViewById(R.id.title);
        TextView tvLetter = (TextView) linearLayout.findViewById(R.id.catalog);
        CheckBox checkBox=(CheckBox) linearLayout.findViewById(R.id.person_select);

        //根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);
        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            tvLetter.setVisibility(View.VISIBLE);
            tvLetter.setText(this.list.get(position).getSortLetters());
        }else {
            tvLetter.setVisibility(View.GONE);
        }

        if(this.list.get(position).isCheck())
        {
            checkBox.setChecked(true);
        }else
        {
            checkBox.setChecked(false);
        }
        tvTitle.setText(this.list.get(position).getName());
        return linearLayout;
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == sectionIndex) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return list.get(position).getSortLetters().charAt(0);
    }

}
