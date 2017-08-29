package com.udacity.stockhawk.ui;


import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;
import com.udacity.stockhawk.data.PrefUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import yahoofinance.histquotes.HistoricalQuote;

class StockHistoryAdapter extends RecyclerView.Adapter<StockHistoryAdapter.StockHistoryViewHolder> {

    public ArrayList<HistoricalQuote> stocks;
    private final Context context;

    StockHistoryAdapter(Context context) {
        this.context = context;
    }

    class StockHistoryViewHolder extends RecyclerView.ViewHolder{
        TextView symbol;
        StockHistoryViewHolder(View itemView) {
            super(itemView);
            symbol = (TextView) itemView.findViewById(R.id.symbol_history);
        }
    }

    @Override
    public StockHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layoutForListItem = R.layout.history_item;
        LayoutInflater inflater = LayoutInflater.from(this.context);
        boolean shouldAttachToParentImmedietly = false;

        View view = inflater.inflate(layoutForListItem, parent, shouldAttachToParentImmedietly);
        StockHistoryViewHolder historyViewHolder = new StockHistoryViewHolder(view);

        return historyViewHolder;
    }

    @Override
    public void onBindViewHolder(StockHistoryViewHolder holder, int position) {

        //holder.symbol.setText("Hello");
        holder.symbol.setText(stocks.get(position).getLow().toString());

    }

    @Override
    public int getItemCount() {
        if (null == stocks) return 0;
        return stocks.size();
    }


    public void setStocks(ArrayList<HistoricalQuote> stocks){
        this.stocks = stocks;
        notifyDataSetChanged();
    }
}
