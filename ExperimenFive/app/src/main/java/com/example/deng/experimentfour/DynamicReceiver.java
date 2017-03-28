package com.example.deng.experimentfour;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by deng on 2016/10/19.
 */
public class DynamicReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // 显示到通知栏
        final Bitmap dynamicIcon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.dynamic);

        NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle("动态广播").
                setTicker(intent.getStringExtra("value")).
                setContentText(intent.getStringExtra("value")).
                setPriority(Notification.PRIORITY_DEFAULT).
                setLargeIcon(dynamicIcon).
                setSmallIcon(R.mipmap.dynamic).
                setWhen(System.currentTimeMillis()).
                setAutoCancel(true);
        Intent mintent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mintent, 0);
        builder.setContentIntent(pendingIntent);
        Notification notify = builder.build();
        manager.notify(0, notify);



    }
}
















