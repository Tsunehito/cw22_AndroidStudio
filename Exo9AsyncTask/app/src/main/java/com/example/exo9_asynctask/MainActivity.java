package com.example.exo9_asynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private class SimpleTask extends AsyncTask<Void, Integer, Void>{

        // cycle de vie 1
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("preExecute", "");
        }

        // cycle de vie 2
        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i < 10 ; i++) {
                try {
                    publishProgress(i);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.e("progressUpdate", values[0].toString());
        }

        // cycle de vie 3
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.e("postExecute", "");
        }
    }
}
