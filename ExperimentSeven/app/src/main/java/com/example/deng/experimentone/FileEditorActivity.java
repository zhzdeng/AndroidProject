package com.example.deng.experimentone;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by deng on 2016/11/9.
 */
public class FileEditorActivity extends AppCompatActivity {
    private static final String FILE_NAME = "editdata.txt";

    private EditText editText;
    private Button save;
    private Button load;
    private Button clear;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_editor_layout);

        findView();
        bind();
    }

    void bind() {
        save.setOnClickListener(new View.OnClickListener() {
            // 保存内容
            @Override
            public void onClick(View v) {
                try (FileOutputStream fileOutputStream = openFileOutput(FILE_NAME, MODE_PRIVATE)) {
                    fileOutputStream.write(editText.getText().toString().getBytes());
                    Toast.makeText(FileEditorActivity.this , "Save successfully", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(FileEditorActivity.this , "Save unsuccessfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try (FileInputStream fileInputStream = openFileInput(FILE_NAME)) {
                    byte[] contents = new byte[fileInputStream.available()];
                    fileInputStream.read(contents);
                    editText.setText(new String(contents));
                    Toast.makeText(FileEditorActivity.this , "Load successfully", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(FileEditorActivity.this , "Fail to load file", Toast.LENGTH_SHORT).show();
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });
    }

    void findView() {
        editText = (EditText)findViewById(R.id.edit);
        save = (Button)findViewById(R.id.save);
        load = (Button)findViewById(R.id.load);
        clear = (Button)findViewById(R.id.textClear);
    }
}
