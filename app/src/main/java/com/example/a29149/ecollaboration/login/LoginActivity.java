package com.example.a29149.ecollaboration.login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a29149.ecollaboration.R;
import com.example.a29149.ecollaboration.httprequest.asynctask.login.TransForLogin;
import com.example.a29149.ecollaboration.mainactivity.MainActivity;
import com.example.a29149.ecollaboration.util.AnnotationUtil;
import com.example.a29149.ecollaboration.util.UserConfig;
import com.example.a29149.ecollaboration.util.annotation.ViewInject;
import com.example.a29149.ecollaboration.widget.MyEditText;
import com.example.a29149.ecollaboration.widget.toast.LoadToast;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Created by Administrator on 2016/10/28 0028.
 */

public class LoginActivity extends Activity {

    @ViewInject(R.id.edittext_psd)
    private MyEditText mPassword;

    @ViewInject(R.id.edittext_account)
    private MyEditText mUserName;

    @ViewInject(R.id.button_login)
    private TextView mLoginBt;

    @ViewInject(R.id.login_mask)
    private LinearLayout mLinearLayoutMask;

    @ViewInject(R.id.rememberpsd)
    private CheckBox mRememberMe;

    private LoadToast mLoadToast;
    private UserConfig mUserConfig;

    private static final String USER_NAME = "username";
    private static final String PASSWORD = "password";
    private static final String SAVE_PASSWORD = "savePassWord";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        final double height = dm.heightPixels * 0.7;

        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);

        setContentView(R.layout.activity_login_layout);
        AnnotationUtil.setClickListener(this);
        AnnotationUtil.injectViews(this);
        EventBus.getDefault().register(this);

        mUserConfig = new UserConfig(this);
        mPassword.setTypeface(Typeface.DEFAULT);
        mLoadToast = new LoadToast(this).setText("Waiting···").setTranslationY((int) height).setProgressColor(R.color.colorAccent);

        if (mUserConfig.getBooleanInfo(SAVE_PASSWORD)) {
            mRememberMe.setChecked(true);
            mUserName.setText(mUserConfig.getStringInfo(USER_NAME));
            mPassword.setText(mUserConfig.getStringInfo(PASSWORD));

            mLinearLayoutMask.setVisibility(View.VISIBLE);
            mLoadToast.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    TransForLogin to = new TransForLogin(LoginActivity.this);
                    to.execute(mUserConfig.getStringInfo(USER_NAME), mUserConfig.getStringInfo(PASSWORD));
                }
            }, 500);
        }

        mLoginBt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        Animation scaleAnimation = new ScaleAnimation(1f, 1.2f, 1f, 1.2f,
                                Animation.RELATIVE_TO_SELF, 0.5f,
                                Animation.RELATIVE_TO_SELF, 0.5f);
                        scaleAnimation.setDuration(50);
                        scaleAnimation.setFillAfter(true);
                        v.startAnimation(scaleAnimation);

                        break;
                    case MotionEvent.ACTION_UP:
                        Animation scaleAnimation2 = new ScaleAnimation(1.2f, 1f, 1.2f, 1f,
                                Animation.RELATIVE_TO_SELF, 0.5f,
                                Animation.RELATIVE_TO_SELF, 0.5f);
                        scaleAnimation2.setDuration(50);
                        scaleAnimation2.setFillAfter(true);
                        v.startAnimation(scaleAnimation2);

                        mLinearLayoutMask.setVisibility(View.VISIBLE);
                        mLoadToast.show();

                        String username = mUserName.getText().toString();
                        String password = mPassword.getText().toString();

                        if (mRememberMe.isChecked()) {
                            mUserConfig.setUserInfo(USER_NAME, username);
                            mUserConfig.setUserInfo(PASSWORD, password);
                            mUserConfig.setUserInfo(SAVE_PASSWORD, true);
                        }

                        TransForLogin to = new TransForLogin(LoginActivity.this);
                        to.execute(username, password);

                        break;
                }
                return false;
            }
        });


    }

    public void showError() {
        mUserName.setText("");
        mPassword.setText("");
        new Handler().postDelayed(new Runnable() {
            public void run() {
                mLinearLayoutMask.setVisibility(View.GONE);
                mLoadToast.error();
            }
        }, (long) 1000);
    }

    public void showSuccess() {
        mLinearLayoutMask.setVisibility(View.GONE);
        mLoadToast.success();

        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);

                LoginActivity.this.startActivity(i);
                LoginActivity.this.finish();
                LoginActivity.this.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
            }
        }, (long) 1000);
    }

    @Subscribe
    public void onEventMainThread(LoginResultEvent loginResultEvent)
    {
        if (loginResultEvent.isSuccess())
            showSuccess();
        else
            showError();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }
}


