package com.example.deng.experimentone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by deng on 16/9/21.
 */
public class MainActivity extends AppCompatActivity{
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Boolean isSetPwd = false;
    private Button okButton;
    private Button clearButton;
    private EditText pwdText;
    private EditText confText;
    private String password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        findView();
        bind();

        sharedPreferences = getSharedPreferences("configure", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        password = sharedPreferences.getString("password", null);
        if (password != null) {
            // 已经设置过密码
            isSetPwd = true;
            pwdText.setHint("Password");
            confText.setVisibility(View.GONE);
        } else  {
            isSetPwd = false;
        }

    }

    void bind() {
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSetPwd == true) {
                    if (pwdText.getText().toString().equals("")) {
                        Toast.makeText(MainActivity.this , "Password cannot be empty", Toast.LENGTH_SHORT).show();
                        return;
                    }
                        // 设置过密码
                    if (password.equals(pwdText.getText().toString())) {
                        // 跳转
                        Intent intent = new Intent(MainActivity.this, FileEditorActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this , "Invalid Password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // 没设置过密码
                    if (pwdText.getText().toString().equals("")||pwdText.getText().toString().equals("")) {
                        Toast.makeText(MainActivity.this , "Password cannot be empty", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String pwd = pwdText.getText().toString();
                    if (pwd.equals(confText.getText().toString())) {
                        editor.putString("password", pwd);
                        editor.commit();
                        Intent intent = new Intent(MainActivity.this, FileEditorActivity.class);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(MainActivity.this , "Password Mismatch", Toast.LENGTH_SHORT).show();
                }
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwdText.setText("");
                confText.setText("");
            }
        });
    }

    void findView() {
        okButton = (Button)findViewById(R.id.ok);
        clearButton = (Button)findViewById(R.id.passwordClear);
        pwdText = (EditText)findViewById(R.id.password);
        confText = (EditText)findViewById(R.id.confirmPassword);
    }

}
