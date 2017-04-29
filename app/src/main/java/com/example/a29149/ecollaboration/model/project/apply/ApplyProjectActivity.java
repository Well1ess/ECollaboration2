package com.example.a29149.ecollaboration.model.project.apply;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a29149.ecollaboration.R;
import com.example.a29149.ecollaboration.dto.ProjectDTO;
import com.example.a29149.ecollaboration.httprequest.asynctask.project.TransFoSearchProject;
import com.example.a29149.ecollaboration.mainactivity.MainActivity;
import com.example.a29149.ecollaboration.model.project.apply.adapter.ProjectSearchListAdapter;
import com.example.a29149.ecollaboration.util.AnnotationUtil;
import com.example.a29149.ecollaboration.util.annotation.OnClick;
import com.example.a29149.ecollaboration.util.annotation.ViewInject;
import com.example.a29149.ecollaboration.util.log;
import com.example.a29149.ecollaboration.widget.MyEditText;
import com.example.a29149.ecollaboration.widget.dialog.DisplayProjectInfoDialog;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class ApplyProjectActivity extends AppCompatActivity {

    @ViewInject(R.id.key_value)
    private MyEditText keyValue;

    @ViewInject(R.id.project_search)
    private ListView searchResult;

    public ProjectSearchListAdapter searchListAdapter;
    public List<ProjectDTO> projectDTOArrayList=new ArrayList<>();

    private DisplayProjectInfoDialog.Builder mDisplayProjectInfo;

    public ApplyProjectActivity() {
    }

    public static ApplyProjectActivity newInstance() {
        ApplyProjectActivity fragment = new ApplyProjectActivity();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_project);
        AnnotationUtil.injectViews(this);
        AnnotationUtil.setClickListener(this);
        EventBus.getDefault().register(this);

        mDisplayProjectInfo = new DisplayProjectInfoDialog.Builder(this);

        searchListAdapter = new ProjectSearchListAdapter(this, projectDTOArrayList);
        searchResult.setAdapter(searchListAdapter);
        searchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mDisplayProjectInfo.create().show();
                mDisplayProjectInfo.setProjectTitle(projectDTOArrayList.get(position).getName());
                mDisplayProjectInfo.setProjectCreater(projectDTOArrayList.get(position).getName());
                mDisplayProjectInfo.setProjectKind(projectDTOArrayList.get(position).getPriority() == 1?"工程实践":"兴趣项目");
                mDisplayProjectInfo.setProjectTime(projectDTOArrayList.get(position).getFinishDate());
                mDisplayProjectInfo.setNeedSkill(projectDTOArrayList.get(position).getRequirement());
                mDisplayProjectInfo.setLearnSkill(projectDTOArrayList.get(position).getGain());
                mDisplayProjectInfo.setProjectContent(projectDTOArrayList.get(position).getInfo());
            }
        });


    }


    @OnClick(R.id.project_search_bt)
    public void searchBtListener(View view)
    {
        if(keyValue.getText().toString().isEmpty())
        {
            Toast.makeText(ApplyProjectActivity.this, "关键字不能为空！", Toast.LENGTH_SHORT).show();
        }
        else
        {
            MainActivity.shapeLoadingDialog.show();
            //TODO:网络请求
            TransFoSearchProject searchTeam = new TransFoSearchProject();
            searchTeam.execute(keyValue.getText().toString());

            searchListAdapter.update(projectDTOArrayList);

        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEventMainThread(SearchProjectResultListEvent searchProjectResultListEvent)
    {
        projectDTOArrayList = searchProjectResultListEvent.getProjectDTOList();
        log.d(this, projectDTOArrayList.size());
        searchListAdapter.update(projectDTOArrayList);
    }

    @OnClick(R.id.bt_return)
    public void btReturnListener(View view)
    {
        ApplyProjectActivity.this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
