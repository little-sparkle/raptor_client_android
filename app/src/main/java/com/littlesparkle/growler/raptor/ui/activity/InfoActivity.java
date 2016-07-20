package com.littlesparkle.growler.raptor.ui.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.littlesparkle.growler.library.activity.BaseActivity;
import com.littlesparkle.growler.library.activity.BaseFragmentActivity;
import com.littlesparkle.growler.library.activity.BaseTitleBarActivity;
import com.littlesparkle.growler.raptor.R;
import com.littlesparkle.growler.raptor.listener.OnPopwindowClickListener;
import com.littlesparkle.growler.raptor.ui.views.HeadPopWindow;
import com.littlesparkle.growler.raptor.utils.DensityUtils;
import com.littlesparkle.growler.raptor.utils.SaveBMUtil;

import java.io.File;
import java.io.FileNotFoundException;


/**
 * Created by dell on 2016/7/4.
 */
public class InfoActivity extends BaseTitleBarActivity implements View.OnClickListener, OnPopwindowClickListener {

    private ImageView headImageView = null;
    private HeadPopWindow mHeadPopWindow = null;
    private RelativeLayout driverLayout = null;
    private TextView mTextViewForPop = null;
    private File mHeadPhoto = null;
    private Bitmap bmp = null;


    //拍照返回
    public static final int REQUEST_CODE_TAKE_PHOTO = 10;
    //截取返回
    public static final int REQUEST_CODE_CLIP_PHOTO = 20;

    private static final int REQUEST_CODE_GALLARY_CROP = 120;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getLayoutResId() {
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
        super.initView();
        driverLayout = (RelativeLayout) findViewById(R.id.setting_driver);
        mTextViewForPop = (TextView) findViewById(R.id.text_for_pop);
        headImageView = (ImageView) findViewById(R.id.imgv_header_setting);
        headImageView.setOnClickListener(this);
    }

    @Override
    protected int getTitleResourceId() {
        return R.string.title_information;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgv_header_setting:
                Toast.makeText(this, "head", Toast.LENGTH_SHORT).show();
                mHeadPopWindow = new HeadPopWindow(InfoActivity.this, driverLayout);
                mHeadPopWindow.setWidth(driverLayout.getWidth());
                mHeadPopWindow.setHeight(DensityUtils.dp2px(this, 160));
                mHeadPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        backgroundAlpha(1.0f);
                    }
                });
                backgroundAlpha(0.7f);
                mHeadPopWindow.showAsDropDown(mTextViewForPop);
                mHeadPopWindow.setOnPopwindowClickListener(this);
                break;

        }
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_TAKE_PHOTO) {
            onTakePhotoFinished(resultCode, data);
        } else if (requestCode == REQUEST_CODE_CLIP_PHOTO) {
            onClipPhotoFinished(requestCode, resultCode, data);
        } else if (requestCode == REQUEST_CODE_GALLARY_CROP) {

            try {
                Uri uri = data.getData();
                ContentResolver cr = getContentResolver();
                bmp = BitmapFactory.decodeStream(cr.openInputStream(uri));
                mHeadPhoto = SaveBMUtil.saveMyBitmap(bmp, "tx" + System.currentTimeMillis());
                clipPhoto(Uri.fromFile(mHeadPhoto));
            } catch (Exception e) {
                e.printStackTrace();
            }
            //保存图片到本地，随后准备上传


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
        PackageManager pm = getPackageManager();
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
        startActivityForResult(newIntent, REQUEST_CODE_TAKE_PHOTO);

    }

    @Override
    public void getPhotoByCamera() {
        takePhoto();
    }

    @Override
    public void getPhotoByAlbums() {
        chooseFormAlbums();

    }

    public void chooseFormAlbums() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_GALLARY_CROP);
    }
}
