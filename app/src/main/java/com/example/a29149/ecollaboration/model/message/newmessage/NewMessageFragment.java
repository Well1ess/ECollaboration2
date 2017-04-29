package com.example.a29149.ecollaboration.model.message.newmessage;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a29149.ecollaboration.R;
import com.example.a29149.ecollaboration.model.message.newmessage.activity.SelectPersonActivity;
import com.example.a29149.ecollaboration.model.message.newmessage.sort.SelectedList;
import com.example.a29149.ecollaboration.util.ActivityCode;
import com.example.a29149.ecollaboration.util.AnnotationUtil;
import com.example.a29149.ecollaboration.util.annotation.OnClick;
import com.example.a29149.ecollaboration.util.annotation.ViewInject;

import java.util.ArrayList;

public class NewMessageFragment extends Fragment {

    @ViewInject(R.id.new_msg_name_list)
    private ListView mNewMsgNameList;
    private ListMsgItemAdapter mListMsgItemAdapter;

    @ViewInject(R.id.send_message_button)
    private TextView mSendMessageButton;

    private ArrayList<String> name;
    private SelectedList result;
    private ArrayList<String> resultStr;


    public NewMessageFragment() {
    }

    public static NewMessageFragment newInstance() {
        NewMessageFragment fragment = new NewMessageFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_message, container, false);
        AnnotationUtil.injectViews(this, view);
        AnnotationUtil.setClickListener(this, view);

        name=new ArrayList<>();
        result=new SelectedList();
        resultStr=new ArrayList<>();
        name.add("关羽");
        name.add("张飞");
        name.add("刘备");
        name.add("赵云");
        name.add("马超");
        name.add("waterzhj");
        name.add("gy");
        name.add("糜竺");
        name.add("糜芳");
        name.add("马岱");
        name.add("廖化");
        name.add("毛玠");
        name.add("于禁");
        name.add("典韦");

        initListView();

        return view;
    }

    private void initListView()
    {
        mListMsgItemAdapter = new ListMsgItemAdapter(getContext(),resultStr);
        mNewMsgNameList.setAdapter(mListMsgItemAdapter);
        mNewMsgNameList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                resultStr.remove(position);
                mListMsgItemAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    private void convertToStringList(SelectedList selectedList,ArrayList<String> strings)
    {
        strings.clear();
        for (int i=0;i<selectedList.getSortModels().size();i++)
        {
            strings.add(selectedList.getSortModels().get(i).getName());
        }
    }

    @OnClick(R.id.new_msg_name_spinner)
    public void spinnerClickListener(View view)
    {
        Intent intent=new Intent(getActivity(), SelectPersonActivity.class);

        Bundle bundle=new Bundle();
        bundle.putStringArrayList("name",name);

        intent.putExtras(bundle);
        startActivityForResult(intent, ActivityCode.NEW_MESSAGE_TO_SELECTED_PERSON);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (resultCode)
        {
            case ActivityCode.NEW_MESSAGE_TO_SELECTED_PERSON:
                Bundle bundle=data.getExtras();
                result=(SelectedList)bundle.getSerializable("result");

                Log.d("selectedList",result.getSortModels().size()+" ");
                convertToStringList(result,resultStr);
                mListMsgItemAdapter.UpdateDates(resultStr);

                break;
        }
    }

    public class ListMsgItemAdapter extends BaseAdapter {
        private ArrayList<String> mList;
        private Context mContext;

        public ListMsgItemAdapter(Context pContext, ArrayList<String> pList) {
            this.mContext = pContext;
            this.mList = pList;
        }

        public void UpdateDates(ArrayList<String> list)
        {
            this.mList=list;
            this.notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_new_info_team_and_man, null);
            if (convertView != null) {

                TextView name = (TextView) convertView.findViewById(R.id.list_item_name);
                name.setText(mList.get(position));
            }
            return convertView;
        }
    }

}
