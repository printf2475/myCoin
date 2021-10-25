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
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kr.or.mrhi.myCoin.CoinMain;
import kr.or.mrhi.myCoin.POJO.TickerData;
import kr.or.mrhi.myCoin.R;

public class MainCoinAdapter extends RecyclerView.Adapter<MainCoinAdapter.ViewHolders> {
    private List<String> transactionCoin;
    List<String> searchList;

    private TickerData tickerCoin;
    private double changeRate;

    public MainCoinAdapter(List<String> transactionCoin, List<String> priceListSearch) {
        this.transactionCoin = transactionCoin;
        this.searchList = priceListSearch;
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
        return searchList.size();
    }

    public void setTickerData(TickerData tickerData) {
        this.tickerCoin = tickerData;
    }


    public class ViewHolders extends RecyclerView.ViewHolder {
        TextView tvCoinNameList, tvCoinNameList2, tvCurrentPriceList, tvChangeRateList, tvTotalVolumeList;

        public ViewHolders(@NonNull View itemView) {
            super(itemView);
            tvCoinNameList = itemView.findViewById(R.id.tvCoinNameList);
            tvCoinNameList2 = itemView.findViewById(R.id.tvCoinNameList2);
            tvCurrentPriceList = itemView.findViewById(R.id.tvCurrentPriceList);
            tvChangeRateList = itemView.findViewById(R.id.tvChangeRateList);
            tvTotalVolumeList = itemView.findViewById(R.id.tvTotalVolumeList);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position >= 0 && position < searchList.size()) {
                        Intent intent = new Intent(view.getContext(), CoinMain.class);
                        intent.putExtra("CoinID", stringSymbol[namePositionMap.get(searchList.get(position))]);
                        intent.putExtra("CoinData", transactionCoin.get(position));
                        intent.putExtra("Position", namePositionMap.get(stringSymbol[position]));
                        view.getContext().startActivity(intent);
                    }

                }
            });
        }

        private void onBind(int position) {
            long volume = 0;
            double volumeList = 0.0;
            String totalVolumeList = null;
            String prevClosingPrice = "0.0";
            String currentPrice = null;
            if (position < transactionCoin.size() && tickerCoin != null && !searchList.isEmpty()) {
                currentPrice = transactionCoin.get(position);
                tvCurrentPriceList.setText(currentPrice);
                tvCoinNameList2.setText(searchList.get(position) + "/KRW");
                if (searchList.get(position).equals("BTC")) {
                    tvCoinNameList.setText("비트코인");
                    prevClosingPrice = tickerCoin.getBtc().getClosingPrice();
                    volumeList = Double.parseDouble(tickerCoin.getBtc().getAccTradeValue24H());
                } else if (searchList.get(position).equals("ETH")) {
                    tvCoinNameList.setText("이더리움");
                    prevClosingPrice = tickerCoin.getEth().getPrevClosingPrice();
                    volumeList = Double.parseDouble(tickerCoin.getEth().getAccTradeValue24H());
                } else if (searchList.get(position).equals("BCH")) {
                    tvCoinNameList.setText("비트코인캐시");
                    prevClosingPrice = tickerCoin.getBch().getPrevClosingPrice();
                    volumeList = Double.parseDouble(tickerCoin.getBch().getAccTradeValue24H());
                } else if (searchList.get(position).equals("LTC")) {
                    tvCoinNameList.setText("라이트코인");
                    prevClosingPrice = tickerCoin.getLtc().getPrevClosingPrice();
                    volumeList = Double.parseDouble(tickerCoin.getLtc().getAccTradeValue24H());
                } else if (searchList.get(position).equals("BSV")) {
                    tvCoinNameList.setText("비트코인에스브이");
                    prevClosingPrice = tickerCoin.getBsv().getPrevClosingPrice();
                    volumeList = Double.parseDouble(tickerCoin.getBsv().getAccTradeValue24H());
                } else if (searchList.get(position).equals("AXS")) {
                    tvCoinNameList.setText("엑시인피니티");
                    prevClosingPrice = tickerCoin.getAxs().getPrevClosingPrice();
                    volumeList = Double.parseDouble(tickerCoin.getAxs().getAccTradeValue24H());
                } else if (searchList.get(position).equals("BTG")) {
                    tvCoinNameList.setText("비트코인골드");
                    prevClosingPrice = tickerCoin.getBtg().getPrevClosingPrice();
                    volumeList = Double.parseDouble(tickerCoin.getBtg().getAccTradeValue24H());
                } else if (searchList.get(position).equals("ETC")) {
                    tvCoinNameList.setText("이더리움 클래식");
                    prevClosingPrice = tickerCoin.getEtc().getPrevClosingPrice();
                    volumeList = Double.parseDouble(tickerCoin.getEtc().getAccTradeValue24H());
                } else if (searchList.get(position).equals("DOT")) {
                    tvCoinNameList.setText("폴카닷");
                    prevClosingPrice = tickerCoin.getDot().getPrevClosingPrice();
                    volumeList = Double.parseDouble(tickerCoin.getDot().getAccTradeValue24H());
                } else if (searchList.get(position).equals("ATOM")) {
                    tvCoinNameList.setText("코스모스");
                    prevClosingPrice = tickerCoin.getAtom().getPrevClosingPrice();
                    volumeList = Double.parseDouble(tickerCoin.getAtom().getAccTradeValue24H());
                } else if (searchList.get(position).equals("WAVES")) {
                    tvCoinNameList.setText("웨이브");
                    prevClosingPrice = tickerCoin.getWaves().getPrevClosingPrice();
                    volumeList = Double.parseDouble(tickerCoin.getWaves().getAccTradeValue24H());
                } else if (searchList.get(position).equals("LINK")) {
                    tvCoinNameList.setText("체인링크");
                    prevClosingPrice = tickerCoin.getLink().getPrevClosingPrice();
                    volumeList = Double.parseDouble(tickerCoin.getLink().getAccTradeValue24H());
                } else if (searchList.get(position).equals("REP")) {
                    tvCoinNameList.setText("어거");
                    prevClosingPrice = tickerCoin.getRep().getPrevClosingPrice();
                    volumeList = Double.parseDouble(tickerCoin.getRep().getAccTradeValue24H());
                } else if (searchList.get(position).equals("OMG")) {
                    tvCoinNameList.setText("오미세고");
                    prevClosingPrice = tickerCoin.getOmg().getPrevClosingPrice();
                    volumeList = Double.parseDouble(tickerCoin.getOmg().getAccTradeValue24H());
                } else if (searchList.get(position).equals("QTUM")) {
                    tvCoinNameList.setText("퀀텀");
                    prevClosingPrice = tickerCoin.getQtum().getPrevClosingPrice();
                    volumeList = Double.parseDouble(tickerCoin.getQtum().getAccTradeValue24H());
                }
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
            }
        }
    }
}
