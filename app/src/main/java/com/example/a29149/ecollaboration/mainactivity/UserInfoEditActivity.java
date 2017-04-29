package com.example.a29149.ecollaboration.mainactivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.a29149.ecollaboration.R;
import com.example.a29149.ecollaboration.httprequest.asynctask.editinfo.TransForEditPhoto;
import com.example.a29149.ecollaboration.util.ActivityCode;
import com.example.a29149.ecollaboration.util.AnnotationUtil;
import com.example.a29149.ecollaboration.util.ImageService;
import com.example.a29149.ecollaboration.util.annotation.OnClick;
import com.example.a29149.ecollaboration.util.annotation.ViewInject;
import com.example.a29149.ecollaboration.util.GlobalUtil;

import java.io.IOException;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class UserInfoEditActivity extends AppCompatActivity {

    @ViewInject(R.id.user_top)
    private LinearLayout mPhotoPanel;

    @ViewInject(R.id.user_email)
    private LinearLayout mEmailPanel;

    @ViewInject(R.id.user_mobilePhone)
    private LinearLayout mNumbersPanel;

    @ViewInject(R.id.user_sex)
    private LinearLayout mSexPanel;

    @ViewInject(R.id.user_school)
    private LinearLayout mSchoolPanel;

    @ViewInject(R.id.user_activeBefore)
    private LinearLayout mAchieveBeforePanel;

    @ViewInject(R.id.user_top_edit)
    private ImageView mPhotoEdit;

    @ViewInject(R.id.user_email_edit)
    private EditText mEmailEdit;

    @ViewInject(R.id.user_mobilePhone_edit)
    private EditText mNumbersEdit;

    @ViewInject(R.id.user_sex_edit)
    private EditText mSexEdit;

    @ViewInject(R.id.user_school_edit)
    private EditText mSchoolEdit;

    @ViewInject(R.id.user_activeBefore_edit)
    private EditText mAchieveEdit;

    private CharSequence[] its = {"拍照", "从相册选择"};
    private Bitmap photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_edit);
        AnnotationUtil.injectViews(this);
        AnnotationUtil.setClickListener(this);

        EventBus.getDefault().register(this);
        initData();
    }

    /**
     * 数据初始化
     */
    private void initData() {
        if (GlobalUtil.getInstance().getmHead() != null) {
            mPhotoEdit.setImageBitmap(GlobalUtil.getInstance().getmHead());
        } else {
            mPhotoEdit.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.photoerror));
        }
        if (GlobalUtil.getInstance().getUserDTO() != null) {
            mEmailEdit.setHint(GlobalUtil.getInstance().getUserDTO().getSchoolId());
            mNumbersEdit.setHint(GlobalUtil.getInstance().getUserDTO().getPhoneNumber());
            mSexEdit.setHint(GlobalUtil.getInstance().getUserDTO().getSchoolId());
            mSchoolEdit.setHint(GlobalUtil.getInstance().getUserDTO().getSex() == 0 ? "女" : "男");
            mAchieveEdit.setHint(GlobalUtil.getInstance().getUserDTO().getActiveBefore());
        }
    }

    @OnClick(R.id.user_top)
    public void setPhotoListener(View view) {
        new AlertDialog.Builder(UserInfoEditActivity.this)
                .setTitle("更换头像")
                .setItems(its, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        switch (which) {
                            case 0://拍照//图片名称 时间命名
                                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent, ActivityCode.SELECT_CAMERA);
                                break;
                            case 1://从相册选择
                                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);//ACTION_OPEN_DOCUMENT
                                intent.setType("image/*");
                                startActivityForResult(intent, ActivityCode.SELECT_PIC);
                                break;
                        }
                    }
                }).create().show();
    }

    @OnClick(R.id.bt_save)
    public void setSaveListener(View view) {
        String content = null;


        byte[] bitmapBytes = ImageService.Bitmap2Bytes(photo);
        content = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);

        TransForEditPhoto editPhoto = new TransForEditPhoto();
        editPhoto.execute("TransForEditPhoto", content);

        GlobalUtil.getInstance().setmHead(photo);

        this.finish();
    }

    @OnClick(R.id.bt_return)
    public void setReturnListener(View view) {
        this.onBackPressed();
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEditResult(EditInfoEvent editInfoEvent)
    {
        if (editInfoEvent.isSuccess())
            this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;
        switch (requestCode) {
            case ActivityCode.SELECT_PIC://相册
                try {
                    photo = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    photo = BitmapFactory.decodeStream(getContentResolver().openInputStream(data.getData()));
                    //显得到bitmap图片

                    mPhotoEdit.setImageBitmap(photo);
                    Log.e("TAG-->Error", photo.getPixel(20, 20) + " ");
                } catch (IOException e) {
                    Log.e("TAG-->Error", e.toString());
                }
                break;
            case ActivityCode.SELECT_CAMERA://相机

                Bundle bundle = data.getExtras();
                photo = (Bitmap) bundle.get("data");
                mPhotoEdit.setImageBitmap(photo);
                break;
            default:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
