
package com.dat.testwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.dat.testwidget.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExampleAppWidgetProvider extends AppWidgetProvider {
    private static final String LOG_TAG = "ExampleWidget";

    private static final DateFormat df = new SimpleDateFormat("hh:mm:ss");

    /**
     * Custom Intent name that is used by the AlarmManager to tell us to update the clock once per second.
     */
    public static String CLOCK_WIDGET_UPDATE = "com.dat.testwidget.CUSTOM_UPDATE_APP_WIDGET";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.e(LOG_TAG, "onReceive" + intent.getAction());

        Log.d(LOG_TAG, "Clock update");
        // Get the widget manager and ids for this widget provider, then call the shared
        // clock update method.
        ComponentName thisAppWidget = new ComponentName(context.getPackageName(), getClass().getName());
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int ids[] = appWidgetManager.getAppWidgetIds(thisAppWidget);
        for (int appWidgetID : ids) {
            updateAppWidget(context, appWidgetManager, appWidgetID);
        }

    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;

        Log.d(LOG_TAG, "Updating Example Widgets.");

        // Perform this loop procedure for each App Widget that belongs to this
        // provider
        for (int appWidgetId : appWidgetIds) {
            // Create an Intent to launch ExampleActivity
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_MUTABLE);

            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget1);
            views.setOnClickPendingIntent(R.id.button, pendingIntent);
            // Tell the AppWidgetManager to perform an update on the current app
            // widget
            appWidgetManager.updateAppWidget(appWidgetId, views);

            // Update The clock label using a shared method
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Log.e(LOG_TAG, "updateAppWidget");
        String currentTime = df.format(new Date());

        RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.widget1);
        updateViews.setTextViewText(R.id.widget1label, currentTime);
        appWidgetManager.updateAppWidget(appWidgetId, updateViews);
    }

}
