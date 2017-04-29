package com.example.a29149.ecollaboration.model.project;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.example.a29149.ecollaboration.R;
import com.example.a29149.ecollaboration.model.project.apply.ApplyProjectActivity;
import com.example.a29149.ecollaboration.model.project.manage.ManageProjectFragment;
import com.example.a29149.ecollaboration.model.project.manage.activity.NewProjectActivity;
import com.example.a29149.ecollaboration.model.project.task.TaskFragment;
import com.example.a29149.ecollaboration.model.project.task.activity.TaskEditActivity;
import com.example.a29149.ecollaboration.util.AnnotationUtil;
import com.example.a29149.ecollaboration.util.annotation.OnClick;
import com.example.a29149.ecollaboration.util.annotation.ViewInject;
import com.example.a29149.ecollaboration.util.GlobalUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class ProjectMainFragment extends Fragment {

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

    @ViewInject(R.id.bt_add)
    private RelativeLayout addRelativeLayout;

    private PopupMenu popupMenu;


    public static final String SHOW_OF_FIRST_TAG = "manage";
    public static final String SHOW_OF_THIRD_TAG = "task";

    private List<Fragment> fragmentList;

    public ProjectMainFragment() {

    }

    public static ProjectMainFragment newInstance() {
        ProjectMainFragment fragment = new ProjectMainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_project_main, container, false);
        AnnotationUtil.injectViews(this, view);
        AnnotationUtil.setClickListener(this,view);

        fragmentList = new ArrayList<Fragment>();

        mFragmentTabhost.setup(getActivity(),getChildFragmentManager(), R.id.project_pager);

        TabSpec tabSpec0 = mFragmentTabhost.newTabSpec(SHOW_OF_FIRST_TAG)
                .setIndicator("0");
        TabSpec tabSpec2 = mFragmentTabhost.newTabSpec(SHOW_OF_THIRD_TAG)
                .setIndicator("1");

        mFragmentTabhost.addTab(tabSpec0, ManageProjectFragment.class, null);
        mFragmentTabhost.addTab(tabSpec2, TaskFragment.class, null);

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

        ManageProjectFragment manageProjectFragment = new ManageProjectFragment();
        TaskFragment taskFragment = new TaskFragment();

        fragmentList.add(manageProjectFragment);
        fragmentList.add(taskFragment);

        mViewPager.setAdapter(new MenuAdapter(getChildFragmentManager()));
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setOnPageChangeListener(new ViewPageListener());


        popupMenu=new PopupMenu(getActivity(), addRelativeLayout);
        getActivity().registerForContextMenu(addRelativeLayout);
        popupMenu.getMenuInflater().inflate(R.menu.activity_main_drawer, popupMenu.getMenu());
        popupMenu.setGravity(Gravity.CENTER_HORIZONTAL);

        //使用反射，强制显示菜单图标
        try {
            Field field = popupMenu.getClass().getDeclaredField("mPopup");
            field.setAccessible(true);
            MenuPopupHelper mHelper = (MenuPopupHelper) field.get(popupMenu);
            mHelper.setForceShowIcon(true);
            mHelper.setGravity(Gravity.CENTER);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = null;
                switch (item.getItemId())
                {
                    case R.id.project_new_project:
                        intent = new Intent(getActivity(), NewProjectActivity.class);
                        break;
                    case R.id.project_new_task:
                        intent=new Intent(getActivity(), TaskEditActivity.class);

                        break;
                }
                if (intent == null)
                    throw new IllegalArgumentException("Menu Intent null");
                startActivity(intent);
                return true;
            }
        });

        return view;
    }

    @OnClick(R.id.bt_search)
    public void searchListener(View view)
    {
        Intent intent = new Intent(getActivity(), ApplyProjectActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.bt_add)
    public void addListener(View view)
    {
        popupMenu.show();
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
