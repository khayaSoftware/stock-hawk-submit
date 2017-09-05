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
import com.jjoe64.graphview.series.DataPoint;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.udacity.stockhawk.MockUtils;
import com.udacity.stockhawk.R;

//import org.junit.experimental.theories.DataPoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import yahoofinance.histquotes.HistoricalQuote;

public class DetailStockActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<HistoricalQuote>> {

    private TextView mSymbol;
    private TextView mPrice;
    private RecyclerView mHistoryList;
    private String TAG = DetailStockActivity.class.getSimpleName();
    private List<HistoricalQuote> stocksList;
    private StockHistoryAdapter stockHistoryAdapter;
    private static final int LOADER_ID = 1236;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_stock);

        Bundle extras = getIntent().getExtras();

        getSupportActionBar().setTitle(getString(R.string.history_text) + extras.getString(getString(R.string.symbol_key)));
        mSymbol = (TextView) findViewById(R.id.symbol_history);
        mPrice = (TextView) findViewById(R.id.price_history);

        mSymbol.setText(extras.getString(getString(R.string.symbol_key)));


        loadHistory();
    }

    public void loadHistory(){

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
            GraphView graph = (GraphView) findViewById(R.id.graph);

            DataPoint[] dps = new DataPoint[stocksList.size()];

            for (int i = 0;i < stocksList.size();++i){
                Integer xi = i;
                Integer yi = stocksList.get(i).getClose().intValue();
                DataPoint dp = new DataPoint(xi,yi);
                dps[i] = dp;
            }

            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dps);
            graph.addSeries(series);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<HistoricalQuote>> loader) {
    }
}
