package com.example.a29149.ecollaboration.model.project.manage.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a29149.ecollaboration.R;
import com.example.a29149.ecollaboration.model.project.manage.fragment.NewSchoolProjectFragment;
import com.example.a29149.ecollaboration.util.AnnotationUtil;
import com.example.a29149.ecollaboration.util.annotation.OnClick;
import com.example.a29149.ecollaboration.util.annotation.ViewInject;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;

public class NewProjectActivity extends AppCompatActivity{

    @ViewInject(R.id.title)
    private TextView title;

    @ViewInject(R.id.insert_project_button)
    private TextView saveBt;

    @ViewInject(R.id.mask)
    private TextView maskLayout;

    @ViewInject(R.id.menu_item_container)
    private LinearLayout menuContainer;

    @ViewInject(R.id.new_school_project)
    private TextView schoolProjectBt;

    @ViewInject(R.id.new_self_project)
    private TextView selfProjectBt;

    private boolean isOpenMenu = false;
    private int mCurrentSelected = 0;//0:工程实践项目，1：兴趣项目
    private NewSchoolProjectFragment projectFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);
        AnnotationUtil.injectViews(this);
        AnnotationUtil.setClickListener(this);

        projectFragment = NewSchoolProjectFragment.newInstance();
        replaceFragment(projectFragment);
    }

    @OnClick(R.id.title)
    public void titleClickListener(View view)
    {
        if (isOpenMenu)
        {
            closeMenu();
        }
        else
        {
            openMenu();
        }
    }

    @OnClick(R.id.bt_return)
    public void cancelBtListener(View view)
    {
        this.finish();
    }

    @OnClick(R.id.commit)
    public void commitBtListener(View view)
    {
        projectFragment.startCommitProject();
    }

    @OnClick(R.id.insert_project_button)
    public void saveBtListener(View view)
    {
        projectFragment.startCommitProject();
    }

    @OnClick(R.id.mask)
    public void maskClickListener(View view)
    {
        closeMenu();
    }

    @OnClick(R.id.new_school_project)
    public void newSchoolProjectBtListener(View view)
    {
        if (mCurrentSelected == 0)
        {
            closeMenu();
        }
        else
        {
            //replaceFragment(NewSchoolProjectFragment.newInstance());

            title.setText("新建工程实践项目");
            mCurrentSelected = 0;
            closeMenu();
        }
        mCurrentSelected = 0;
    }

    @OnClick(R.id.new_self_project)
    public void newSelfProjectBtListener(View view)
    {
        if (mCurrentSelected == 1)
        {
            closeMenu();
        }
        else
        {
            //replaceFragment(NewSelfProjectFragment.newInstance());

            title.setText("新建兴趣项目");
            mCurrentSelected = 1;
            closeMenu();
        }
        mCurrentSelected = 1;

    }

    private void openMenu()
    {
        maskLayout.setVisibility(View.VISIBLE);
        menuContainer.setVisibility(View.INVISIBLE);

        menuContainer.postDelayed(new Runnable() {
            @Override
            public void run() {
                menuContainer.setVisibility(View.VISIBLE);
                ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(menuContainer, "translationY", -menuContainer.getHeight(), 0);
                objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                objectAnimator.start();
            }
        }, 0);
        isOpenMenu = true;
    }

    private void closeMenu()
    {
        maskLayout.setVisibility(View.GONE);
        ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(menuContainer, "translationY", 0, -menuContainer.getHeight());
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.start();
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                menuContainer.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        isOpenMenu = false;
    }

    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.project_container, fragment);
        transaction.commit();
    }

}
