package com.example.a29149.ecollaboration.widget.toast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a29149.ecollaboration.R;


/**
 * Created by Administrator on 2016/11/15 0015.
 */

public class CustomToast extends Toast {
    private Context mContent;

    public CustomToast(Context context) {
        super(context);
        this.mContent = context;
    }

    public CustomToast setContent(String string) {
        View v = LayoutInflater.from(mContent).inflate(R.layout.toast_custom_layout, null);
        TextView txt = (TextView) v.findViewById(R.id.mytoast_content);
        txt.setText(string);
        this.setView(v);
        return this;
    }

    public CustomToast setTime(int duration) {
        super.setDuration(duration);
        return this;
    }

    public CustomToast setLocation(int gravity, int xOffset, int yOffset) {
        super.setGravity(gravity, xOffset, yOffset);
        return this;
    }
}
