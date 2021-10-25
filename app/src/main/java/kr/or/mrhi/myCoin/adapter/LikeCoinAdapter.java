package kr.or.mrhi.myCoin.adapter;


import static kr.or.mrhi.myCoin.MainActivity.namePositionMap;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kr.or.mrhi.myCoin.CoinMain;
import kr.or.mrhi.myCoin.POJO.TickerData;
import kr.or.mrhi.myCoin.R;

public class LikeCoinAdapter extends RecyclerView.Adapter<LikeCoinAdapter.ViewHolders> {
    private List<String> transactionCoin;
    private TickerData tickerCoin;
    private List<String> list;
    private double changeRate;

    public LikeCoinAdapter(List<String> transactionCoin, TickerData tickerCoin, List<String> list) {
        this.transactionCoin = transactionCoin;
        this.tickerCoin = tickerCoin;
        this.list = list;
    }

    @NonNull
    @Override
    public LikeCoinAdapter.ViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coinlist_item,parent,false);
        return new ViewHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LikeCoinAdapter.ViewHolders holder, int position) {
        holder.onBind(position);
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setTickerData(TickerData tickerData) {
        this.tickerCoin = tickerData;
    }

    public class ViewHolders extends RecyclerView.ViewHolder {
            TextView tvCoinNameList,tvCurrentPriceList,tvChangeRateList,tvTotalVolumeList,tvCoinNameList2;
        public ViewHolders(@NonNull View itemView) {
            super(itemView);
            tvCoinNameList = itemView.findViewById(R.id.tvCoinNameList);
            tvCurrentPriceList = itemView.findViewById(R.id.tvCurrentPriceList);
            tvChangeRateList = itemView.findViewById(R.id.tvChangeRateList);
            tvTotalVolumeList = itemView.findViewById(R.id.tvTotalVolumeList);
            tvCoinNameList2 = itemView.findViewById(R.id.tvCoinNameList2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//
                        int position = getAdapterPosition();
                        Intent intent = new Intent(view.getContext(), CoinMain.class);
                        intent.putExtra("CoinID", list.get(position));
                        intent.putExtra("CoinData", transactionCoin.get(position));
                        intent.putExtra("Position", namePositionMap.get(list.get(position)));
                        view.getContext().startActivity(intent);
//
                }
            });
        }

        public void onBind(int position) {
            long volume = 0;
            double volumeList = 0.0;
            String totalVolumeList = null;
            String prevClosingPrice = "0.0";
            String currentPrice = null;


            if (position < transactionCoin.size() && tickerCoin != null && !list.isEmpty()) {
                currentPrice = transactionCoin.get(position);
                tvCurrentPriceList.setText(currentPrice);
                tvCoinNameList2.setText(list.get(position) + "/KRW");
                if (list.get(position).equals("BTC")) {
                    tvCoinNameList.setText("비트코인");
                    prevClosingPrice = tickerCoin.getBtc().getClosingPrice();
                    volumeList = Double.parseDouble(tickerCoin.getBtc().getAccTradeValue24H());
                } else if (list.get(position).equals("ETH")) {
                    tvCoinNameList.setText("이더리움");
                    prevClosingPrice = tickerCoin.getEth().getPrevClosingPrice();
                    volumeList = Double.parseDouble(tickerCoin.getEth().getAccTradeValue24H());
                } else if (list.get(position).equals("BCH")) {
                    tvCoinNameList.setText("비트코인캐시");
                    prevClosingPrice = tickerCoin.getBch().getPrevClosingPrice();
                    volumeList = Double.parseDouble(tickerCoin.getBch().getAccTradeValue24H());
                } else if (list.get(position).equals("LTC")) {
                    tvCoinNameList.setText("라이트코인");
                    prevClosingPrice = tickerCoin.getLtc().getPrevClosingPrice();
                    volumeList = Double.parseDouble(tickerCoin.getLtc().getAccTradeValue24H());
                } else if (list.get(position).equals("BSV")) {
                    tvCoinNameList.setText("비트코인에스브이");
                    prevClosingPrice = tickerCoin.getBsv().getPrevClosingPrice();
                    volumeList = Double.parseDouble(tickerCoin.getBsv().getAccTradeValue24H());
                } else if (list.get(position).equals("AXS")) {
                    tvCoinNameList.setText("엑시인피니티");
                    prevClosingPrice = tickerCoin.getAxs().getPrevClosingPrice();
                    volumeList = Double.parseDouble(tickerCoin.getAxs().getAccTradeValue24H());
                } else if (list.get(position).equals("BTG")) {
                    tvCoinNameList.setText("비트코인골드");
                    prevClosingPrice = tickerCoin.getBtg().getPrevClosingPrice();
                    volumeList = Double.parseDouble(tickerCoin.getBtg().getAccTradeValue24H());
                } else if (list.get(position).equals("ETC")) {
                    tvCoinNameList.setText("이더리움 클래식");
                    prevClosingPrice = tickerCoin.getEtc().getPrevClosingPrice();
                    volumeList = Double.parseDouble(tickerCoin.getEtc().getAccTradeValue24H());
                } else if (list.get(position).equals("DOT")) {
                    tvCoinNameList.setText("폴카닷");
                    prevClosingPrice = tickerCoin.getDot().getPrevClosingPrice();
                    volumeList = Double.parseDouble(tickerCoin.getDot().getAccTradeValue24H());
                } else if (list.get(position).equals("ATOM")) {
                    tvCoinNameList.setText("코스모스");
                    prevClosingPrice = tickerCoin.getAtom().getPrevClosingPrice();
                    volumeList = Double.parseDouble(tickerCoin.getAtom().getAccTradeValue24H());
                } else if (list.get(position).equals("WAVES")) {
                    tvCoinNameList.setText("웨이브");
                    prevClosingPrice = tickerCoin.getWaves().getPrevClosingPrice();
                    volumeList = Double.parseDouble(tickerCoin.getWaves().getAccTradeValue24H());
                } else if (list.get(position).equals("LINK")) {
                    tvCoinNameList.setText("체인링크");
                    prevClosingPrice = tickerCoin.getLink().getPrevClosingPrice();
                    volumeList = Double.parseDouble(tickerCoin.getLink().getAccTradeValue24H());
                } else if (list.get(position).equals("REP")) {
                    tvCoinNameList.setText("어거");
                    prevClosingPrice = tickerCoin.getRep().getPrevClosingPrice();
                    volumeList = Double.parseDouble(tickerCoin.getRep().getAccTradeValue24H());
                } else if (list.get(position).equals("OMG")) {
                    tvCoinNameList.setText("오미세고");
                    prevClosingPrice = tickerCoin.getOmg().getPrevClosingPrice();
                    volumeList = Double.parseDouble(tickerCoin.getOmg().getAccTradeValue24H());
                } else if (list.get(position).equals("QTUM")) {
                    tvCoinNameList.setText("퀀텀");
                    prevClosingPrice = tickerCoin.getQtum().getPrevClosingPrice();
                    volumeList = Double.parseDouble(tickerCoin.getQtum().getAccTradeValue24H());
                }
//                Log.i("대체 왜",prevClosingPrice));
//                Log.i("대체 왜",currentPrice);
                changeRate = (Double.parseDouble(currentPrice) - Double.parseDouble(prevClosingPrice)) / Double.parseDouble(prevClosingPrice) * 100;
                tvChangeRateList.setText(String.format("%.2f", changeRate));
                totalVolumeList = String.format("%.0f", volumeList);
                volume = Long.parseLong(totalVolumeList);
                tvTotalVolumeList.setText(String.valueOf(volume / 1000000) + "백만");
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
