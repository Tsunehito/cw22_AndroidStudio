package com.example.slax.myapplicationexogallery;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;


public class AsyncRestClient extends AsyncTask<Pair<String, String>, Void, JSONObject> {

    private String error;
    private Context context;


    // --------- listener start ---------

    private OnReceiveDataListener onReceiveDataListener;

    public interface OnReceiveDataListener {
         public void onReceiveData(JSONObject jsonObject);
    }

    public void setOnReceiveDataListener(OnReceiveDataListener onReceiveDataListener) {
        this.onReceiveDataListener = onReceiveDataListener;
    }

    // --------- listener end ---------


    public AsyncRestClient(Context cx) {
       context = cx;
       error   = new String();
    }


    @Override
    protected JSONObject doInBackground(Pair<String, String>... pairs) {

        JSONObject result  = new JSONObject();
        String query       = new String();
        String http_method = null;
        String http_url    = null;


        for(int i=0; i<pairs.length; i++){

            if(pairs[i].first.equals("HTTP_URL")){
                http_url = pairs[i].second;
                continue;
            }

            if(pairs[i].first.equals("HTTP_METHOD")){
                http_method = pairs[i].second.toUpperCase();
                continue;
            }

            if(!query.isEmpty()){
                query += "&";
            }

            try {
                query += URLEncoder.encode(pairs[i].first, "UTF-8") + "=" + URLEncoder.encode(pairs[i].second, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }


        try {

            URL url = new URL(http_url);
            if(http_method.equals("GET") || http_method.equals("DELETE")){
                url = new URL(http_url + "?" + query);
            }

            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setRequestMethod(http_method);
            httpsURLConnection.setInstanceFollowRedirects(false);
            httpsURLConnection.setConnectTimeout(5000);
            httpsURLConnection.setReadTimeout(5000);
            httpsURLConnection.setUseCaches(false);

            if(http_method.equals("POST") || http_method.equals("UPDATE")) {
                httpsURLConnection.setDoInput(true);
                httpsURLConnection.setDoOutput(true);
                OutputStream outputStream = httpsURLConnection.getOutputStream();
                outputStream.write(query.getBytes("UTF-8"));
                outputStream.close();
            }

            InputStream inputStream = httpsURLConnection.getInputStream();
            Scanner scanner = new Scanner(inputStream, "UTF-8");
            String response = scanner.useDelimiter("\\A").next();
            scanner.close();
            inputStream.close();

            result = new JSONObject(response);

        } catch (MalformedURLException e) {
            error = context.getString(R.string.error_url);
            e.printStackTrace();
        } catch (IOException e) {
            error = context.getString(R.string.error_io);
            e.printStackTrace();
        } catch (JSONException e) {
            error = context.getString(R.string.error_json);
            e.printStackTrace();
        }

        return result;
    }


    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);

        if(!error.isEmpty()) {
            Toast.makeText(context, error, Toast.LENGTH_LONG).show();
        }

        if(onReceiveDataListener!=null){
            onReceiveDataListener.onReceiveData(jsonObject);
        }
    }
}
