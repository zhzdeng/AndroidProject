package com.example.deng.experimenteight;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.deng.experimenteight.dao.MySQListOpenHelper;
import com.example.deng.experimenteight.dao.SqliteHelper;
import com.example.deng.experimenteight.entity.BirthInfo;

/**
 * Created by deng on 2016/11/16.
 */
public class CreateActivity extends AppCompatActivity {
    static public final int RESULT_OK = 1;
    private EditText nameText;
    private EditText birthdayText;
    private EditText giftText;
    private Button createButton;
    private SqliteHelper sqliteHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.additem_layout);
        sqliteHelper = new SqliteHelper(new MySQListOpenHelper(this));

        findView();
        bind();
    }
    
    void findView() {
        nameText = (EditText)findViewById(R.id.addItemName);
        birthdayText = (EditText)findViewById(R.id.addItemBirthday);
        giftText = (EditText)findViewById(R.id.addItemGift);
        createButton = (Button)findViewById(R.id.addItemAdd);
    }
    
    void bind() {
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameText.getText().toString();
                String birthday = birthdayText.getText().toString();
                String gift = giftText.getText().toString();
                if (name.equals("")) {
                    Toast.makeText(CreateActivity.this, "名字为空, 请完善", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (sqliteHelper.queryNameRepeat(name)) {
                    Toast.makeText(CreateActivity.this, "名字重复啦, 请核查", Toast.LENGTH_SHORT).show();
                    return;
                }

                sqliteHelper.insert(new BirthInfo(name, birthday, gift));
                setResult(RESULT_OK, new Intent());
                finish();
            }
        });
    }
}
