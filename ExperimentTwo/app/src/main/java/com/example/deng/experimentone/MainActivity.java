package com.example.deng.experimentone;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

        final RadioButton student = (RadioButton)findViewById((R.id.student));
        final RadioButton teacher = (RadioButton)findViewById((R.id.teacher));
        final RadioButton party = (RadioButton)findViewById((R.id.party));
        final RadioButton manager = (RadioButton)findViewById((R.id.manager));

        // RadioGroup切换事件
        final RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String massage = "error!";
                if (checkedId == student.getId()) massage = "学生身份被选中";
                else if (checkedId == teacher.getId()) massage = "教师身份被选中";
                else if (checkedId == party.getId()) massage = "社团身份被选中";
                else if (checkedId == manager.getId()) massage = "管理者身份被选中";
                Toast.makeText(MainActivity.this , massage, Toast.LENGTH_SHORT).show();
            }
        });


        // 注册交互实现
        Button signup = (Button)findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String massage = "error";
                int select = radioGroup.getCheckedRadioButtonId();
                if (select == student.getId()) massage = "学生身份注册功能尚未开启";
                else if (select == teacher.getId()) massage = "教师身份注册功能尚未开启";
                else if (select == party.getId()) massage = "社团身份注册功能尚未开启";
                else if (select == manager.getId()) massage = "管理者身份注册功能尚未开启";
                Toast.makeText(MainActivity.this, massage, Toast.LENGTH_SHORT).show();
            }
        });

        // 登录交互实现
        final Dialog alertSuccess = new AlertDialog.Builder(this).setTitle("提示")
                .setMessage("登陆成功")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "对话框“确定”按钮被点击", Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "对话框“取消”按钮被点击", Toast.LENGTH_SHORT).show();
                    }
                })
                .create();
        final Dialog alertflase = new AlertDialog.Builder(this).setTitle("提示")
                .setMessage("登陆失败")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "对话框“确定”按钮被点击", Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "对话框“取消”按钮被点击", Toast.LENGTH_SHORT).show();
                    }
                })
                .create();

        final EditText username = (EditText)findViewById(R.id.username);
        final EditText password = (EditText)findViewById(R.id.password);
        Button login = (Button)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.print("username: " + username.getText());
                System.out.print("password: " + password.getText());
                if (username.getText().toString().equals(""))
                    Toast.makeText(MainActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                else if (password.getText().toString().equals(""))
                    Toast.makeText(MainActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                else if (username.getText().toString().equals("Android") && password.getText().toString().equals("123456"))
                    alertSuccess.show();
                else
                    alertflase.show();
            }
        });
    }

}
