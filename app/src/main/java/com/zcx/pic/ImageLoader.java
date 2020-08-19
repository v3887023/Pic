package com.zcx.pic;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ImageLoader {
    private static final String TAG = ImageLoader.class.getSimpleName();
    private static Handler handler = new Handler(Looper.getMainLooper());

    public static void loadImage(String url, ImageView imageView) {
        OkHttpClient client = new OkHttpClient.Builder().followRedirects(false).build();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String location = response.header("Location");
                Log.d(TAG, "location: " + location);
                handler.post(() -> Glide.with(imageView).load(location).into(imageView));
            }
        });
    }
}
