package com.example.nhdangdh.servicedemo;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

public class MyService extends Service {
    public MyService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        try {
            new DoBackgroundTask().execute(
                    new URL("https://yt3.ggpht.com/-tUnSh4hL1b0/AAAAAAAAAAI/AAAAAAAAAAA/AIy" +
                            "5-05CyFk/s900-c-k-no-mo-rj-c0xffffff/photo.jpg"),
                    new URL("https://www.android.com/static/2016/img/hero-carousel/android-nougat.png")
            );
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }

    private int DownloadFile(URL url) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 100;
    }

    private class DoBackgroundTask extends AsyncTask<URL, Integer, Long> {

        Activity superContext;

        @Override
        protected Long doInBackground(URL... params) {
            long total = 0;
            for (int i = 0; i < params.length; i++) {
                total += DownloadFile(params[i]);
                publishProgress((int) (((i + 1) / (float) params.length) * 100));
            }
            return total;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.d("Downloading files", String.valueOf(values[0]) + "% downloaded");
            Toast.makeText(getBaseContext(), String.valueOf(values[0]) + "% downloaded",
                    Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPostExecute(Long result) {
            Toast.makeText(getBaseContext(),"Downloaded " + result + " bytes",
                    Toast.LENGTH_LONG).show();
            stopSelf();
        }
    }
}
