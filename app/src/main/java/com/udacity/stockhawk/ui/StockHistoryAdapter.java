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

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import yahoofinance.histquotes.HistoricalQuote;

class StockHistoryAdapter extends RecyclerView.Adapter<StockHistoryAdapter.StockHistoryViewHolder> {

    public ArrayList<HistoricalQuote> stocks;
    private final Context context;
    private static final String DAATE_FORMAT = "dd-MM-yyyy";
    StockHistoryAdapter(Context context) {
        this.context = context;
    }

    class StockHistoryViewHolder extends RecyclerView.ViewHolder{
        TextView date;
        TextView price;
        StockHistoryViewHolder(View itemView) {
            super(itemView);

            date = (TextView) itemView.findViewById(R.id.history_date);
            price = (TextView) itemView.findViewById(R.id.history_price);
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

        SimpleDateFormat dateFormat = new SimpleDateFormat(DAATE_FORMAT);
        dateFormat.setCalendar(stocks.get(position).getDate());

        //holder.symbol.setText("Hello");
        holder.date.setText(dateFormat.format(stocks.get(position).getDate().getTime()));
        BigDecimal closes = stocks.get(position).getClose();
        holder.price.setText((closes.setScale(2, BigDecimal.ROUND_HALF_UP)).toString());

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
