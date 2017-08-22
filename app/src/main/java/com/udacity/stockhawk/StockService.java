package com.udacity.stockhawk;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.udacity.stockhawk.data.Contract;

/**
 * Created by noybs on 22/08/2017.
 */

public class StockService extends IntentService {

    public StockService(){super(StockService.class.getSimpleName());}

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        handleActionUpdateQuoteWidgets();
    }

    private void handleActionUpdateQuoteWidgets(){
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, StockWidget.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_linear_view);
        StockWidget.updateAppWidget(this, appWidgetManager, appWidgetIds

        );
    }
}
