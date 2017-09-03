package com.udacity.stockhawk;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
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
        Log.d(LinearWidgetService.class.getSimpleName(),"Here");
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
        Log.d(TAG, "HERE'S JOHNNY");
        if(mCursor != null) mCursor.close();
        mCursor = mContext.getContentResolver().query(
                Contract.Quote.URI,
                null,
                null,
                null,
                null
        );

    }

    @Override
    public void onDestroy() {
        try{
            mCursor.close();
        }catch (Exception e){
            Log.e(TAG, "Destroy error");
        }
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
        //views.setTextViewText(R.id.widget_change, ""+mCursor.getCount());
        views.setTextViewText(R.id.widget_symbol, mCursor.getString(Contract.Quote.POSITION_SYMBOL));
        views.setTextViewText(R.id.widget_change, dollarFormat.format(mCursor.getFloat(Contract.Quote.POSITION_PRICE)));


        Bundle extras = new Bundle();
        extras.putString(mContext.getString(R.string.symbol_widget_key), mCursor.getString(Contract.Quote.POSITION_SYMBOL));
        extras.putString(mContext.getString(R.string.price_widdget_key), dollarFormat.format(mCursor.getFloat(Contract.Quote.POSITION_PRICE)));

        Intent fillIntent = new Intent();
        fillIntent.putExtras(extras);
        views.setOnClickFillInIntent(R.id.widget_symbol, fillIntent);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
