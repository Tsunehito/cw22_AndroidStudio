package com.example.slax.myapplicationexogallery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.widget.SearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private SearchView searchView;
    private RecyclerView listImages;
    private ArrayList<Image> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get element
        searchView = findViewById(R.id.inputSearch);
        listImages = findViewById(R.id.listImages);

        // listener
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        AsyncRestClient arc = new AsyncRestClient(this);
        arc.setOnReceiveDataListener(new AsyncRestClient.OnReceiveDataListener() {
            @Override
            public void onReceiveData(JSONObject jsonObject) {


                try {
                    JSONArray hits = jsonObject.getJSONArray("hits");
                    images.clear();
                    for (int i=0; i<hits.length(); i++){
                        images.add(new Image(hits.getJSONObject(i)));
                    }

                    listImages.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                    listImages.setAdapter(new ImageAdapter(images));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        arc.execute(
                new Pair<String, String>("HTTP_METHOD", "GET"),
                new Pair<String, String>("HTTP_URL", "https://pixabay.com/api/"),
                new Pair<String, String>("key", "8659067-9528e27b00b945401215ec184"),
                new Pair<String, String>("image_type", "photo"),
                new Pair<String, String>("q", query.trim())
        );
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
