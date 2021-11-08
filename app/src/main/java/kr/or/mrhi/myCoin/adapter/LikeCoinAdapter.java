package kr.or.mrhi.myCoin.adapter;


import static kr.or.mrhi.myCoin.activity.MainActivity.namePositionMap;
import static kr.or.mrhi.myCoin.activity.MainActivity.stringName;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kr.or.mrhi.myCoin.activity.CoinMain;
import kr.or.mrhi.myCoin.model.TickerDTO;
import kr.or.mrhi.myCoin.R;

public class LikeCoinAdapter extends RecyclerView.Adapter<LikeCoinAdapter.ViewHolders> {
    private List<String> transactionCoin;
    private List<TickerDTO> tickerDataList;
    private List<String> favoritCoinList;
    private double changeRate;

    public LikeCoinAdapter(List<String> transactionCoin, List<TickerDTO> tickerDataList, List<String> favoritCoinList) {
        this.transactionCoin = transactionCoin;
        this.tickerDataList = tickerDataList;
        this.favoritCoinList = favoritCoinList;
    }

    @NonNull
    @Override
    public LikeCoinAdapter.ViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coinlist_item, parent, false);
        return new ViewHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LikeCoinAdapter.ViewHolders holder, int position) {
        holder.onBind(position);
    }


    @Override
    public int getItemCount() {
        return favoritCoinList.size();
    }

    public void setTickerData(List<TickerDTO> tickerDataList) {
        this.tickerDataList = tickerDataList;
    }

    public class ViewHolders extends RecyclerView.ViewHolder {
        TextView tvCoinNameList, tvCurrentPriceList, tvChangeRateList, tvTradeValue24H, tvCoinNameList2;
        String tradeValue24HStr, prevClosingPrice, currentPrice;
        double currentPriceDouble, prevClosingPriceDouble, tradeValue24HDouble;
        long tradeValue24H;

        public ViewHolders(@NonNull View itemView) {
            super(itemView);
            tvCoinNameList = itemView.findViewById(R.id.tvCoinNameList);
            tvCurrentPriceList = itemView.findViewById(R.id.tvCurrentPriceList);
            tvChangeRateList = itemView.findViewById(R.id.tvChangeRateList);
            tvTradeValue24H = itemView.findViewById(R.id.tvTotalVolumeList);
            tvCoinNameList2 = itemView.findViewById(R.id.tvCoinNameList2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(view.getContext(), CoinMain.class);
                    intent.putExtra("CoinID", favoritCoinList.get(position));
                    intent.putExtra("CoinData", transactionCoin.get(position));
                    intent.putExtra("Position", namePositionMap.get(favoritCoinList.get(position)));
                    view.getContext().startActivity(intent);
                }
            });
        }

        public void onBind(int position) {
            prevClosingPrice = "0.0";
            currentPrice = null;

            if (position < transactionCoin.size() && tickerDataList != null && !favoritCoinList.isEmpty()) {
                initCoinData(position);
                changeTextColor(changeRate);
            }
        }

        private void initCoinData(int position) {
            tvCoinNameList.setText(favoritCoinList.get(position));
            currentPrice = transactionCoin.get(position);
            tvCurrentPriceList.setText(currentPrice);
            tvCoinNameList2.setText(favoritCoinList.get(position) + "/KRW");

            prevClosingPrice = tickerDataList.get(position).getPrevClosingPrice();
            tradeValue24HDouble = Double.parseDouble(tickerDataList.get(position).getAccTradeValue24H());

            currentPriceDouble = Double.parseDouble(currentPrice);
            prevClosingPriceDouble = Double.parseDouble(prevClosingPrice);

            changeRate = (currentPriceDouble - prevClosingPriceDouble) / prevClosingPriceDouble * 100;
            tvChangeRateList.setText(String.format("%.2f", changeRate));
            tradeValue24HStr = String.format("%.0f", tradeValue24HDouble);
            tradeValue24H = Long.parseLong(tradeValue24HStr);
            tvTradeValue24H.setText(tradeValue24H / 1000000 + "백만");
        }


        private void changeTextColor(double changeRate) {
            if (changeRate == 0.00) {
                tvChangeRateList.setTextColor(Color.BLACK);
                tvCurrentPriceList.setTextColor(Color.BLACK);
                tvTradeValue24H.setTextColor(Color.BLACK);
            } else if (changeRate < 0.00) {
                tvChangeRateList.setTextColor(Color.BLUE);
                tvCurrentPriceList.setTextColor(Color.BLUE);
                tvTradeValue24H.setTextColor(Color.BLUE);
            } else if (changeRate > 0.00) {
                tvChangeRateList.setTextColor(Color.RED);
                tvCurrentPriceList.setTextColor(Color.RED);
                tvTradeValue24H.setTextColor(Color.RED);
            }
        }

    }

}
