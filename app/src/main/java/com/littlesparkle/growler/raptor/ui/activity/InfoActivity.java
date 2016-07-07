package com.littlesparkle.growler.raptor.ui.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.littlesparkle.growler.library.activity.BaseActivity;
import com.littlesparkle.growler.raptor.R;
import com.littlesparkle.growler.raptor.listener.OnPopwindowClickListener;
import com.littlesparkle.growler.raptor.ui.views.HeadPopWindow;

import java.io.File;


/**
 * Created by dell on 2016/7/4.
 */
public class InfoActivity extends BaseActivity implements View.OnClickListener, OnPopwindowClickListener {

    private ImageView headImageView = null;
    private HeadPopWindow mHeadPopWindow = null;
    private RelativeLayout driverLayout = null;
    private TextView mTextViewForPop = null;
    private File mHeadPhoto = null;

    //拍照
    public static final int REQUEST_CODE_TAKE_PHOTO = 10;
    //截取
    public static final int REQUEST_CODE_CLIP_PHOTO = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setActivityContentView() {
        return R.layout.activity_info;
    }

    @Override
    public void initData() {
        String sdPath = Environment.getExternalStorageDirectory()
                .getAbsolutePath();
        mHeadPhoto = new File(sdPath, System.currentTimeMillis() + ".tmp");
    }

    @Override
    public void initView() {
        driverLayout = (RelativeLayout) this.findViewById(R.id.setting_driver);
        mTextViewForPop = (TextView) this.findViewById(R.id.text_for_pop);
        headImageView = (ImageView) this.findViewById(R.id.imgv_header_setting);
        headImageView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgv_header_setting:
                Toast.makeText(mBaseActivity, "head", Toast.LENGTH_SHORT).show();
                mHeadPopWindow = new HeadPopWindow(InfoActivity.this, driverLayout);
                mHeadPopWindow.setWidth(driverLayout.getWidth());
                mHeadPopWindow.setHeight(600);
                mHeadPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {

                    }
                });
                mHeadPopWindow.showAsDropDown(mTextViewForPop);
                mHeadPopWindow.setOnPopwindowClickListener(this);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_TAKE_PHOTO:
                onTakePhotoFinished(resultCode, data);
                break;
            case REQUEST_CODE_CLIP_PHOTO:
                onClipPhotoFinished(requestCode, resultCode, data);
                break;

        }
    }

    //拍照返回
    private void onTakePhotoFinished(int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "take photo canceled", Toast.LENGTH_SHORT)
                    .show();
            return;
        } else if (resultCode != RESULT_OK) {
            Toast.makeText(this, "take photo failed", Toast.LENGTH_SHORT)
                    .show();
        } else {
            clipPhoto(Uri.fromFile(mHeadPhoto));
        }
    }

    //裁剪图片方法
    private void clipPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 720);
        intent.putExtra("outputY", 720);
        //把最后裁剪的图片保存在文件里
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(mHeadPhoto.getAbsoluteFile() + "tmp")));
        startActivityForResult(intent, REQUEST_CODE_CLIP_PHOTO);
    }

    //裁剪返回
    private void onClipPhotoFinished(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "clip photo canceled", Toast.LENGTH_SHORT)
                    .show();
            return;
        } else if (resultCode != RESULT_OK) {
            Toast.makeText(this, "take photo failed", Toast.LENGTH_SHORT)
                    .show();
        } else {

            Bitmap bm = BitmapFactory.decodeFile(mHeadPhoto.getAbsolutePath()
                    + "tmp");
            headImageView.setImageBitmap(bm);
        }


    }

    //判断是否有支持相机
    private boolean hasCarema() {
        PackageManager pm = this.getPackageManager();
        if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)
                && !pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)) {
            Toast.makeText(this, "no camera found", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    //拍照方法
    private void takePhoto() {
        if (!hasCarema()) {
            return;
        }
//        生成一个文件，存储我们将来拍照的照片
//        String sdPath = Environment.getExternalStorageDirectory()
//                .getAbsolutePath();
//        mHeadPhoto = new File(sdPath, System.currentTimeMillis() + ".tmp");
        Uri uri = Uri.fromFile(mHeadPhoto);

        //跳转到我们系统的相机界面
        Intent newIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //最终把拍摄的相片，输出到uri指向
        newIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        this.startActivityForResult(newIntent, REQUEST_CODE_TAKE_PHOTO);

    }

    @Override
    public void getPhotoByCamera() {
        takePhoto();
    }

    @Override
    public void getPhotoByAlbums() {

    }
}
