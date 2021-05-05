package com.zcx.pic;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtils {
    private static final String TAG = HttpUtils.class.getSimpleName();
    private static OkHttpClient httpClient = new OkHttpClient.Builder().followRedirects(false).build();

    public static void getRedirectedLocation(String url, LocationCallback locationCallback) {
        Request request = new Request.Builder().head().url(url).build();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {
                final String location = response.header("Location");
                Log.d(TAG, "location: " + location);

                if (TextUtils.isEmpty(location)) {
                    locationCallback.onError();
                } else {
                    locationCallback.onResponse(location);
                }
            }
        });
    }

    public static String getRedirectedLocation(String keyword) {
        String url = Utils.getUrl(keyword);
        Request request = new Request.Builder().head().url(url).build();
        String location = null;
        try (Response response = httpClient.newCall(request).execute()) {

            location = response.header("Location");
            Log.d(TAG, "location: " + location);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return location == null ? "" : location;
    }


    public interface LocationCallback {
        void onResponse(String location);

        void onError();
    }
}
