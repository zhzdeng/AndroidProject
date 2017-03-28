package com.example.deng.experimentfour;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * Created by deng on 2016/10/19.
 */
public class StaticReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // 接受到广播, 需要显示到状态栏上, 发出通知
        Fruit fruit = (Fruit) intent.getParcelableExtra("fruit");
        if (fruit == null) Log.e("StaticReceiver", "intent.getParcelableExtra(\"fruit\") == null");

        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), fruit.getPictureId());

        NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle("静态广播").
                setTicker(fruit.getName()).
                setContentText(fruit.getName()).
                setPriority(Notification.PRIORITY_DEFAULT).
                setLargeIcon(icon).
                setSmallIcon(fruit.getPictureId()).
                setWhen(System.currentTimeMillis()).
                setAutoCancel(true);
        Intent mintent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mintent, 0);
        builder.setContentIntent(pendingIntent);
        Notification notify = builder.build();
        manager.notify(0, notify);
    }
}
