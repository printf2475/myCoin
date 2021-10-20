package kr.or.mrhi.myCoin.adapter;

import static kr.or.mrhi.myCoin.MainActivity.namePositionMap;
import static kr.or.mrhi.myCoin.MainActivity.stringSymbol;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kr.or.mrhi.myCoin.CoinMain;
import kr.or.mrhi.myCoin.POJO.TickerData;
import kr.or.mrhi.myCoin.POJO.TickerPOJOData;
import kr.or.mrhi.myCoin.POJO.orderBookCoins.Btc;
import kr.or.mrhi.myCoin.R;
import kr.or.mrhi.myCoin.viewModel.CoinViewModel;

public class MainCoinAdapter extends RecyclerView.Adapter<MainCoinAdapter.ViewHolders> {
    private List<String> transactionCoin;
    private TickerData tickerCoin;
//    private TickerPOJOData tickerPOJOData;



    public MainCoinAdapter(List<String> transactionCoin) {
        this.transactionCoin = transactionCoin;
//        this.tickerCoin = tickerData;
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

    public void setTickerData(TickerData tickerData) {
        this.tickerCoin = tickerData;
    }


    public class ViewHolders extends RecyclerView.ViewHolder {
            TextView tvCoinNameList,tvCurrentPriceList,tvChangeRateList,tvTotalVolumeList;
        public ViewHolders(@NonNull View itemView) {
            super(itemView);
            tvCoinNameList = itemView.findViewById(R.id.tvCoinNameList);
            tvCurrentPriceList = itemView.findViewById(R.id.tvCurrentPriceList);
            tvChangeRateList = itemView.findViewById(R.id.tvChangeRateList);
            tvTotalVolumeList = itemView.findViewById(R.id.tvTotalVolumeList);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//
                        int position = getAdapterPosition();
                        Intent intent = new Intent(view.getContext(), CoinMain.class);
                        intent.putExtra("CoinID", stringSymbol[position]);
                        intent.putExtra("CoinData", transactionCoin.get(position));
                        intent.putExtra("Position", namePositionMap.get(stringSymbol[position]));
                        view.getContext().startActivity(intent);
                        Log.i("값이가나", intent.toUri(0));
//
                }
            });
        }

        public void onBind(int position) {
            if (position<transactionCoin.size() && tickerCoin != null){
                tvCoinNameList.setText(stringSymbol[position]);
                tvCurrentPriceList.setText(transactionCoin.get(position));
                if(tvCoinNameList.getText().equals("BTC")){
                    Log.i("값이 나오나",tvCoinNameList.getText().toString());
//                    tvChangeRateList.setText();
//                    tvTotalVolumeList.setText(transactionCoin.get(position));
                }else if(tvCoinNameList.getText().equals("ETH")){
                    tvChangeRateList.setText(String.valueOf(String.format("%.2f",(Double.parseDouble(transactionCoin.get(position)) - Double.parseDouble(tickerCoin.getEth().getPrevClosingPrice()))/ Double.parseDouble(tickerCoin.getEth().getPrevClosingPrice()) * 100)));
                    tvTotalVolumeList.setText(tickerCoin.getEth().getAccTradeValue24H());
                }

//                tvChangeRateList.setText(String.valueOf((Double.parseDouble(transactionCoin.get(position)) - Double.parseDouble(tickerPOJOData.getPrevClosingPrice(tvCoinNameList)))/Double.parseDouble(tickerPOJOData.getPrevClosingPrice())*100));
//                tvTotalVolumeList.setText(tickerPOJOData.getAccTradeValue24H().toString());
                //                mainChangePrice = Double.parseDouble(stringList.get(position)) - Double.parseDouble(prevClosingPrice);
//                mainPercent = Double.parseDouble(String.format("%.2f", (mainChangePrice) / Double.parseDouble(prevClosingPrice) * 100));변동률
                //acc trade value
            }
//            coinCompareYesterday.setText(String.valueOf(currentPrice/closingPrice*100-100));
        }
    }
//    private void startActivity(Intent intent) {
//        startActivity(intent);
//    }
}
