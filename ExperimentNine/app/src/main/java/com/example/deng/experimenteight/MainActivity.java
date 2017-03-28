package com.example.deng.experimenteight;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deng.experimenteight.Adapter.ListViewAdapter;
import com.example.deng.experimenteight.Adapter.RecyclerAdapter;
import com.example.deng.experimenteight.entity.ErrorInfo;
import com.example.deng.experimenteight.entity.WeatherInfo;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by deng on 2016/11/16.
 */
public class MainActivity extends AppCompatActivity {
    static private final String url = "http://ws.webxml.com.cn/WebServices/WeatherWS.asmx/getWeather";

    private Button searchButton;
    private EditText searchEdit;
    private TextView cityName, timeUpdateText, temperatureText, humidityText, windText;
    private TextView tempRangeText, aqiText;
    private ListView listView;
    private RecyclerView recyclerView;
    private LinearLayout mainLayout;

    private static final int MIN_CLICK_TIME = 600;
    private long ctime = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        findView();
        bindView();
    }

    void findView() {
        searchButton = (Button)findViewById(R.id.searchButton);
        searchEdit = (EditText)findViewById(R.id.searchInput);

        cityName = (TextView)findViewById(R.id.cityText);
        timeUpdateText = (TextView)findViewById(R.id.timeText);
        temperatureText = (TextView)findViewById(R.id.tempText);
        humidityText = (TextView)findViewById(R.id.humidityText);
        windText = (TextView)findViewById(R.id.windText);
        tempRangeText = (TextView)findViewById(R.id.tempRangeText);
        aqiText = (TextView)findViewById(R.id.aqiText);

        listView = (ListView)findViewById(R.id.listView);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        mainLayout = (LinearLayout)findViewById(R.id.mainLayout);
    }

    @TargetApi(Build.VERSION_CODES.N)
    void bindView() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 发送请求 请求网页, 需要用到后台线程
                // 判断异常
                // 是否有网络连接
                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo == null || networkInfo.isConnected() == false) {
                    // 无网络连接
                    Toast.makeText(MainActivity.this, "当前没有可用网络", Toast.LENGTH_SHORT).show();
                    return;
                } else if (Calendar.getInstance().getTimeInMillis() - ctime < MIN_CLICK_TIME) {
                    Toast.makeText(MainActivity.this, "您的点击速度过快, 二次查询间隔<" + MIN_CLICK_TIME + "ms", Toast.LENGTH_SHORT).show();
                    return;
                }

                GetWeatherAsyncTask getWeatherAsyncTask = new GetWeatherAsyncTask();
                getWeatherAsyncTask.execute(searchEdit.getText().toString());

                ctime = Calendar.getInstance().getTimeInMillis();
                // Log.e("ctime", "bindView: ctime: " + ctime);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

        mainLayout.setAlpha(0);



    }

    public class GetWeatherAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            List<String> list = new ArrayList<String>();

            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = factory.newPullParser();
                parser.setInput(new StringReader(s));
                int eventType = parser.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            if ("string".equals(parser.getName())) {
                                list.add(parser.nextText());
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            break;
                        default:
                            break;
                    }
                    eventType = parser.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Log.e("string", "onPostExecute: "+ list.toString() );


            if (list.size() < 2) {
                // 认为是异常
                ErrorInfo errorInfo = new ErrorInfo(list.get(0));
                Toast.makeText(MainActivity.this, errorInfo.getErrorMessage() , Toast.LENGTH_SHORT).show();
            } else {
                WeatherInfo weatherInfo = null;
                try {
                    // 构造失败,就返回 没有数据 信息
                    weatherInfo = new WeatherInfo(list);
                } catch (Exception e) {
                    weatherInfo = new WeatherInfo(list.get(0));
                }

                // Log.e("TAG", "weatherInfo.toString(): " + weatherInfo.toString());

                ListViewAdapter listAdapter = new ListViewAdapter(MainActivity.this, weatherInfo.getSuggestList());
                RecyclerAdapter recyclerAdapter = new RecyclerAdapter(MainActivity.this, weatherInfo.getDayInfoList());
                listView.setAdapter(listAdapter);
                recyclerView.setAdapter(recyclerAdapter);

                cityName.setText(weatherInfo.getCity());
                timeUpdateText.setText(weatherInfo.getTime());
                temperatureText.setText(weatherInfo.getTemperature());
                humidityText.setText(weatherInfo.getHumidity());
                windText.setText(weatherInfo.getWind());
                tempRangeText.setText(weatherInfo.getTempRange());
                aqiText.setText(weatherInfo.getAqi());

                mainLayout.setAlpha(1);

            }
        }

        @Override
        protected String doInBackground(String... params) {
            // 工作线程
            HttpURLConnection connection = null;
            String result = null;
            try {
                connection = (HttpURLConnection)((new URL(url)).openConnection());
                connection.setRequestMethod("POST");
                connection.setReadTimeout(8000);
                connection.setConnectTimeout(8000);

                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                String request = URLEncoder.encode(params[0], "utf-8");
//                out.writeBytes("theCityCode=" + request + "&theUserID=47c4e96882f9481a80160d2790603b0d");
                out.writeBytes("theCityCode=" + request + "&theUserID=");
                InputStream in = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                result = response.toString();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (connection != null) connection.disconnect();
            }

            // Log.e("tag", "doInBackground: result: " + result);
            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

    }
}