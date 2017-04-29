package com.example.a29149.ecollaboration.model.message.newmessage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a29149.ecollaboration.R;
import com.example.a29149.ecollaboration.model.message.newmessage.adapter.SortAdapter;
import com.example.a29149.ecollaboration.model.message.newmessage.sort.CharacterParser;
import com.example.a29149.ecollaboration.model.message.newmessage.sort.PinyinComparator;
import com.example.a29149.ecollaboration.model.message.newmessage.sort.SelectedList;
import com.example.a29149.ecollaboration.model.message.newmessage.sort.SortModel;
import com.example.a29149.ecollaboration.util.ActivityCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/12/14 0014.
 */

public class SelectPersonActivity extends AppCompatActivity {

    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<SortModel> SourceDateList;

    private List<SortModel> resultList;
    private SelectedList selectedList;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    private ListView personList;
    private SortAdapter sortAdapter;

    private Intent intent;

    private TextView textView1;
    private TextView textView2;
    private LinearLayout linearLayout1;
    private LinearLayout linearLayout2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_person);

        intent = getIntent();
        initViews();

        personList=(ListView)findViewById(R.id.select_person);
        sortAdapter=new SortAdapter(this, SourceDateList);
        personList.setAdapter(sortAdapter);

        personList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox checkBox=(CheckBox)view.findViewById(R.id.person_select);
                if(checkBox.isChecked())
                {
                    SourceDateList.get(position).setCheck(false);
                    sortAdapter.updateListView(SourceDateList);
                    resultList.remove(SourceDateList.get(position));
                }
                else
                {
                    SourceDateList.get(position).setCheck(true);
                    sortAdapter.updateListView(SourceDateList);
                    resultList.add(SourceDateList.get(position));
                    Collections.sort(resultList,pinyinComparator);
                }
            }
        });

        ImageView return_bt=(ImageView)findViewById(R.id.bt_return);
        return_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedList=new SelectedList();
                selectedList.setSortModels(resultList);

                Bundle bundle=new Bundle();
                bundle.putSerializable("result",selectedList);

                Log.d("selectedList1",selectedList.getSortModels().size()+" ");

                intent.putExtras(bundle);
                SelectPersonActivity.this.setResult(ActivityCode.NEW_MESSAGE_TO_SELECTED_PERSON,intent);
                finish();
            }
        });

        textView1=(TextView)findViewById(R.id.info_fragment2_new_msg_bt);
        textView2=(TextView)findViewById(R.id.info_fragment2_old_msg_bt);
        linearLayout1=(LinearLayout)findViewById(R.id.person_select_panel);
        linearLayout2=(LinearLayout)findViewById(R.id.team_select_panel);

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView2.setBackground(getResources().getDrawable(R.drawable.textview_right_menu_normal));
                textView2.setTextColor(getResources().getColor(R.color.transparent));

                textView1.setBackground(getResources().getDrawable(R.drawable.textview_left_menu_selected));
                textView1.setTextColor(getResources().getColor(R.color.colorPrimary));

                linearLayout1.setVisibility(View.VISIBLE);
                linearLayout2.setVisibility(View.GONE);
            }
        });

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView2.setBackground(getResources().getDrawable(R.drawable.textview_right_menu_selected));
                textView2.setTextColor(getResources().getColor(R.color.colorPrimary));

                textView1.setBackground(getResources().getDrawable(R.drawable.textview_left_menu_normal));
                textView1.setTextColor(getResources().getColor(R.color.transparent));

                linearLayout1.setVisibility(View.GONE);
                linearLayout2.setVisibility(View.VISIBLE);

            }
        });

    }

    private void initViews()
    {
        Bundle bundle = intent.getExtras();

        ArrayList<String> name = bundle.getStringArrayList("name");

        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        SourceDateList = filledData(name);
        Collections.sort(SourceDateList, pinyinComparator);

        resultList = new ArrayList<>();


    }

    /**
     * 为ListView填充数据
     * @param date
     * @return
     */
    private List<SortModel> filledData(ArrayList<String> date){
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for(int i=0; i<date.size(); i++){
            SortModel sortModel = new SortModel();
            sortModel.setName(date.get(i));

            //汉字转换成拼音
            String pinyin = characterParser.getSelling(date.get(i));
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if(sortString.matches("[A-Z]")){
                sortModel.setSortLetters(sortString.toUpperCase());
            }else{
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }
}
