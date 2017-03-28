package com.example.deng.experimentfour;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.security.spec.ECField;

/**
 * Created by deng on 2016/10/19.
 */
public class DynamicActivity extends AppCompatActivity {
    private static final String FILTER = "dynamicBroadcast";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dynamic_broadcast);

        final boolean[] isRegister = {false};
        Button stateButton = (Button)findViewById(R.id.dybroadcast);
        final Button sent = (Button)findViewById(R.id.sent);
        final DynamicReceiver dynamicReceiver = new DynamicReceiver();
        final WidgetProvider widgetProvider = new WidgetProvider();
        stateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button)v;
                if (isRegister[0] == false) {
                    // 注册动态广播
                    IntentFilter filter = new IntentFilter();
                    filter.addAction(FILTER);
                    registerReceiver(dynamicReceiver, filter);
                    registerReceiver(widgetProvider, filter);
                    button.setText("Unregister Broadcast");
                } else {
                    // 注销动态广播
                    unregisterReceiver(dynamicReceiver);
                    unregisterReceiver(widgetProvider);
                    button.setText("Register Broadcast");
                }
                isRegister[0] = !isRegister[0];
            }
        });


        final EditText editText = (EditText)findViewById(R.id.editText);
        sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRegister[0] == false) return;

                // 广播给 DynamicReceiver
                Intent intent = new Intent(FILTER);
                intent.putExtra("value", editText.getText().toString());

                // 广播给 WidgetProvider
                Bundle bundle = new Bundle();
                Fruit fruit = new Fruit(editText.getText().toString(), R.mipmap.dynamic);
                bundle.putParcelable("fruit", fruit);
                intent.putExtras(bundle);

                sendBroadcast(intent);
            }
        });
    }

}





















