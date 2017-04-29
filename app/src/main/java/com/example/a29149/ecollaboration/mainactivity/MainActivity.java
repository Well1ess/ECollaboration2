package com.example.a29149.ecollaboration.mainactivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a29149.ecollaboration.R;
import com.example.a29149.ecollaboration.aidl.BinderPoolCode;
import com.example.a29149.ecollaboration.aidl.BinderPoolUtil;
import com.example.a29149.ecollaboration.aidl.INewMessage;
import com.example.a29149.ecollaboration.aidl.impl.newmessage.DBInsertObserverImpl;
import com.example.a29149.ecollaboration.aidl.impl.newmessage.NewMessageImpl;
import com.example.a29149.ecollaboration.aidl.impl.newmessage.RefreshNewMessageFlag;
import com.example.a29149.ecollaboration.httprequest.asynctask.infoandmessage.TransForGetApply;
import com.example.a29149.ecollaboration.httprequest.asynctask.project.TransForGetSchoolProject;
import com.example.a29149.ecollaboration.httprequest.asynctask.task.TransForGetTask;
import com.example.a29149.ecollaboration.httprequest.asynctask.team.TransForGetMyJoinTeams;
import com.example.a29149.ecollaboration.login.LoginActivity;
import com.example.a29149.ecollaboration.model.evaluation.EvaluationMainFragment;
import com.example.a29149.ecollaboration.model.message.MessageMainFragment;
import com.example.a29149.ecollaboration.model.platform.PlatformMainFragment;
import com.example.a29149.ecollaboration.model.project.ProjectMainFragment;
import com.example.a29149.ecollaboration.model.team.TeamMainFragment;
import com.example.a29149.ecollaboration.myinterface.OnFragmentInteractionListener;
import com.example.a29149.ecollaboration.util.AnnotationUtil;
import com.example.a29149.ecollaboration.util.GlobalUtil;
import com.example.a29149.ecollaboration.util.UserConfig;
import com.example.a29149.ecollaboration.util.annotation.OnClick;
import com.example.a29149.ecollaboration.util.annotation.ViewInject;
import com.example.a29149.ecollaboration.widget.dialog.WarningDisplayDialog;
import com.example.a29149.ecollaboration.widget.shapeloading.ShapeLoadingDialog;
import com.example.a29149.ecollaboration.widget.toast.CustomToast;
import com.example.a29149.ecollaboration.widget.toast.SuccessToast;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * caused by z1h
 * app main launch
 */
public class MainActivity extends AppCompatActivity {



    public static final String SHOW_OF_FIRST_TAG = "first";
    public static final String SHOW_OF_SECOND_TAG = "second";
    public static final String SHOW_OF_THIRD_TAG = "third";
    public static final String SHOW_OF_FOURTH_TAG = "fourth";
    public static final String SHOW_OF_FIFTH_TAG = "fifth";

    @ViewInject(android.R.id.tabhost)
    private FragmentTabHost mFragmentTabhost;

    @ViewInject(R.id.tab_main_menu)
    private RadioGroup rg;

    @ViewInject(R.id.main_menu_team)
    private RadioButton firstBtn;//TODO:team

    @ViewInject(R.id.main_menu_project)
    private RadioButton secondBtn;//TODO:project

    @ViewInject(R.id.main_menu_evaluation)
    private RadioButton thirdBtn;//TODO:evaluation

    @ViewInject(R.id.main_menu_msg)
    private RadioButton fourthBtn;//TODO:message

    @ViewInject(R.id.main_menu_platform)
    private RadioButton fifthBtn;//TODO:platform

    @ViewInject(R.id.main_menu_team_tips)
    private View main_menu_team_tips;

    @ViewInject(R.id.main_menu_project_tips)
    private View main_menu_project_tips;

    @ViewInject(R.id.main_menu_evaluation_tips)
    private View main_menu_evaluation_tips;

    @ViewInject(R.id.main_menu_msg_tips)
    private View main_menu_msg_tips;

    @ViewInject(R.id.main_menu_platform_tips)
    private View main_menu_platform_tips;


    @ViewInject(R.id.user_photo)
    private ImageView mPhoto;

    @ViewInject(R.id.user_name)
    private TextView mName;

    @ViewInject(R.id.user_school)
    private TextView mSchool;

    @ViewInject(R.id.user_mobilePhone)
    private TextView mNumbers;

    @ViewInject(R.id.user_email)
    private TextView mEmail;

    @ViewInject(R.id.user_sex)
    private TextView mSex;

    @ViewInject(R.id.user_activeBefore)
    private TextView mAchieve;

    @ViewInject(R.id.user_lastLogTime)
    private TextView mLastLogin;


    public static ShapeLoadingDialog shapeLoadingDialog;
    private WarningDisplayDialog.Builder quitDialog;
    private OnFragmentInteractionListener onFragmentInteractionListener;

    private INewMessage iNewMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnnotationUtil.injectViews(this);
        AnnotationUtil.setClickListener(this);
        EventBus.getDefault().register(this);

        initQuitDialog();

        mFragmentTabhost.setup(this, getSupportFragmentManager(), R.id.main_tab_fragment);

        TabHost.TabSpec tabSpec0 = mFragmentTabhost.newTabSpec(SHOW_OF_FIRST_TAG)
                .setIndicator("0");
        TabHost.TabSpec tabSpec1 = mFragmentTabhost.newTabSpec(SHOW_OF_SECOND_TAG)
                .setIndicator("1");
        TabHost.TabSpec tabSpec2 = mFragmentTabhost.newTabSpec(SHOW_OF_THIRD_TAG)
                .setIndicator("2");
        TabHost.TabSpec tabSpec3 = mFragmentTabhost.newTabSpec(SHOW_OF_FOURTH_TAG)
                .setIndicator("3");
        TabHost.TabSpec tabSpec4 = mFragmentTabhost.newTabSpec(SHOW_OF_FIFTH_TAG)
                .setIndicator("4");

        mFragmentTabhost.addTab(tabSpec0, TeamMainFragment.class, null);
        mFragmentTabhost.addTab(tabSpec1, ProjectMainFragment.class, null);
        mFragmentTabhost.addTab(tabSpec2, EvaluationMainFragment.class, null);
        mFragmentTabhost.addTab(tabSpec3, MessageMainFragment.class, null);
        mFragmentTabhost.addTab(tabSpec4, PlatformMainFragment.class, null);

        firstBtn.setTextColor(0xff31C37C);
        initRadioGroupListener();

        shapeLoadingDialog = new ShapeLoadingDialog(this);
        shapeLoadingDialog.setLoadingText("加载中...");
        shapeLoadingDialog.setCanceledOnTouchOutside(false);

        quitDialog.create();
        //addListener.start();//启动服务

        initUserInfo();

    }

    /**
     * 对组件注入数据
     */
    public void initUserInfo()
    {
        if(GlobalUtil.getInstance().getmHead()!=null)
        {
            mPhoto.setImageBitmap(GlobalUtil.getInstance().getmHead());
        }else
        {
            mPhoto.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.photoerror));
        }
        if(GlobalUtil.getInstance().getUserDTO()!=null)
        {
            mName.setText(GlobalUtil.getInstance().getUserDTO().getName());
            mSchool.setText(GlobalUtil.getInstance().getUserDTO().getSchoolId());
            mNumbers.setText(GlobalUtil.getInstance().getUserDTO().getPhoneNumber());
            mEmail.setText(GlobalUtil.getInstance().getUserDTO().getEmail());
            mSex.setText(GlobalUtil.getInstance().getUserDTO().getSex()==0?"女":"男");
            mAchieve.setText(GlobalUtil.getInstance().getUserDTO().getActiveBefore());
            mLastLogin.setText(GlobalUtil.getInstance().getUserDTO().getLastLogTime());
        }
    }

    private void initQuitDialog() {
        quitDialog = new WarningDisplayDialog.Builder(this);
        quitDialog.setNegativeButton("取     消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        quitDialog.setPositiveButton("退     出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                MainActivity.this.finish();
                //TransForQuit transForQuit=new TransForQuit(MainActivity.this);
                //transForQuit.execute();
            }
        });
    }

    private void initRadioGroupListener() {
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.main_menu_team:
                        TransForGetMyJoinTeams joinTeams=new TransForGetMyJoinTeams();
                        joinTeams.execute();

//                        SQLiteDBUtil.getSqLiteDBUtil().insert("404", "张丽华", "今天下午开会", "2017-4-21",
//                                "USTCzzz");
//                        SQLiteDBUtil.getSqLiteDBUtil().insert("404", "张丽华", "《三国演义》是中国古典四大名著之一\n，是中国第一部长篇章回体历史演义小说，\n全名为《三国志通俗演义》（又称《三国志演义》），\n作者是元末明初的著名小说家罗贯中。", "2017-4-21",
//                                "USTCzzz");
//                        SQLiteDBUtil.getSqLiteDBUtil().insert("404", "张丽华", "黄巾之乱、董卓之乱、群雄逐鹿、三国鼎立、三国归晋五大部分", "2017-4-21",
//                                "USTCzzz");
//                        SQLiteDBUtil.getSqLiteDBUtil().insert("404", "张丽华", "China Joy", "2017-4-21",
//                                "USTCzzz");
//                        SQLiteDBUtil.getSqLiteDBUtil().insert("404", "张丽华", "▪ 汉室倾颓·讨伐董卓 \n" +
//                                        "▪ 群雄逐鹿·吕布覆灭 \n" +
//                                        "▪ 内阁密诏·千里独行 \n" +
//                                        "▪ 官渡之战·大破袁绍 \n" +
//                                        "▪ 三顾茅庐·荆襄之变 \n" +
//                                        "▪ 赤壁之战·火烧曹兵 \n" +
//                                        "▪ 三气周瑜·大战马超 \n" +
//                                        "▪ 夺占西川·合肥会战", "2017-4-21",
//                                "USTCzzz");


                        mFragmentTabhost.setCurrentTabByTag(SHOW_OF_FIRST_TAG);
                        resetMenuTextColor();
                        firstBtn.setTextColor(0xff31C37C);

                        main_menu_team_tips.setVisibility(View.INVISIBLE);
                        GlobalUtil.getInstance().setIndexChild(0);
                        break;
                    case R.id.main_menu_project:
                        TransForGetSchoolProject schoolProject = new TransForGetSchoolProject();
                        schoolProject.execute();

                        TransForGetTask getTask = new TransForGetTask();
                        getTask.execute();

                        mFragmentTabhost.setCurrentTabByTag(SHOW_OF_SECOND_TAG);
                        resetMenuTextColor();
                        secondBtn.setTextColor(0xff31C37C);

                        main_menu_project_tips.setVisibility(View.INVISIBLE);
                        GlobalUtil.getInstance().setIndexChild(0);
                        break;
                    case R.id.main_menu_evaluation:
                        //TransForGetTeamByTeacher transForGetTeamByTeacher = new TransForGetTeamByTeacher();
                        //transForGetTeamByTeacher.execute();

                        mFragmentTabhost.setCurrentTabByTag(SHOW_OF_THIRD_TAG);
                        resetMenuTextColor();
                        thirdBtn.setTextColor(0xff31C37C);

                        main_menu_evaluation_tips.setVisibility(View.INVISIBLE);
                        GlobalUtil.getInstance().setIndexChild(0);
                        break;
                    case R.id.main_menu_msg:
                        TransForGetApply applyMyTeam = new TransForGetApply();
                        applyMyTeam.execute();

                        mFragmentTabhost.setCurrentTabByTag(SHOW_OF_FOURTH_TAG);
                        resetMenuTextColor();
                        fourthBtn.setTextColor(0xff31C37C);

                        main_menu_msg_tips.setVisibility(View.INVISIBLE);
                        GlobalUtil.getInstance().setIndexChild(0);
                        break;
                    case R.id.main_menu_platform:
                        mFragmentTabhost.setCurrentTabByTag(SHOW_OF_FIFTH_TAG);
                        resetMenuTextColor();
                        fifthBtn.setTextColor(0xff31C37C);

                        main_menu_platform_tips.setVisibility(View.INVISIBLE);
                        GlobalUtil.getInstance().setIndexChild(0);
                        break;

                    default:
                        break;
                }
            }
        });
        registerReceiver()
    }

    private void resetMenuTextColor() {
        firstBtn.setTextColor(0xffaaaaaa);
        secondBtn.setTextColor(0xffaaaaaa);
        thirdBtn.setTextColor(0xffaaaaaa);
        fourthBtn.setTextColor(0xffaaaaaa);
        fifthBtn.setTextColor(0xffaaaaaa);
    }

    private Thread addListener = new Thread() {
        @Override
        public void run() {
            BinderPoolUtil binderPoolUtil = BinderPoolUtil.getInstance(MainActivity.this);

            INewMessage iNewMessage = (INewMessage) NewMessageImpl.
                    asInterface(binderPoolUtil.getServiceBinder(BinderPoolCode.NEW_MESSAGE));
            MainActivity.this.iNewMessage = iNewMessage;

            try {
                iNewMessage.registerListener(dbInsertObserver);
                iNewMessage.registerListener(refreshNewMessageFlag);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
    };

    //对业务做出反应
    private Handler centerHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RefreshNewMessageFlag.NEW_MESSAGE_TEAM:
                    main_menu_team_tips.setVisibility(View.VISIBLE);
                    break;
                default:
                    super.handleMessage(msg);
            }

        }
    };

    /**
     * 监听者初始化
     */
    private DBInsertObserverImpl dbInsertObserver = new DBInsertObserverImpl();

    private RefreshNewMessageFlag refreshNewMessageFlag = new RefreshNewMessageFlag(centerHandler);

    @OnClick(R.id.info_edit)
    public void setInfoEditListener(View view) {
        Intent intent = new Intent(this, UserInfoEditActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.bt_quit)
    public void setBackListener(View view) {
        UserConfig mUserConfig = new UserConfig(this);
        mUserConfig.clear();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    protected void onDestroy() {
        Log.d("Activity", "onDestroy");
        if (iNewMessage != null && iNewMessage.asBinder().isBinderAlive()) {
            Log.d("Activity", "disconnection");
            try {
                iNewMessage.unregisterListener(dbInsertObserver);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            BinderPoolUtil.getInstance(MainActivity.this).disconnectService();
        }
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        quitDialog.getDialog().show();
        quitDialog.setMsg("你确定要退出吗？");
    }


    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEventMainThread(HttpResponseEvent httpResponseEvent) {
        if (!httpResponseEvent.isSuccess()) {
            new CustomToast(this).setContent(httpResponseEvent.getMsg())
                    .setTime(Toast.LENGTH_SHORT).show();
        } else {
            new SuccessToast(this).setContent(httpResponseEvent.getMsg())
                    .setTime(Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onGetEditInfo(EditInfoEvent editInfoEvent)
    {
        if (editInfoEvent.isSuccess())
        {
            initUserInfo();
        }
    }

}
