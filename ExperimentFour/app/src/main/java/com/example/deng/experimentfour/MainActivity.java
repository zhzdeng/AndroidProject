package com.example.deng.experimentfour;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by deng on 2016/10/19.
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        Button staticBoradcast = (Button)findViewById(R.id.staticBroadcast);
        Button dynamicBoradcast = (Button)findViewById(R.id.dynamicBroadcast);

        staticBoradcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳到 StaticActivity
                Intent intent = new Intent(MainActivity.this, StaticActivity.class);
                startActivity(intent);
            }
        });

        dynamicBoradcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳到 DynamicActivity
                Intent intent = new Intent(MainActivity.this, DynamicActivity.class);
                startActivity(intent);
            }
        });
    }
}
