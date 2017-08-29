package com.udacity.stockhawk.ui;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.udacity.stockhawk.MockUtils;
import com.udacity.stockhawk.R;

import java.util.ArrayList;
import java.util.List;

import yahoofinance.histquotes.HistoricalQuote;

public class DetailStockActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<HistoricalQuote>> {

    private TextView mSymbol;
    private RecyclerView mHistoryList;
    private String TAG = DetailStockActivity.class.getSimpleName();
    private List<HistoricalQuote> stocksList;
    private StockHistoryAdapter stockHistoryAdapter;
    private static final int LOADER_ID = 1236;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_stock);

        mSymbol = (TextView) findViewById(R.id.symbol_history);

        Bundle extras = getIntent().getExtras();
        mSymbol.setText(extras.getString(getString(R.string.symbol_key)));

        loadHistory();
    }

    public void loadHistory(){
        mHistoryList = (RecyclerView) findViewById(R.id.rv_stock_history);
        LinearLayoutManager layoutMg = new LinearLayoutManager(this);
        mHistoryList.setLayoutManager(layoutMg);
        stockHistoryAdapter = new StockHistoryAdapter(this);
        mHistoryList.setHasFixedSize(true);
        mHistoryList.setAdapter(stockHistoryAdapter);

        Bundle bundle = new Bundle();
        LoaderManager lm = getLoaderManager();
        Loader<ArrayList<HistoricalQuote>> stockLoadaer = lm.getLoader(LOADER_ID);

        if (stockLoadaer == null) {
            lm.initLoader(LOADER_ID, bundle, this);
        } else {
            lm.restartLoader(LOADER_ID, bundle, this);
        }
    }


    @Override
    public Loader<ArrayList<HistoricalQuote>> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<ArrayList<HistoricalQuote>>(this) {
            @Override
            public ArrayList<HistoricalQuote> loadInBackground() {
                try{
                    stocksList = MockUtils.getHistory();
                    return (ArrayList<HistoricalQuote>) stocksList;
                }catch (Exception e){
                    Log.e(TAG, "MY ERROR" + e.getMessage());
                    return null;
                }

            }

            @Override
            protected void onStartLoading() {
                if(args == null) {
                    return;
                }
                forceLoad();
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<HistoricalQuote>> loader, ArrayList<HistoricalQuote> data) {
        if(stocksList != null){
            stockHistoryAdapter.setStocks((ArrayList<HistoricalQuote>) stocksList);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<HistoricalQuote>> loader) {
    }
}
