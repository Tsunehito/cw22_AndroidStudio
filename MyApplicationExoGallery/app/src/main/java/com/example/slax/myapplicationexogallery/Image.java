package com.example.slax.myapplicationexogallery;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Image implements Parcelable {


    private Integer id;
    private String largeUrl;
    private String previewUrl;
    private Bitmap bitmap;


    public Image(JSONObject jsonObject) {
        try {
            setId(jsonObject.getInt("id"));
            setLargeUrl(jsonObject.getString("largeImageURL"));
            setPreviewUrl(jsonObject.getString("previewURL"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    protected Image(Parcel in) {
        id = in.readInt();
        largeUrl = in.readString();
        previewUrl = in.readString();
        bitmap = in.readParcelable(Bitmap.class.getClassLoader());
    }



    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };



    @Override
    public int describeContents() {
        return 0;
    }



    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(largeUrl);
        dest.writeString(previewUrl);
        dest.writeParcelable(bitmap, flags);
    }




    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLargeUrl() {
        return largeUrl;
    }

    public void setLargeUrl(String largeUrl) {
        this.largeUrl = largeUrl;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }


}
