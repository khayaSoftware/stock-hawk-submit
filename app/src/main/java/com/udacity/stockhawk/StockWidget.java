package com.udacity.stockhawk;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.udacity.stockhawk.ui.MainActivity;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {link StockWidgetConfigureActivity StockWidgetConfigureActivity}
 */
public class StockWidget extends AppWidgetProvider {

    @Override
    public void onReceive(Context context, Intent intent) {
        String intentAction = intent.getAction();
        if (intentAction != null && intentAction.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)){
            AppWidgetManager manager = AppWidgetManager.getInstance(context);
            ComponentName componentName = new ComponentName(context,StockWidget.class);
            manager.notifyAppWidgetViewDataChanged(manager.getAppWidgetIds(componentName), R.id.widget_linear_view);
        }
        super.onReceive(context, intent);
    }

    public static void updateFromContext(Context c){
        Intent i = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        ComponentName thisWidget = new ComponentName(c,StockWidget.class);
        i.setComponent(thisWidget);
        c.sendBroadcast(i);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        super.onDisabled(context);
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_linear_view);
        Intent i = new Intent(context, LinearWidgetService.class);
        views.setRemoteAdapter(R.id.widget_linear_view, i);


        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

}

