package com.example.a29149.ecollaboration.model.message;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.example.a29149.ecollaboration.R;
import com.example.a29149.ecollaboration.model.message.historyapply.HistoryApplyFragment;
import com.example.a29149.ecollaboration.model.message.newmessage.NewMessageFragment;
import com.example.a29149.ecollaboration.util.AnnotationUtil;
import com.example.a29149.ecollaboration.util.annotation.ViewInject;
import com.example.a29149.ecollaboration.util.GlobalUtil;

import java.util.ArrayList;
import java.util.List;

public class MessageMainFragment extends Fragment {

    View view;

    @ViewInject(R.id.tab_project_menu)
    private RadioGroup rg;

    @ViewInject(R.id.project_manage)
    private RadioButton projectManage;

    @ViewInject(R.id.project_task)
    private RadioButton projectTask;

    @ViewInject(android.R.id.tabhost)
    private FragmentTabHost mFragmentTabhost;

    @ViewInject(R.id.project_pager)
    private ViewPager mViewPager;

    public static final String SHOW_OF_FIRST_TAG = "manage";
    public static final String SHOW_OF_THIRD_TAG = "task";

    private List<Fragment> fragmentList;

    public MessageMainFragment() {

    }

    public static MessageMainFragment newInstance(String param1, String param2) {
        MessageMainFragment fragment = new MessageMainFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_message_main, container, false);
        AnnotationUtil.injectViews(this, view);
        AnnotationUtil.setClickListener(this,view);

        fragmentList = new ArrayList<Fragment>();

        mFragmentTabhost.setup(getActivity(),getChildFragmentManager(), R.id.project_pager);

        TabSpec tabSpec0 = mFragmentTabhost.newTabSpec(SHOW_OF_FIRST_TAG)
                .setIndicator("0");
        TabSpec tabSpec1 = mFragmentTabhost.newTabSpec(SHOW_OF_THIRD_TAG)
                .setIndicator("1");

        mFragmentTabhost.addTab(tabSpec0, HistoryApplyFragment.class, null);
        mFragmentTabhost.addTab(tabSpec1, NewMessageFragment.class, null);

        initRadioGroupListener();

        mFragmentTabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {
                int position = mFragmentTabhost.getCurrentTab();
                mViewPager.setCurrentItem(position);
            }
        });

        mFragmentTabhost.setCurrentTab(0);
        projectManage.setTextColor(getResources().getColor(R.color.colorPrimary));

        HistoryApplyFragment historyApplyFragment = new HistoryApplyFragment();
        NewMessageFragment newMessageFragment = new NewMessageFragment();

        fragmentList.add(historyApplyFragment);
        fragmentList.add(newMessageFragment);

        mViewPager.setAdapter(new MenuAdapter(getChildFragmentManager()));
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setOnPageChangeListener(new ViewPageListener());

        return view;
    }

    private void initRadioGroupListener()
    {
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.project_manage:
                        mFragmentTabhost.setCurrentTabByTag(SHOW_OF_FIRST_TAG);
                        resetMenuTextColor();
                        projectManage.setTextColor(getResources().getColor(R.color.colorPrimary));
                        break;
                    case R.id.project_task:
                        mFragmentTabhost.setCurrentTabByTag(SHOW_OF_THIRD_TAG);
                        resetMenuTextColor();
                        projectTask.setTextColor(getResources().getColor(R.color.colorPrimary));
                        break;
                    default:
                        break;
                }
            }
        });
    }


    private void resetMenuTextColor()
    {
        projectManage.setTextColor(getResources().getColor(R.color.white));
        projectTask.setTextColor(getResources().getColor(R.color.white));
    }

    class MenuAdapter extends FragmentPagerAdapter {

        public MenuAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            return fragmentList.get(arg0);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

    }


    class ViewPageListener implements ViewPager.OnPageChangeListener
    {
        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        //当前页面被滑动时调用
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels){

        }

        @Override
        public void onPageSelected(int index) {
            if (index == 0) {
                projectManage.setChecked(true);

            } else if (index == 1) {
                projectTask.setChecked(true);

            }
            GlobalUtil.getInstance().setIndexChild(index);
            mFragmentTabhost.setCurrentTab(index);
        }
    }

}
