package com.example.androidchoi.appsearcher;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Created by hankook on 2015. 7. 24..
 */
public class WidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // 여러개의 위젯이 띄워질 경우 위젯을 컨트롤 하기위한 소스.
        for (int i = 0; i < appWidgetIds.length; i++) {
            int appWidgetId = appWidgetIds[i];
            RemoteViews views = buildViews(context);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    // 실행 인텐트 설정 코드.
    private PendingIntent buildActivityIntent(Context context) {
        Intent intent = new Intent(context, AppWidgetActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
        return pi;
    }

    private RemoteViews buildViews(Context context) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        views.setOnClickPendingIntent(R.id.widget_icon, buildActivityIntent(context));
        return views;
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }
}
