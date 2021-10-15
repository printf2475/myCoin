package kr.or.mrhi.myCoin;

import static kr.or.mrhi.myCoin.MainActivity.strings;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kr.or.mrhi.myCoin.POJO.TickerData;
import kr.or.mrhi.myCoin.POJO.TransactionData;

public class MainCoinAdapter extends RecyclerView.Adapter<MainCoinAdapter.ViewHolders> {
    private List<String> transactionCoin;
    private TickerData tickerCoin;

    public MainCoinAdapter(List<String> transactionCoin ,TickerData tickerData) {
        this.transactionCoin = transactionCoin;
        this.tickerCoin = tickerData;

    }

    @NonNull
    @Override
    public MainCoinAdapter.ViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coinlist_item,parent,false);
        return new ViewHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainCoinAdapter.ViewHolders holder, int position) {
        holder.onBind(position);
    }



    @Override
    public int getItemCount() {
        return transactionCoin.size();
    }

    public class ViewHolders extends RecyclerView.ViewHolder {
            TextView coinId,coinCurrentPrice,coinCompareYesterday;
        public ViewHolders(@NonNull View itemView) {
            super(itemView);
            coinId = itemView.findViewById(R.id.coinId);
            coinCurrentPrice = itemView.findViewById(R.id.coinCurrentPrice);
            coinCompareYesterday = itemView.findViewById(R.id.coinCompareYesterday);
        }

        public void onBind(int position) {
            double currentPrice= Double.parseDouble(transactionCoin.get(position));
//            double closingPrice= Double.parseDouble(tickerCoin.getBtc().getClosingPrice());
            coinId.setText(strings[position]);
            coinCurrentPrice.setText(transactionCoin.get(position));
//            coinCompareYesterday.setText(String.valueOf(currentPrice/closingPrice*100-100));
        }
    }
}
