package com.example.deng.experimenteight;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.deng.experimenteight.dao.MySQListOpenHelper;
import com.example.deng.experimenteight.dao.SqliteHelper;

/**
 * Created by deng on 2016/11/16.
 */
public class MainActivity extends AppCompatActivity {
    private SqliteHelper sqliteHelper;
    private Button addButton;
    private ListView listView;
    private MyAdapter myAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        sqliteHelper = new SqliteHelper(new MySQListOpenHelper(this));

        findView();

        myAdapter = new MyAdapter(this, sqliteHelper.queryAllData());
        listView.setAdapter(myAdapter);

        bind();
    }

    void findView() {
        addButton = (Button) findViewById(R.id.mainAdd);
        listView = (ListView)findViewById(R.id.mainListView);
    }

    void bind() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, final long id) {
                Dialog deleteConfirmnew = new AlertDialog.Builder(MainActivity.this).setMessage("是否删除")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sqliteHelper.delete(myAdapter.getList().get((int) id).getId());
                                myAdapter.remove((int) id);
                            }
                        })
                        .setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        })
                        .create();
                deleteConfirmnew.show();
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO: 2016/11/16 弹框出来是否需要修改信息
                showAlertDialog(MainActivity.this, (int) id);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateActivity.class);
                startActivityForResult(intent, 0);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == CreateActivity.RESULT_OK) {
            myAdapter = new MyAdapter(this, sqliteHelper.queryAllData());
            listView.setAdapter(myAdapter);
        }
    }

    void showAlertDialog(Context context, final int id) {
        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.dialog_layout, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(context)
                                    .setView(view)
                                    .setPositiveButton("保存修改", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // TODO: 2016/11/17 保存数据并update
                                            EditText birthEdit = (EditText)view.findViewById(R.id.dialogBirth);
                                            EditText giftEdit = (EditText)view.findViewById(R.id.dialogGift);
                                            myAdapter.updateBirth(id, birthEdit.getText().toString());
                                            myAdapter.updateGift(id, giftEdit.getText().toString());
                                            sqliteHelper.update(myAdapter.getItem(id));
                                        }
                                    })
                                    .setNegativeButton("放弃修改", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            return;
                                        }
                                    });
        TextView nameText = (TextView)view.findViewById(R.id.dialogName);
        TextView phoneText = (TextView)view.findViewById(R.id.phone);
        EditText birthText = (EditText)view.findViewById(R.id.dialogBirth);
        EditText giftText = (EditText)view.findViewById(R.id.dialogGift);

        birthText.setText(myAdapter.getItem(id).getBirth());
        giftText.setText(myAdapter.getItem(id).getGift());
        nameText.setText(myAdapter.getItem(id).getName());
        phoneText.setText(getPhoneByName(myAdapter.getItem(id).getName()));
        alert.create();
        alert.show();
    }

    String getPhoneByName(String name) {
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + "=\"" + name + "\"", null, null);
        if (cursor == null || !(cursor.getCount() > 0)) return "";

        String phone = new String();
        cursor.moveToFirst();
        do {
            phone += cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)) + " ";
        } while (cursor.moveToNext());
        return phone;
    }
}
