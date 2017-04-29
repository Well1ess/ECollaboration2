package com.example.a29149.ecollaboration.widget.subtaskview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a29149.ecollaboration.R;

/**
 * Created by 29149 on 2017/3/19.
 */

public class SubTaskView extends ViewGroup {

    private int measureWidth=0;
    private int measureHeigh=0;

    private Paint drawUpLine;
    private Paint drawDownLine;
    private Paint drawBigCir;
    private Paint drawCir;

    public SubTaskView(Context context) {
        super(context);
        init();
    }

    public SubTaskView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init()
    {
        drawUpLine=new Paint();
        drawUpLine.setAntiAlias(true);
        drawUpLine.setColor(getResources().getColor(R.color.grey));
        drawUpLine.setStyle(Paint.Style.FILL);
        drawUpLine.setStrokeWidth(2);

        drawDownLine=new Paint();
        drawDownLine.setAntiAlias(true);
        drawDownLine.setColor(getResources().getColor(R.color.grey));
        drawDownLine.setStyle(Paint.Style.FILL);
        drawDownLine.setStrokeWidth(2);

        drawBigCir=new Paint();
        drawBigCir.setAntiAlias(true);
        drawBigCir.setColor(getResources().getColor(R.color.grey));
        drawBigCir.setStyle(Paint.Style.FILL);
        drawBigCir.setStrokeWidth(2);

        drawCir=new Paint();
        drawCir.setAntiAlias(true);
        drawCir.setColor(getResources().getColor(R.color.white));
        drawCir.setStyle(Paint.Style.FILL);
        drawCir.setStrokeWidth(2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d("Sub", "onMeasure");
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);

        measureChildren(widthMeasureSpec,heightMeasureSpec);

        int widthSpaceSize=MeasureSpec.getSize(widthMeasureSpec);
        int widthSpaceMode=MeasureSpec.getMode(widthMeasureSpec);
        int heightSpaceSize=MeasureSpec.getSize(heightMeasureSpec);
        int heightSpaceMode=MeasureSpec.getMode(heightMeasureSpec);

        View child1=getChildAt(0);
        View child2=getChildAt(1);
        measureHeigh=child1.getMeasuredHeight()>child2.getMeasuredHeight()?
                9*child1.getMeasuredHeight()/4+80:9*child2.getMeasuredHeight()/4+80;
        measureWidth=widthSpaceSize;

        measureChildren(MeasureSpec.makeMeasureSpec(2*measureWidth/3-160,widthSpaceMode),
                MeasureSpec.makeMeasureSpec(measureHeigh-80,heightSpaceMode));
        setMeasuredDimension(measureWidth,measureHeigh);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d("Sub", "onLayout");
        final int childCount = getChildCount();

        if (childCount >0)
        {
            getChildAt(0).layout(60, 40, measureWidth/3-60, measureHeigh-40);
            getChildAt(1).layout(measureWidth/3+80, 40, measureWidth-80, measureHeigh-40);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("Sub", "onDraw");

        canvas.drawLine(measureWidth/3, 0, measureWidth/3, 80, drawUpLine);
        canvas.drawLine(measureWidth/3, 80, measureWidth/3, measureHeigh, drawDownLine);

        canvas.drawCircle(measureWidth/3, 80, 15, drawBigCir);

        canvas.drawCircle(measureWidth/3, 80, 7, drawCir);
    }

    public void completeUp()
    {
        drawUpLine.setColor(getResources().getColor(R.color.colorPrimary));
        drawBigCir.setColor(getResources().getColor(R.color.colorPrimary));
        TextView child1=(TextView)getChildAt(0);
        child1.setTextColor(getResources().getColor(R.color.colorPrimary));
        TextView child2=(TextView)getChildAt(1);
        child2.setTextColor(getResources().getColor(R.color.colorPrimary));
        invalidate();
    }

    public void completeDown()
    {
        drawDownLine.setColor(getResources().getColor(R.color.colorPrimary));
        drawBigCir.setColor(getResources().getColor(R.color.colorPrimary));
        TextView child1=(TextView)getChildAt(0);
        child1.setTextColor(getResources().getColor(R.color.colorPrimary));
        TextView child2=(TextView)getChildAt(1);
        child2.setTextColor(getResources().getColor(R.color.colorPrimary));
        invalidate();
    }

    public void complete() {
        drawUpLine.setColor(getResources().getColor(R.color.colorPrimary));
        drawBigCir.setColor(getResources().getColor(R.color.colorPrimary));
        drawDownLine.setColor(getResources().getColor(R.color.colorPrimary));

        TextView child1=(TextView)getChildAt(0);
        child1.setTextColor(getResources().getColor(R.color.colorPrimary));
        TextView child2=(TextView)getChildAt(1);
        child2.setTextColor(getResources().getColor(R.color.colorPrimary));

        invalidate();
    }

    public void firstNode()
    {
        drawUpLine.setColor(getResources().getColor(R.color.white));
        invalidate();
    }

    public void lastNode()
    {
        drawDownLine.setColor(getResources().getColor(R.color.white));
        invalidate();
    }
}
