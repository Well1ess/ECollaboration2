package com.example.a29149.ecollaboration.model.project.task.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a29149.ecollaboration.R;
import com.example.a29149.ecollaboration.util.GlobalUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 29149 on 2017/3/20.
 */

public class TaskParentList extends BaseExpandableListAdapter {

    private Context context;
    private List<String> task;

    private Integer mTaskId;

    public TaskParentList(Context context)
    {
        task = new ArrayList<>();

        for (int i = 0; i< GlobalUtil.getInstance().getMyTask().size(); i++)
        {
            task.add("任务<<"+i+">>");
        }
        this.context=context;

    }

    public void updateList()
    {
        task.clear();

        for (int i = 0; i< GlobalUtil.getInstance().getMyTask().size(); i++)
        {
            task.add("任务<<"+i+">>");
        }

        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return task.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return task.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder groupHolder;
        if (convertView == null)
        {
            convertView=View.inflate(context, R.layout.expandablelistview_task_group, null);
            groupHolder=new GroupHolder(convertView);
            convertView.setTag(groupHolder);
        }
        else
        {
            groupHolder=(GroupHolder)convertView.getTag();
        }
        groupHolder.title.setText(task.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        View root=View.inflate(context, R.layout.list_task_teamsname, null);

//        ListView listView=(ListView)root.findViewById(R.id.task_team);
//        listView.setAdapter(new ArrayAdapter(
//                context,
//                R.layout.item_teamname,
//                new String[]{"青葱岁月", "404", "404"}));
//
//        fixListViewHeight(listView);
        return root;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private TextView getTextView() {
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView textView = new TextView(context);
        textView.setLayoutParams(lp);
        textView.setGravity(Gravity.LEFT);
        textView.setTextSize(18);
        return textView;
    }

    public void fixListViewHeight(ListView listView) {
        // 如果没有设置数据适配器，则ListView没有子项，返回。  
        ListAdapter listAdapter = listView.getAdapter();
        int totalHeight = 0;
        if (listAdapter == null) {
            return;
        }
        for (int index = 0, len = listAdapter.getCount(); index < len; index++) {
            View listViewItem = listAdapter.getView(index, null, listView);
            // 计算子项View 的宽高   
            listViewItem.measure(0, 0);
            // 计算所有子项的高度和
            totalHeight += listViewItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // listView.getDividerHeight()获取子项间分隔符的高度   
        // params.height设置ListView完全显示需要的高度    

        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    private static class GroupHolder
    {
        public TextView title;
        public TextView dispatch;

        public GroupHolder(View view)
        {
            this.title=(TextView)view.findViewById(R.id.title);
            this.dispatch = (TextView)view.findViewById(R.id.dispatch);
        }
    }
}
