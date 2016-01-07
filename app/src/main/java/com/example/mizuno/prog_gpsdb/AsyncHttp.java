package com.example.mizuno.prog_gpsdb;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by mizuno on 2015/12/11.
 */
public class AsyncHttp extends AsyncTask<String, Integer, Boolean> {
    //HttpURLConnectionを利用したPOSTプログラム
    HttpURLConnection urlConnection = null; //HTTPコネクション管理用
    Boolean flg = false;

    String name;
    double latitude, longitude;

    public AsyncHttp(String name, double latitude, double longitude){
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    //非同期処理ここから
    @Override
    protected Boolean doInBackground(String... contents) {
        //URLの設定（AndroidからホストPCのローカルに接続するには10.0.2.2を利用する
        String urlinput = "http://lab_kou.sangi01.net/seminar/coordinates/add";
        try {
            //HttpURLConnectionの利用手順
           /*
           1.url.openConnection()を呼び出し接続開始
           取得できる型はURLConnection型なので、キャストする必要あり
           2.ヘッダーの設定
           3.bodyを設定する場合はHttpURLConnection.setDoOutputにbodyが存在することを明示
           4.connect()で接続を確立する
           5.レスポンスをgetInputStream()で取得する
           * */
            URL url = new URL(urlinput);
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            //POST用パラメータ
            String postDataSample = "name="+name+"&latitude="+this.latitude+"&longitude="+this.longitude;

            //POSTパラメータ設定
            OutputStream out = urlConnection.getOutputStream();
            out.write(postDataSample.getBytes());
            out.flush();
            out.close();


            //レスポンスを受け取る
            //InputStream is = urlConnection.getInputStream();
            urlConnection.getInputStream();

            flg = true;


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return flg;
    }

}

