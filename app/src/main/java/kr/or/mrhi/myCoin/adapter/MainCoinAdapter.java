package kr.or.mrhi.myCoin.adapter;

import static kr.or.mrhi.myCoin.activity.MainActivity.namePositionMap;
import static kr.or.mrhi.myCoin.activity.MainActivity.stringName;
import static kr.or.mrhi.myCoin.activity.MainActivity.stringSymbol;

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

public class MainCoinAdapter extends RecyclerView.Adapter<MainCoinAdapter.ViewHolders> {
    private List<String> priceList;
    List<String> nameList;
    private List<TickerDTO> tickerDataList;
    private double changeRate;

    public MainCoinAdapter(List<String> priceList, List<String> nameList, List<TickerDTO> tickerDataList) {
        this.priceList = priceList;
        this.nameList = nameList;
        this.tickerDataList = tickerDataList;
    }

    @NonNull
    @Override
    public MainCoinAdapter.ViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coinlist_item, parent, false);
        return new ViewHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainCoinAdapter.ViewHolders holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }

    public void setTickerData(List<TickerDTO> tickerDataList) {
        this.tickerDataList = tickerDataList;
    }

    public class ViewHolders extends RecyclerView.ViewHolder {
        TextView tvCoinNameList, tvCoinNameList2, tvCurrentPriceList, tvChangeRateList, tvTradeValue24H;
        String tradeValue24HStr, prevClosingPrice, currentPrice;
        double currentPriceDouble, prevClosingPriceDouble;
        long tradeValue24H;

        double tradeValue24HDouble;

        public ViewHolders(@NonNull View itemView) {
            super(itemView);
            tvCoinNameList = itemView.findViewById(R.id.tvCoinNameList);
            tvCoinNameList2 = itemView.findViewById(R.id.tvCoinNameList2);
            tvCurrentPriceList = itemView.findViewById(R.id.tvCurrentPriceList);
            tvChangeRateList = itemView.findViewById(R.id.tvChangeRateList);
            tvTradeValue24H = itemView.findViewById(R.id.tvTotalVolumeList);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position >= 0 && position < nameList.size()) {
                        Intent intent = new Intent(view.getContext(), CoinMain.class);
                        intent.putExtra("CoinID", stringSymbol[namePositionMap.get(nameList.get(position))]);
                        intent.putExtra("CoinData", priceList.get(position));
                        intent.putExtra("Position", namePositionMap.get(stringSymbol[position]));
                        view.getContext().startActivity(intent);
                    }

                }
            });
        }

        private void onBind(int position) {
            prevClosingPrice = null;
            tradeValue24HDouble = 0.0;
            if (position < priceList.size() && tickerDataList != null && !nameList.isEmpty() && !priceList.isEmpty()) {
                initCoinData(position);
                changeTextColor(changeRate);
            }
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

        private void initCoinData(int position) {
            tvCoinNameList.setText(stringName[namePositionMap.get(nameList.get(position))]);
            currentPrice = priceList.get(position);
            tvCurrentPriceList.setText(currentPrice);
            tvCoinNameList2.setText(nameList.get(position) + "/KRW");

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
    }
}
