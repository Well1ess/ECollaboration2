package com.example.a29149.ecollaboration.widget.toast;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a29149.ecollaboration.R;

/**
 * Created by 张丽华 on 2016/11/15 0015.
 */

public class SuccessToast extends Toast {
    private Activity activity;

    public SuccessToast(Activity activity) {
        super(activity);
        this.activity=activity;

    }
    public SuccessToast setContent(String string){
        View v=activity.getLayoutInflater().inflate(R.layout.toast_success_layout,null);
        TextView txt=(TextView) v.findViewById(R.id.mytoast_content);
        txt.setText(string);
        this.setView(v);
        return this;
    }
    public SuccessToast setTime(int duration)
    {
        super.setDuration(duration);
        return this;
    }
    public SuccessToast setLocation(int gravity, int xOffset, int yOffset)
    {
        super.setGravity(gravity,xOffset, yOffset);
        return this;
    }
}
