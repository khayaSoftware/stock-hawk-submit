package com.udacity.stockhawk;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.stockhawk.data.Contract;
import com.udacity.stockhawk.data.PrefUtils;

import java.net.URI;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by noybs on 22/08/2017.
 */

public class LinearWidgetService extends RemoteViewsService{


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d(LinearWidgetService.class.getSimpleName(), "Here");
        return new LinearWidgetFactory(this.getApplicationContext());
    }
}
class LinearWidgetFactory implements RemoteViewsService.RemoteViewsFactory {
    private final static String TAG = LinearWidgetFactory.class.getSimpleName();
    Context mContext;
    private final DecimalFormat dollarFormatWithPlus;
    private final DecimalFormat dollarFormat;
    private final DecimalFormat percentageFormat;
    Cursor mCursor;

    public LinearWidgetFactory(Context appContext){
        mContext = appContext;
        dollarFormat = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
        dollarFormatWithPlus = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
        dollarFormatWithPlus.setPositivePrefix("+$");
        percentageFormat = (DecimalFormat) NumberFormat.getPercentInstance(Locale.getDefault());
        percentageFormat.setMaximumFractionDigits(2);
        percentageFormat.setMinimumFractionDigits(2);
        percentageFormat.setPositivePrefix("+");
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        Uri QUOTE_URI = Contract.BASE_URI.buildUpon().appendPath(Contract.PATH_QUOTE).build();
        Log.d(TAG, "HERE'S JOHNNY");
        if(mCursor != null) mCursor.close();
        mCursor = mContext.getContentResolver().query(
                QUOTE_URI,
                null,
                null,
                null,
                null
        );

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(mCursor == null) return 0;
        return mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        mCursor.moveToPosition(position);

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.stock_widget);

        Log.d(TAG, "Cursor " + position);
        views.setTextViewText(R.id.widget_change, ""+mCursor.getCount());
        //views.setTextViewText(R.id.widget_symbol, mCursor.getString(Contract.Quote.POSITION_SYMBOL));
        //views.setTextViewText(R.id.widget_change, dollarFormat.format(mCursor.getFloat(Contract.Quote.POSITION_PRICE)));


        //float rawAbsoluteChange = mCursor.getFloat(Contract.Quote.POSITION_ABSOLUTE_CHANGE);
        //float percentageChange = mCursor.getFloat(Contract.Quote.POSITION_PERCENTAGE_CHANGE);

        //if (rawAbsoluteChange > 0) {
            //holder.change.setBackgroundResource(R.drawable.percent_change_pill_green);
        //    views.setInt(R.id.widget_change, "setBackgroundResource", R.drawable.percent_change_pill_green);
        //} else {
            //holder.change.setBackgroundResource(R.drawable.percent_change_pill_red);
         //   views.setInt(R.id.widget_change, "setBackgroundResource", R.drawable.percent_change_pill_red);
        //}

       // String change = dollarFormatWithPlus.format(rawAbsoluteChange);
       // String percentage = percentageFormat.format(percentageChange / 100);

        //if (PrefUtils.getDisplayMode(mContext)
         //       .equals(mContext.getString(R.string.pref_display_mode_absolute_key))) {
        //    views.setTextViewText(R.id.widget_change, change);
        //} else {
        //    views.setTextViewText(R.id.widget_change, percentage);
        //}

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
