package com.example.a29149.ecollaboration.model.team.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;

import com.example.a29149.ecollaboration.R;
import com.example.a29149.ecollaboration.util.AnnotationUtil;
import com.example.a29149.ecollaboration.util.annotation.OnClick;
import com.example.a29149.ecollaboration.util.annotation.ViewInject;

/**
 * Created by Administrator on 2016/12/12 0012.
 */

public class CheckPlanActivity extends AppCompatActivity {

    @ViewInject(R.id.events)
    private ExpandableListView displayNumbersEvents;

    private int mPosition;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_task);
        AnnotationUtil.injectViews(this);
        AnnotationUtil.setClickListener(this);

        intent = getIntent();
        mPosition = intent.getIntExtra("position", -1);
        //dispatcherEvents();
    }

    @OnClick(R.id.bt_return)
    public void closeListener(View view)
    {
        this.finish();
    }
    /*private void dispatcherEvents()
    {
        eventsForMumberArrayList.clear();

        for(String master:events.getMumbers())
        {
            EventsForMumber eventsForMumber=new EventsForMumber();
            eventsForMumber.setMaster(master);
            for (Event event:events.getEvents())
            {
                if(event.getCreator().equals(master))
                {
                    eventsForMumber.getEvents().add(event);
                }
            }

            eventsForMumberArrayList.add(eventsForMumber);
        }

    }*/
}
