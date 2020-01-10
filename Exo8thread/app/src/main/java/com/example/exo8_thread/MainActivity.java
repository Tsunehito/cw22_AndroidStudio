package com.example.exo8_thread;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.tv.TvContract;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private ImageView imageData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageData = findViewById(R.id.imageData);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(R.string.title_pd);
        progressDialog.setMessage(getString(R.string.message_pd));
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);

        findViewById(R.id.ssThread).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempo();
            }
        });

        findViewById(R.id.acThread).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        tempo();
                    }
                }).start();
            }
        });

        findViewById(R.id.loadData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }

    protected void loadData(){
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png");
                    HttpURLConnection cx = (HttpURLConnection) url.openConnection();
                    InputStream is = cx.getInputStream();
                    final Bitmap bitmap = BitmapFactory.decodeStream(is);

                    // methode 1
                   /* runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageData.setImageBitmap(bitmap);
                        }
                    });*/

                   // methode 2
                   imageData.post(new Runnable() {
                       @Override
                       public void run() {
                           imageData.setImageBitmap(bitmap);
                       }
                   });

                   is.close();
                   cx.disconnect();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                progressDialog.dismiss();
            }
        }).start();
    }

    protected void tempo(){
        Log.e(">>>>>>>>", "Tempo Start");

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.show();
            }
        });

        for (int i=0; i < 10; i++){
            Log.e(">>>>>>>>", "Tempo N°" + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        progressDialog.dismiss();
        Log.e(">>>>>>>>", "Tempo End");

    }
}
