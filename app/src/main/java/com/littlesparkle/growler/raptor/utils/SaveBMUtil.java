package com.littlesparkle.growler.raptor.utils;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by SJ on 2015/12/20.
 */
public class SaveBMUtil {

    public static  File saveMyBitmap(Bitmap mBitmap, String bitName)  {

        String sdPath= Environment.getExternalStorageDirectory().getAbsolutePath();
        File f = new File(sdPath+ "/"+bitName + ".jpg");
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //把BitMap保存到文件中
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  f;
    }
}
