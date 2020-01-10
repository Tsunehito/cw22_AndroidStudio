package com.example.slax.myapplicationexogallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.NetworkInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class ViewerActivity extends AppCompatActivity {

    Image image;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewer);

        image = getIntent().getParcelableExtra("imageParcel");
        imageView = findViewById(R.id.viewerImageView);
        imageView.setImageBitmap(image.getBitmap());

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        SupplicantState supplicantState = wifiManager.getConnectionInfo().getSupplicantState();

        if(WifiInfo.getDetailedStateOf(supplicantState) != NetworkInfo.DetailedState.DISCONNECTED){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    loadImage();
                }
            }).start();
        }
    }

    public void loadImage() {
        try {
            URL url = new URL(image.getLargeUrl());
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.setInstanceFollowRedirects(false);
            httpsURLConnection.setConnectTimeout(5000);
            httpsURLConnection.setReadTimeout(5000);

            InputStream inputStream = httpsURLConnection.getInputStream();
            final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            image.setBitmap(bitmap);

            imageView.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap(bitmap);
                }
            });

            inputStream.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
