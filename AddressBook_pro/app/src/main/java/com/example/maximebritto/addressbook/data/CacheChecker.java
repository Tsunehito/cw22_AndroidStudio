package com.example.maximebritto.addressbook.data;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by trainermac on 02/03/16.
 */
public class CacheChecker extends AsyncTask<String, Integer, String> {

    private final TextView ui_statusLabel;

    public CacheChecker(TextView statusLabel) {
        ui_statusLabel = statusLabel;
    }

    @Override
    protected String doInBackground(String... params) {
        int handledParamCount = 0;
        for (String param:params) {
            if (isCancelled() == false) {
                Log.i("CacheChecker","Starting : " + param);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.i("CacheChecker","Ended : " + param);
                handledParamCount = handledParamCount + 1;
                int progress = (int) (handledParamCount / (float)params.length * 100);
                publishProgress(progress);
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ui_statusLabel.setText("Cache loading started");
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        ui_statusLabel.setText(values[0] + " %");
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        ui_statusLabel.setText("Cache loading finished!");
    }
}
