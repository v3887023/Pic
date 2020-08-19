package com.zcx.pic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class ImageDownloader {
    private static final String BASE_URL = "https://source.unsplash.com";
    private static final String[] KEY_WORD_ARRAY = {"sun", "mountain",
            "sea", "nature", "rain", "lake", "river", "sky", "ocean",
            "sunset", "sunrise", "free", "dawn", "hill", "cloud", "snow",
            "island"};
    private static final String TAG = ImageDownloader.class.getSimpleName();
    private static final Random x = new Random();

    private static String nextRandomKeyWord() {
        return KEY_WORD_ARRAY[x.nextInt(KEY_WORD_ARRAY.length)];
    }

    public static void loadImage(DisplayMetrics displayMetrics, final LoadCallback callback) {
        Log.d(TAG, "width pixels: " + displayMetrics.widthPixels);
        Log.d(TAG, "height pixels: " + displayMetrics.heightPixels);
        String keyWord = nextRandomKeyWord();
        final String urlString = BASE_URL +
                "/" + displayMetrics.widthPixels + "x" + displayMetrics.heightPixels + "/?" +
                keyWord;

        new Thread(() -> {
            HttpURLConnection connection = null;
            try {
                URL url = new URL(urlString);
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5 * 1000);
                connection.setReadTimeout(5 * 1000);

                final Bitmap bitmap = BitmapFactory.decodeStream(connection.getInputStream());

                if (bitmap == null) {
                    callback.onError();
                } else {
                    callback.onSuccess(keyWord, bitmap);
                }
            } catch (IOException e) {
                e.printStackTrace();
                callback.onError();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }).start();
    }

    public interface LoadCallback {
        void onSuccess(String keyword, Bitmap bitmap) throws IOException;

        void onError();
    }
}
