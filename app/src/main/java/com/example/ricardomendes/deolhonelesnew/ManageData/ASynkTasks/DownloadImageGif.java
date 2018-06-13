package com.example.ricardomendes.deolhonelesnew.ManageData.ASynkTasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.example.ricardomendes.deolhonelesnew.R;

import java.io.InputStream;

public class DownloadImageGif extends AsyncTask<String, Void, Bitmap>{
    private ImageView imageView;
    private Context context;
    public DownloadImageGif(Context context, ImageView imageView){
        this.imageView = imageView;
        this.context = context;
    }
    @Override
    protected Bitmap doInBackground(String... strings) {
        Bitmap bitmap = null;
        try{
            InputStream input = new java.net.URL(strings[0]).openStream();
            bitmap = BitmapFactory.decodeStream(input);
        }catch (Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap result){
        if(result==null){
            Drawable drawable = context.getResources().getDrawable(R.drawable.image_off);
            result = ((BitmapDrawable)drawable).getBitmap();
        }
        imageView.setImageBitmap(result);
    }
}
