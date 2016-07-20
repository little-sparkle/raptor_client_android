package com.littlesparkle.growler.raptor.boradcastreceiver;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.littlesparkle.growler.library.preference.PrefHelper;
import com.littlesparkle.growler.raptor.ui.activity.MainActivity;

import java.io.File;

public class DownloadReceiver extends BroadcastReceiver {
    public DownloadReceiver() {
    }


    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
            PrefHelper.setBoolean(context, "isDownloading", false);
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            Intent mIntent = new Intent(Intent.ACTION_VIEW);
            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mIntent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/download/raptor.apk")),
                    "application/vnd.android.package-archive");
            context.startActivity(mIntent);
        } else if (intent.getAction().equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)) {

        }
    }


}
