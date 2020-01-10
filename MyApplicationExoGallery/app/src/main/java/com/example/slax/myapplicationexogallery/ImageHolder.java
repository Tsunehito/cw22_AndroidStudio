package com.example.slax.myapplicationexogallery;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class ImageHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Context context;
    private ProgressBar cardProgressBar;
    private ImageView cardImageView;
    private Image image;


    public ImageHolder(@NonNull View itemView) {
        super(itemView);
        context          = itemView.getContext();
        cardProgressBar  = itemView.findViewById(R.id.cardProgressBar);
        cardImageView    = itemView.findViewById(R.id.cardImageView);
        cardImageView.setOnClickListener(this);

    }

    public void display(final Image image) {
        this.image = image;
        if(image.getBitmap() == null){
            new Thread(new Runnable() {
                @Override
                public void run() {
                   loadImage();
                }
            }).start();

        } else {
            cardImageView.setImageBitmap(image.getBitmap());
            cardProgressBar.setVisibility(View.INVISIBLE);
        }
    }


    protected void loadImage() {
        try {
            URL url = new URL(image.getPreviewUrl());
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.setInstanceFollowRedirects(false);
            httpsURLConnection.setConnectTimeout(5000);
            httpsURLConnection.setReadTimeout(5000);

            InputStream inputStream = httpsURLConnection.getInputStream();
            final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            image.setBitmap(bitmap);

            cardImageView.post(new Runnable() {
                @Override
                public void run() {
                    cardImageView.setImageBitmap(bitmap);
                    cardProgressBar.setVisibility(View.INVISIBLE);
                }
            });

            inputStream.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, ViewerActivity.class);
        intent.putExtra("imageParcel", image);
        context.startActivity(intent);
    }
}
