package com.example.deng.mapsensor;

import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;

public class MainActivity extends AppCompatActivity {
    private TextureMapView mMapView;
    private ToggleButton toggleButton;

    private SensorManager sensorManager;
    private LocationManager locationManager;

    private Sensor magenticSensor = null;
    private Sensor accelerometerSensor = null;

    private Location lastLocation = new Location(LocationManager.NETWORK_PROVIDER);
    private float curRotation = 20;

    private BaiduMap mBaiduMap;
    private BitmapDescriptor currentMark;

    private String provider;

    private SensorEventListener msensorEventListener = new SensorEventListener() {
        // 传感器事件回调
        float[] accValues = null;
        float[] magValues = null;
        long lastShakeTime = 0;
        @Override
        public void onSensorChanged(SensorEvent event) {
            switch (event.sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                    accValues = event.values;

                    float x = (float) Math.abs(accValues[0]);
                    float y = (float) Math.abs(accValues[1]);
                    float z = (float) Math.abs(accValues[2]);

                    // 摇一摇功能
                    if (x > 18.5 || y > 18.5 || z > 28.5) {
                        shake();
                    }

                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    magValues = event.values;
                    break;
                default:
                    Log.e("传感器回调函数default", "onSensorChanged: " + event.toString() );
                    break;
            }
            if (accValues == null || magValues == null) return;

            float[] R = new float[9];
            float[] values = new float[3];
            SensorManager.getRotationMatrix(R, null, accValues, magValues);
            SensorManager.getOrientation(R, values);

            curRotation = (float) Math.toDegrees(values[0]);
            setMyCurLocation();
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            Log.e("传感器事件回调", "onAccuracyChanged: sensor=" + sensor.getType() + "accuracy=" + accuracy);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        findView();
        bindView();

        initMap();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(msensorEventListener, magenticSensor, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(msensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);
        getBestProvider();
        locationManager.requestLocationUpdates(provider, 1000, 1, locationListener);
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(msensorEventListener);
        locationManager.removeUpdates(locationListener);
        mMapView.onPause();

    }

    private void findView() {
        // 找到了所有控件和传感器管理类,磁场传感器,加速度传感器,位置管理类
        mMapView = (TextureMapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        toggleButton = (ToggleButton) findViewById(R.id.tb_center);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        magenticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    }


    private void bindView() {
        // 这里绑定 tuggleButton 更改状态 和 mMapView 滑动 事件

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    MyLocationConfiguration config = new MyLocationConfiguration(
                            MyLocationConfiguration.LocationMode.FOLLOWING, true, currentMark);
                    mBaiduMap.setMyLocationConfigeration(config);
                    toggleButton.setBackgroundResource(R.mipmap.center_on);
                    mapTocenter();

                } else {
                    MyLocationConfiguration config = new MyLocationConfiguration(
                            MyLocationConfiguration.LocationMode.NORMAL, true, currentMark);
                    mBaiduMap.setMyLocationConfigeration(config);
                    toggleButton.setBackgroundResource(R.mipmap.center_off);
                }
            }
        });

        mBaiduMap.setOnMapTouchListener(new BaiduMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                toggleButton.setChecked(false);
            }
        });
    }



    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.e("系统定位信息", "onLocationChanged: " + location);
            if (location == null) return;
            lastLocation = location;
            setMyCurLocation();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.e("提供商发生变化", "onProviderEnabled: provider=" + provider
                    + "status=" + status);

        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.e("可用提供商", "onProviderEnabled: " + provider);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.e("不可用提供商", "onProviderEnabled: " + provider);

        }
    };

    private void initMap() {
        // 开启定位图层, 初始化位置, 设置位置, 移到中心, 自定义图标

        mBaiduMap.setMyLocationEnabled(true);
        lastLocation.setLatitude(23.117055306224895);
        lastLocation.setLongitude(113.2759952545166);
        setMyCurLocation();
        mapTocenter();

        currentMark = BitmapDescriptorFactory.fromResource(R.mipmap.pointer);
        MyLocationConfiguration config = new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.FOLLOWING, true, currentMark);
        mBaiduMap.setMyLocationConfigeration(config);
    }

    private void getBestProvider() {
        // 找到合适的定位方式
        // TODO: 2016/12/3 选择合适的位置提供方式
        boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (isGpsEnabled) provider = LocationManager.GPS_PROVIDER;
        else if (isNetEnabled) provider = LocationManager.NETWORK_PROVIDER;
        else {
            provider = LocationManager.PASSIVE_PROVIDER;
            Toast.makeText(this, "没有可用的定位方式,查看是否打开定位", Toast.LENGTH_SHORT).show();
//            throw new NullPointerException("没有可用的网络提供商");
        }
        Log.e("provider情况", "getBestProvider: provider=" + provider);

    }

    private void setMyCurLocation() {
        // 更新手机位置和朝向
        LatLng latLng = conver2Lat(lastLocation);
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(lastLocation.getAccuracy())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(curRotation)
                .latitude(latLng.latitude)
                .longitude(latLng.longitude)
                .build();
        mBaiduMap.setMyLocationData(locData);
    }

    private LatLng conver2Lat(Location location) {
        // location To LatLen
        CoordinateConverter converter  = new CoordinateConverter();
        converter.from(CoordinateConverter.CoordType.GPS);
        converter.coord(new LatLng(location.getLatitude(), location.getLongitude()));
        return converter.convert();

    }

    private void mapTocenter() {
        // 将最后一次定位点移动到中心
        MapStatus mapStatus = new MapStatus.Builder().target(conver2Lat(lastLocation)).build();
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
        mBaiduMap.setMapStatus(mapStatusUpdate);
    }

    private boolean shakeAble = true;
    void shake() {
        if (!shakeAble) return;
        shakeAble = false;
        LayoutInflater inflater = LayoutInflater.from(this);
        final View view = inflater.inflate(R.layout.dialog_layout, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(this)
                                    .setView(view)
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            shakeAble = true;
                                        }
                                    });
        alert.create();
        alert.show();
    }
}