package com.example.mizuno.prog_gpsdb;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ProgGPSDBActivity extends ActionBarActivity implements LocationListener {

    //GPS
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prog_gpsdb);

        //LocationManagerインスタンスを取得
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_prog_gpsdb, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        if(locationManager != null){
            //位置情報の更新不要の場合は終了
            locationManager.removeUpdates(this);
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if(locationManager != null){
            //位置情報の更新を取得
            locationManager.requestLocationUpdates(
                    //permissionにACCESS_FINE_LOCATIONを追加
                    LocationManager.GPS_PROVIDER,
                    //NETWORK_PROVIDERを利用する場合はpermissionにACCESS_COARSE_LOCATIONを追加
                    //LocationManager.NETWORK_PROVIDER
                    //networkから取得する場合こちらに切り替える
                    10000,// 通知のための最小時間間隔（ミリ秒）この場合は10秒に１回
                    0,// 通知のための最小距離間隔（メートル）
                    this
            );
        }
        super.onResume();
    }

    @Override
    public void onLocationChanged(Location location) {

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        TextView tv_lat = (TextView)findViewById(R.id.latitude);
        tv_lat.setText("Latitude: " + latitude);

        //経度の表示
        TextView tv_lng = (TextView)findViewById(R.id.longitude);
        tv_lng.setText("Longitude:" + longitude);

        AsyncHttp post = new AsyncHttp("GPS", latitude, longitude);
        post.execute();

        //Accuracy(精度)
        Log.v("Accuracy", String.valueOf(location.getAccuracy()));
        //Altitude(標高)
        Log.v("Altitude", String.valueOf(location.getAltitude()));
        //Time(時間)
        Log.v("Time", String.valueOf(location.getTime()));
        //Speed(速度)
        Log.v("Speed", String.valueOf(location.getSpeed()));
        //Bearing(角度)出発点から目的地に引いた直線と北がなす角度・・・らしい
        Log.v("Bearing", String.valueOf(location.getBearing()));

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

        switch (i){
            case LocationProvider.AVAILABLE:
                Log.v("Status", "AVAILABLE");
                break;
            case LocationProvider.OUT_OF_SERVICE:
                Log.v("Status", "OUT_OF_SERVICE");
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                Log.v("Status", "TEMPORATIY_UNAVAILABLE");
                break;
        }


    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
