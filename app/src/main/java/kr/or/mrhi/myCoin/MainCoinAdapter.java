package kr.or.mrhi.myCoin;


import static kr.or.mrhi.myCoin.MainActivity.stringSymbol;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kr.or.mrhi.myCoin.POJO.TickerData;

public class MainCoinAdapter extends RecyclerView.Adapter<MainCoinAdapter.ViewHolders> {
    private List<String> transactionCoin;
    private TickerData tickerCoin;

    public MainCoinAdapter(List<String> transactionCoin) {
        this.transactionCoin = transactionCoin;
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

    public void setTicker(TickerData tickerData) {
        this.tickerCoin=tickerData;
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
            if (tickerCoin!=null){
                coinCompareYesterday.setText(tickerCoin.getBtc().getAccTradeValue24H());

            }
            coinId.setText(stringSymbol[position]);
            coinCurrentPrice.setText(transactionCoin.get(position));
//            coinCompareYesterday.setText(String.valueOf(currentPrice/closingPrice*100-100));
        }
    }
}
