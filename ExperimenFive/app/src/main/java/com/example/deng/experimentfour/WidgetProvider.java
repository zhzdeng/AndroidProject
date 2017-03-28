package com.example.deng.experimentfour;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Created by deng on 2016/10/26.
 */
public class WidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        remoteViews.setOnClickPendingIntent(R.id.fruitPictureInWidget, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        Log.v("WidgetProvider", intent.getAction());

        if (intent.getAction().equals("staticBroadcast")||intent.getAction().equals("dynamicBroadcast")) {
            Fruit fruit = (Fruit) intent.getParcelableExtra("fruit");
            if (fruit == null) Log.e("WidgetProvider", "intent.getParcelableExtra(\"fruit\") == null");

            remoteViews.setImageViewResource(R.id.fruitPictureInWidget, fruit.getPictureId());
            remoteViews.setTextViewText(R.id.fruitNameInWidget, fruit.getName());

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName componentName = new ComponentName(context, WidgetProvider.class);
            appWidgetManager.updateAppWidget(componentName, remoteViews);
        }

    }
}
