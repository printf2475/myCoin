package kr.or.mrhi.myCoin.adapter;

import static kr.or.mrhi.myCoin.MainActivity.namePositionMap;
import static kr.or.mrhi.myCoin.MainActivity.stringSymbol;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
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
    private double changeRate;
    private int num;
//    private TickerPOJOData tickerPOJOData;


    public MainCoinAdapter(List<String> transactionCoin) {
        this.transactionCoin = transactionCoin;
//        this.tickerCoin = tickerData;
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
        return transactionCoin.size();
    }

    public void setTickerData(TickerData tickerData) {
        this.tickerCoin = tickerData;
    }


    public class ViewHolders extends RecyclerView.ViewHolder {
        TextView tvCoinNameList, tvCurrentPriceList, tvChangeRateList, tvTotalVolumeList;

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
//            DecimalFormat formatter = new DecimalFormat("###,###,###");


            String prevClosingPrice = null;
            String currentPrice = transactionCoin.get(position);
//            changeRate = (Double.parseDouble(transactionCoin.get(position)) - Double.parseDouble(tickerCoin.getLtc().getPrevClosingPrice())) / Double.parseDouble(tickerCoin.getLtc().getPrevClosingPrice()) * 100;
            if (position < transactionCoin.size() && tickerCoin != null) {
                tvCoinNameList.setText(stringSymbol[position]);
                tvCurrentPriceList.setText(transactionCoin.get(position));
                if (tvCoinNameList.getText().equals("BTC")) {
                    prevClosingPrice = tickerCoin.getBtc().getClosingPrice();
                } else if (tvCoinNameList.getText().equals("ETH")) {
                    prevClosingPrice = tickerCoin.getEth().getPrevClosingPrice();
//                    changeRate = Double.parseDouble(currentPrice) - Double.parseDouble(s) / Double.parseDouble(s) * 100;
//                    tvChangeRateList.setText(String.valueOf(String.format("%.2f", (Double.parseDouble(transactionCoin.get(position)) - Double.parseDouble(prevClosingPrice)) / Double.parseDouble(prevClosingPrice) * 100)));
//                    tvTotalVolumeList.setText(formatter.format(Double.parseDouble((String.format("%6.2f",tickerCoin.getEth().getAccTradeValue24H())))+"백만"));
//                    tvTotalVolumeList.setText(String.format("%")tickerCoin.getEth().getAccTradeValue24H()+"백만");
                } else if (tvCoinNameList.getText().equals("BCH")) {
                   prevClosingPrice = tickerCoin.getBch().getPrevClosingPrice();
                } else if (tvCoinNameList.getText().equals("LTC")) {
                    prevClosingPrice = tickerCoin.getLtc().getPrevClosingPrice();
                } else if (tvCoinNameList.getText().equals("BSV")) {
                    prevClosingPrice = tickerCoin.getBsv().getPrevClosingPrice();
                }else if (tvCoinNameList.getText().equals("AXS")) {
                    prevClosingPrice = tickerCoin.getAxs().getPrevClosingPrice();
                }else if (tvCoinNameList.getText().equals("BTG")) {
                    prevClosingPrice = tickerCoin.getBtg().getPrevClosingPrice();
                }else if (tvCoinNameList.getText().equals("ETC")) {
                    prevClosingPrice = tickerCoin.getEtc().getPrevClosingPrice();
                }else if (tvCoinNameList.getText().equals("DOT")) {
                    prevClosingPrice = tickerCoin.getDot().getPrevClosingPrice();
                }else if (tvCoinNameList.getText().equals("ATOM")) {
                    prevClosingPrice = tickerCoin.getAtom().getPrevClosingPrice();
                }else if (tvCoinNameList.getText().equals("WAVES")) {
                    prevClosingPrice = tickerCoin.getWaves().getPrevClosingPrice();
                }else if (tvCoinNameList.getText().equals("LINK")) {
                    prevClosingPrice = tickerCoin.getLink().getPrevClosingPrice();
                }else if (tvCoinNameList.getText().equals("REP")) {
                    prevClosingPrice = tickerCoin.getRep().getPrevClosingPrice();
                }else if (tvCoinNameList.getText().equals("OMG")) {
                    prevClosingPrice = tickerCoin.getOmg().getPrevClosingPrice();
                }else if (tvCoinNameList.getText().equals("QTUM")) {
                    prevClosingPrice = tickerCoin.getQtum().getPrevClosingPrice();
                }
                changeRate = (Double.parseDouble(currentPrice) - Double.parseDouble(prevClosingPrice)) / Double.parseDouble(prevClosingPrice) * 100;
                tvChangeRateList.setText(String.format("%.2f",changeRate));
                if (changeRate == 0.00) {
                    tvChangeRateList.setTextColor(Color.BLACK);
                    tvCurrentPriceList.setTextColor(Color.BLACK);
                    tvTotalVolumeList.setTextColor(Color.BLACK);
                } else if (changeRate < 0.00) {
                    tvChangeRateList.setTextColor(Color.BLUE);
                    tvCurrentPriceList.setTextColor(Color.BLUE);
                    tvTotalVolumeList.setTextColor(Color.BLUE);
                } else if (changeRate > 0.00) {
                    tvChangeRateList.setTextColor(Color.RED);
                    tvCurrentPriceList.setTextColor(Color.RED);
                    tvTotalVolumeList.setTextColor(Color.RED);
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
