package com.example.a29149.ecollaboration.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

import com.example.a29149.ecollaboration.util.GlobalUtil;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by 29149 on 2017/3/24.
 */

public class SlideView extends ViewGroup {

    private View mMenuLayout;
    private View mMainLayout;

    private int marginLeft=150;
    private int mScreenWidth;

    private int mChildrenSize;
    private int mChildWidth;
    private int mCurrentChild = 1;

    private int mLastX = 0;
    private int mLastY = 0;

    private int mLastXIntercept = 0;
    private int mLastYIntercept = 0;

    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;

    public SlideView(Context context)
    {
        super(context);
        init(context);
    }

    public SlideView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public SlideView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context)
    {
        if (mScroller == null)
        {
            mScroller = new Scroller(getContext());
            mVelocityTracker = VelocityTracker.obtain();
        }


        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        mScreenWidth = displayMetrics.widthPixels;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = false;
        int x = (int)ev.getX();
        int y = (int)ev.getY();

        switch (ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                intercepted = false;
                if(!mScroller.isFinished())
                {
                    mScroller.abortAnimation();
                    intercepted=true;
                }
                if (mScreenWidth > x && x > mScreenWidth-marginLeft && mCurrentChild == 0)
                {
                    intercepted = true;
                    //mCurrentChild = 1;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastXIntercept;
                int deltaY = y - mLastYIntercept;
                if(Math.abs(deltaX) > Math.abs(deltaY))
                {
                    if (mCurrentChild == 1)
                    {
                        if (GlobalUtil.getInstance().getIndexChild() == 0)
                        {
                            if (deltaX > 0)
                            {
                                intercepted = true;
                            }
                            else
                            {
                                intercepted = false;
                            }

                        }
                        else
                        {
                            intercepted = false;
                        }
                    }
                    if (mCurrentChild == 0)
                    {
                        intercepted = true;
                    }
                }
                else
                {
                    intercepted = false;
                }
                break;
            case  MotionEvent.ACTION_UP:
                intercepted = false;
                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;

        mLastXIntercept = x;
        mLastYIntercept = y;

        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        mVelocityTracker.addMovement(ev);

        int x = (int)ev.getX();
        int y = (int)ev.getY();

        switch (ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if(!mScroller.isFinished())
                {
                    mScroller.abortAnimation();
                }
                /*if (mCurrentChild == 1 && getScrollX() == 0)
                {
                    smoothScrollBy(mChildWidth - marginLeft - getScrollX(), 0);
                }*/
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                scrollBy(-deltaX, 0);
                break;
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                mVelocityTracker.computeCurrentVelocity(1000);
                float xVelocity = mVelocityTracker.getXVelocity();
                if (Math.abs(xVelocity) >= 100)
                {
                    mCurrentChild = xVelocity > 0 ? mCurrentChild - 1 : mChildWidth + 1;
                }
                else
                {
                    mCurrentChild = (scrollX + mChildWidth / 2) / mChildWidth;
                }
                mCurrentChild = Math.max(0 , Math.min(mCurrentChild, mChildrenSize - 1));

                int dx = mCurrentChild * mChildWidth - scrollX;
                if (mCurrentChild == 1)
                {
                    dx = dx - marginLeft;
                }
                smoothScrollBy(dx, 0);
                mVelocityTracker.clear();

                if (mScreenWidth > x && x > mScreenWidth-marginLeft && mCurrentChild == 0 && getScrollX() == 0)
                {
                    mCurrentChild = 1;
                    smoothScrollBy(mChildWidth - marginLeft - getScrollX(), 0);
                }
                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = 0;
        int measureHeight = 0;
        final int childCount = getChildCount();
        //measureChildren(widthMeasureSpec, heightMeasureSpec);
        measureChild(getChildAt(0), widthMeasureSpec - marginLeft, heightMeasureSpec);
        measureChild(getChildAt(1), widthMeasureSpec, heightMeasureSpec);

        int widthSpaceSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpaceMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpaceSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpaceMode = MeasureSpec.getMode(heightMeasureSpec);

        if (childCount == 0)
        {
            setMeasuredDimension(0 ,0);
        }else if(widthSpaceMode == MeasureSpec.AT_MOST &&
                heightSpaceMode == MeasureSpec.AT_MOST)
        {
            final View childView = getChildAt(0);
            measureWidth = childView.getMeasuredWidth() * childCount;
            measureHeight = childView.getMeasuredHeight();
            setMeasuredDimension(measureWidth - marginLeft, measureHeight);
        }else if(widthSpaceMode == MeasureSpec.AT_MOST)
        {
            final View childView = getChildAt(0);
            measureWidth = childView.getMeasuredWidth() * childCount;
            setMeasuredDimension(measureWidth - marginLeft, heightSpaceSize);
        }else if(heightSpaceMode == MeasureSpec.AT_MOST)
        {
            final View childView = getChildAt(0);
            measureHeight = childView.getMeasuredWidth();
            setMeasuredDimension(widthSpaceSize - marginLeft, measureHeight);
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childLeft = 0;
        final int childCount = getChildCount();
        mChildrenSize = childCount;

        //菜单布局
        mMenuLayout =  getChildAt(0);

        //主界面布局
        mMainLayout =  getChildAt(1);

        for (int i = 0; i<childCount; i++)
        {
            final View childView = getChildAt(i);
            if(childView.getVisibility() != View.GONE)
            {
                int childWidth = childView.getMeasuredWidth();

                //if (i == 0)
                 //   childWidth = childWidth - marginLeft;

                mChildWidth = childWidth;

                childView.layout(childLeft, 0, childLeft + childWidth,
                        childView.getMeasuredHeight());
                childLeft += childWidth;
            }
        }

        if (getScrollX() == 0)
            scrollBy(mScreenWidth - marginLeft, 0);

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt)
    {
        super.onScrollChanged(l, t, oldl, oldt);
        //缩放比例
        float scale = (float)l/(mChildWidth - marginLeft);
        Log.e("TAG", scale+"");
        //左菜单缩放
        float scaleLeft = 1-0.3f*scale;
        ViewHelper.setScaleX(mMenuLayout, scaleLeft);
        ViewHelper.setScaleY(mMenuLayout, scaleLeft);
        //透明度缩放
        float alphaLeft = 1 - scale;
        ViewHelper.setAlpha(mMenuLayout, alphaLeft);

        float alphaRight = 1 - alphaLeft + 0.5f;
        ViewHelper.setAlpha(mMainLayout, alphaRight);

        //主界面
        float scaleRight = 0.85f + scale * 0.15f;
        //ViewHelper.setScaleX(mMainLayout, scaleRight);
        ViewHelper.setScaleY(mMainLayout, scaleRight);
        //视觉差平移
        ViewHelper.setTranslationX(mMenuLayout, (mChildWidth - marginLeft) * scale);

    }

    @Override
    public void computeScroll()
    {
        if (mScroller.computeScrollOffset())
        {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow()
    {
        mVelocityTracker.recycle();
        super.onDetachedFromWindow();
    }

    private void smoothScrollBy(int dx, int dy)
    {
        mScroller.startScroll(getScrollX(), 0, dx, 0);
        invalidate();
    }
}
