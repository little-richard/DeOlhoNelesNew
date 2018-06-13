package com.example.ricardomendes.deolhonelesnew.ManageData.ASynkTasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;

public class DownloadImage extends AsyncTask<String, Void, Bitmap> {
    ImageView imageView;
    public DownloadImage(ImageView imageView){
        this.imageView = imageView;

    }
    protected  Bitmap doInBackground(String... urls){
        String imageURL = urls[0];
        Bitmap imageRounded= null;
        try{
            InputStream in = new java.net.URL(imageURL).openStream();
            Bitmap bimage = BitmapFactory.decodeStream(in);
            Bitmap mbitmap = bimage;
            if(mbitmap!=null) {
                imageRounded = Bitmap.createBitmap(mbitmap.getWidth(), mbitmap.getHeight(), mbitmap.getConfig());
                Canvas canvas = new Canvas(imageRounded);
                Paint mpaint = new Paint();
                mpaint.setAntiAlias(true);
                mpaint.setShader(new BitmapShader(mbitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
                canvas.drawRoundRect((new RectF(0, 0, mbitmap.getWidth(), mbitmap.getHeight())), 10, 10, mpaint);
            }else{
                imageRounded = null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return imageRounded;
    }

    protected void onPostExecute(Bitmap result){
        if(result!=null) {
            imageView.setImageBitmap(result);
        }
    }
}
