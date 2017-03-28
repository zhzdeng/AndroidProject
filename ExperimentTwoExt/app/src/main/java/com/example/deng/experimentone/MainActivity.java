package com.example.deng.experimentone;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by deng on 16/9/21.
 */
public class MainActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        final LinearLayout rootView = (LinearLayout)findViewById(R.id.rootView);

        final RadioButton student = (RadioButton)findViewById((R.id.student));
        final RadioButton teacher = (RadioButton)findViewById((R.id.teacher));
        final RadioButton party = (RadioButton)findViewById((R.id.party));
        final RadioButton manager = (RadioButton)findViewById((R.id.manager));

        // RadioGroup切换事件
        final RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String message = "error!";
                if (checkedId == student.getId()) message = "学生身份被选中";
                else if (checkedId == teacher.getId()) message = "教师身份被选中";
                else if (checkedId == party.getId()) message = "社团身份被选中";
                else if (checkedId == manager.getId()) message = "管理者身份被选中";
                Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT)
                        .setAction("按钮", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "Snackbar的按钮被点击", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setDuration(5000)
                        .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                        .show();
            }
        });


        // 注册交互实现
        Button signup = (Button)findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "error";
                int select = radioGroup.getCheckedRadioButtonId();
                if (select == student.getId()) message = "学生身份注册功能尚未开启";
                else if (select == teacher.getId()) message = "教师身份注册功能尚未开启";
                else if (select == party.getId()) message = "社团身份注册功能尚未开启";
                else if (select == manager.getId()) message = "管理者身份注册功能尚未开启";
                Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT)
                        .setAction("按钮", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "Snackbar的按钮被点击", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setDuration(5000)
                        .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                        .show();
            }
        });

        // 登录交互实现
        final TextInputLayout usernameText = (TextInputLayout)findViewById(R.id.more_username);
        final TextInputLayout passwordText = (TextInputLayout)findViewById(R.id.more_password);

        final EditText username = usernameText.getEditText();
        final EditText password = passwordText.getEditText();
        Button login = (Button)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().equals("")) {
                    usernameText.setErrorEnabled(true);
                    usernameText.setError("用户名不能为空");
                } else if (password.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    passwordText.setErrorEnabled(true);
                    passwordText.setError("密码不能为空");
                } else if (username.getText().toString().equals("Android") && password.getText().toString().equals("123456")) {
                    Snackbar.make(rootView, "登录成功", Snackbar.LENGTH_SHORT)
                            .setAction("按钮", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(MainActivity.this, "Snackbar的按钮被点击", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setDuration(5000)
                            .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                            .show();
                    usernameText.setErrorEnabled(false);
                    passwordText.setErrorEnabled(false);
                } else {
                    Snackbar.make(rootView, "登录失败", Snackbar.LENGTH_SHORT)
                            .setAction("按钮", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(MainActivity.this, "Snackbar的按钮被点击", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setDuration(5000)
                            .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                            .show();
                    usernameText.setErrorEnabled(false);
                    passwordText.setErrorEnabled(false);
                }
        }});

    }

}
