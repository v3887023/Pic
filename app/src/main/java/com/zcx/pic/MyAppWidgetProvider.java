package com.zcx.pic;

import android.app.PendingIntent;
import android.app.WallpaperManager;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.io.IOException;

public class MyAppWidgetProvider extends AppWidgetProvider {
    public static final String ACTION_CLICK = "com.example.fragmenttest.action.CLICK";
    private static final String TAG = MyAppWidgetProvider.class.getSimpleName();
    private static volatile boolean idle = true;
    private Handler handler = new Handler();

    @Override
    public void onReceive(final Context context, Intent intent) {
        super.onReceive(context, intent);

        final String action = intent.getAction();
        Log.d(TAG, "onReceive: action = " + action);

        final RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        final ComponentName componentName = new ComponentName(context, MyAppWidgetProvider.class);

        if (idle) {
            remoteViews.setViewVisibility(R.id.btn_refresh, View.VISIBLE);
            remoteViews.setImageViewResource(R.id.btn_refresh, R.drawable.ic_picture);
            remoteViews.setViewVisibility(R.id.loading_progress_bar, View.GONE);
            appWidgetManager.updateAppWidget(componentName, remoteViews);
        }

        if (ACTION_CLICK.equals(action) && idle) {
            idle = false;

            remoteViews.setViewVisibility(R.id.btn_refresh, View.GONE);
            remoteViews.setViewVisibility(R.id.loading_progress_bar, View.VISIBLE);
            appWidgetManager.updateAppWidget(componentName, remoteViews);
            Log.d(TAG, "loading");

            ImageDownloader.loadImage(context.getResources().getDisplayMetrics(), new ImageDownloader.LoadCallback() {
                @Override
                public void onSuccess(String keyword, Bitmap bitmap) throws IOException {
                    Log.d(TAG, "onSuccess");

                    WallpaperManager wallpaperManager = (WallpaperManager) context.getSystemService(Context.WALLPAPER_SERVICE);
                    wallpaperManager.setBitmap(bitmap);

                    remoteViews.setViewVisibility(R.id.btn_refresh, View.VISIBLE);
                    remoteViews.setImageViewResource(R.id.btn_refresh, R.drawable.ic_done);
                    remoteViews.setViewVisibility(R.id.loading_progress_bar, View.GONE);
                    appWidgetManager.updateAppWidget(componentName, remoteViews);

                    try {
                        Thread.sleep(800);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    remoteViews.setImageViewResource(R.id.btn_refresh, R.drawable.ic_picture);
                    appWidgetManager.updateAppWidget(componentName, remoteViews);

                    handler.post(() -> Toast.makeText(context, keyword, Toast.LENGTH_SHORT).show());

                    idle = true;
                }

                @Override
                public void onError() {
                    Log.d(TAG, "onError");
                    remoteViews.setViewVisibility(R.id.btn_refresh, View.VISIBLE);
                    remoteViews.setImageViewResource(R.id.btn_refresh, R.drawable.ic_error);
                    remoteViews.setViewVisibility(R.id.loading_progress_bar, View.GONE);
                    appWidgetManager.updateAppWidget(componentName, remoteViews);

                    try {
                        Thread.sleep(800);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    remoteViews.setImageViewResource(R.id.btn_refresh, R.drawable.ic_picture);
                    appWidgetManager.updateAppWidget(componentName, remoteViews);

                    idle = true;
                }
            });
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.d(TAG, "onUpdate");

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);

        Intent clickIntent = new Intent(ACTION_CLICK);
        PendingIntent clickPendingIntent = PendingIntent.getBroadcast(context, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.btn_refresh, clickPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetIds[0], remoteViews);
    }
}
