package com.tr.blebutton;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;


import java.util.UUID;

import static android.content.Context.MODE_PRIVATE;
import static com.tr.blebutton.AppWidgetConfig.ADDRESS_SAVED;
import static com.tr.blebutton.AppWidgetConfig.KAPI_1;
import static com.tr.blebutton.AppWidgetConfig.PASSWORD_SAVED;
import static com.tr.blebutton.AppWidgetConfig.SHARED_PREFS;


/**
 * Implementation of App Widget functionality.
 */
public class OpenWidget extends AppWidgetProvider {

    PendingIntent pendingIntent;
    DBhandeler DB;
    String Address;
    public static String EXTRA_AD = "ADDRESS";
    public static String EXTRA_PASS = "PASSWORD";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            DB = new DBhandeler(context, null, null, 1);


            //Give the name of service you want to start in your case Alarm Service

            Intent intent = new Intent(context, BluetoothLeService.class);
            Intent ActivityIntent = new Intent(context, AppWidgetConfig.class);
            PendingIntent ActivityIntentConfig = PendingIntent.getActivity(context,0,ActivityIntent,0);
            SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
            //  SharedPreferences.Editor editor = prefs.edit();
            String buttonText = prefs.getString(KAPI_1, "Kapı seç");

            String PASS = prefs.getString(PASSWORD_SAVED, "PASS");
            String ADDR = prefs.getString(ADDRESS_SAVED, "ADDR");

            intent.putExtra(EXTRA_AD, ADDR);
            intent.putExtra(EXTRA_PASS, PASS);

            intent.setData(Uri.withAppendedPath(Uri.parse("myapp://widget/id/#togetituniqie" + this), UUID.randomUUID().toString()));
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                pendingIntent = PendingIntent.getForegroundService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            }else {
                pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            }
            //This basically sets the layout of your widget to widget_layout
            RemoteViews views = new RemoteViews(context.getPackageName(),
                    R.layout.open_widget);
         //   views.setOnClickPendingIntent(R.id.idget_text, ActivityIntentConfig);
            views.setOnClickPendingIntent(R.id.Open, pendingIntent);
        //    views.setCharSequence(R.id.idget_text, "setText", buttonText);


            //views.setOnClickPendingIntent(R.id.Open, pendingIntent);

            //views.setInt(R.id.Open, "setBackgroundResource",R.drawable.siyah);
            Bundle appWidgetOptions = appWidgetManager.getAppWidgetOptions(appWidgetId);
            resizeWidget(appWidgetOptions, views);

            appWidgetManager.updateAppWidget(appWidgetId, views);
            DB.addName(appWidgetId);
        }}

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.open_widget);
        resizeWidget(newOptions, views);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
    private void resizeWidget(Bundle appWidgetOptions, RemoteViews views) {

        int maxHeight = appWidgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT);
        if (maxHeight > 80) {
          //  views.setViewVisibility(R.id.idget_text, View.VISIBLE);
        } else {
           // views.setViewVisibility(R.id.idget_text, View.GONE);
        }
    }
}