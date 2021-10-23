package kr.or.mrhi.myCoin.adapter;

import static kr.or.mrhi.myCoin.MainActivity.namePositionMap;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kr.or.mrhi.myCoin.POJO.TickerData;
import kr.or.mrhi.myCoin.POJO.TickerPOJOData;
import kr.or.mrhi.myCoin.POJO.TransactionData;
import kr.or.mrhi.myCoin.R;
import kr.or.mrhi.myCoin.model.Transaction;

public class WalletAdapter extends BaseAdapter {
    private String currentPrice = null;
    List<String> searchList;
    private List<Transaction> transactionList;
    private TickerData tickerCoin;
    Context context;
    TextView myCoinName, myCoinMarginCount, myCoinPercentCount, myCoinAmountCount, myCoinAveragePriceCount, myCoinValueCount, myCoinTicker, myCoinBuyPriceCount;

    public WalletAdapter(List<Transaction> transactionList) {
        this.transactionList = transactionList;

    }

    @Override
    public int getCount() {
        return transactionList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        double curruntPrice = 0.0;

        context = viewGroup.getContext();
        view = LayoutInflater.from(context).inflate(R.layout.my_coin_item, viewGroup, false);

        myCoinName = view.findViewById(R.id.myCoinName);
        myCoinMarginCount = view.findViewById(R.id.myCoinMarginCount);
        myCoinPercentCount = view.findViewById(R.id.myCoinPercentCount);
        myCoinAmountCount = view.findViewById(R.id.myCoinAmountCount);
        myCoinAveragePriceCount = view.findViewById(R.id.myCoinAveragePriceCount);
        myCoinValueCount = view.findViewById(R.id.myCoinValueCount);
        myCoinBuyPriceCount = view.findViewById(R.id.myCoinBuyPriceCount);
        //
        myCoinTicker = view.findViewById(R.id.myCoinTicker);


        if (i < transactionList.size() && tickerCoin != null && !searchList.isEmpty()) {
            currentPrice = String.valueOf(transactionList.get(i));
            myCoinTicker.setText("(" + searchList.get(i) + ")");
            if (searchList.get(i).equals("BTC")) {
                myCoinName.setText("비트코인");
                //평가손익 = 그때 당시 가격 * 개수 - 지금 가격 * 개수
                myCoinMarginCount.setText(String.format("%.0f", (Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice()) -
                        Double.parseDouble(currentPrice) * Double.parseDouble(transactionList.get(i).getQuantity()))));
                //수익률 (현재가 * 개수)
                myCoinPercentCount.setText(String.format("%.0f", (Double.parseDouble(currentPrice) *
                        Double.parseDouble(transactionList.get(i).getQuantity())) -
                        Double.parseDouble(transactionList.get(i).getQuantity()) * 100));
                //보유수량
                myCoinAmountCount.setText(transactionList.get(i).getQuantity());
                //매수평균가
                myCoinAveragePriceCount.setText(String.format("%.0f", Double.parseDouble(transactionList.get(i).getPrice())
                        / Double.parseDouble(transactionList.get(i).getQuantity())));
                //평가금액
                myCoinValueCount.setText(String.format("%.0f", (Double.parseDouble(currentPrice)
                        * Double.parseDouble(transactionList.get(i).getQuantity()))));
                //매수금액
                myCoinBuyPriceCount.setText(String.format("%.0f", (Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice()))));
            } else if (searchList.get(i).equals("ETH")) {
                myCoinName.setText("이더리움");
                myCoinMarginCount.setText(String.format("%.0f", (Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice()) -
                        Double.parseDouble(currentPrice) * Double.parseDouble(transactionList.get(i).getQuantity()))));
                myCoinPercentCount.setText(String.format("%.0f", (Double.parseDouble(currentPrice) *
                        Double.parseDouble(transactionList.get(i).getQuantity())) -
                        Double.parseDouble(transactionList.get(i).getQuantity()) * 100));
                myCoinAmountCount.setText(transactionList.get(i).getQuantity());
                myCoinAveragePriceCount.setText(String.format("%.0f", Double.parseDouble(transactionList.get(i).getPrice())
                        / Double.parseDouble(transactionList.get(i).getQuantity())));
                myCoinValueCount.setText(String.format("%.0f", (Double.parseDouble(currentPrice)
                        * Double.parseDouble(transactionList.get(i).getQuantity()))));
                myCoinBuyPriceCount.setText(String.format("%.0f", (Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice()))));
            } else if (searchList.get(i).equals("BCH")) {
                myCoinName.setText("비트코인캐시");
                myCoinMarginCount.setText(String.format("%.0f", (Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice()) -
                        Double.parseDouble(currentPrice) * Double.parseDouble(transactionList.get(i).getQuantity()))));
                myCoinPercentCount.setText(String.format("%.0f", (Double.parseDouble(currentPrice) *
                        Double.parseDouble(transactionList.get(i).getQuantity())) -
                        Double.parseDouble(transactionList.get(i).getQuantity()) * 100));
                myCoinAmountCount.setText(transactionList.get(i).getQuantity());
                myCoinAveragePriceCount.setText(String.format("%.0f", Double.parseDouble(transactionList.get(i).getPrice())
                        / Double.parseDouble(transactionList.get(i).getQuantity())));
                myCoinValueCount.setText(String.format("%.0f", (Double.parseDouble(currentPrice)
                        * Double.parseDouble(transactionList.get(i).getQuantity()))));
                myCoinBuyPriceCount.setText(String.format("%.0f", (Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice()))));
            } else if (searchList.get(i).equals("LTC")) {
                myCoinName.setText("라이트코인");
                myCoinMarginCount.setText(String.format("%.0f", (Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice()) -
                        Double.parseDouble(currentPrice) * Double.parseDouble(transactionList.get(i).getQuantity()))));
                myCoinPercentCount.setText(String.format("%.0f", (Double.parseDouble(currentPrice) *
                        Double.parseDouble(transactionList.get(i).getQuantity())) -
                        Double.parseDouble(transactionList.get(i).getQuantity()) * 100));
                myCoinAmountCount.setText(transactionList.get(i).getQuantity());
                myCoinAveragePriceCount.setText(String.format("%.0f", Double.parseDouble(transactionList.get(i).getPrice())
                        / Double.parseDouble(transactionList.get(i).getQuantity())));
                myCoinValueCount.setText(String.format("%.0f", (Double.parseDouble(currentPrice)
                        * Double.parseDouble(transactionList.get(i).getQuantity()))));
                myCoinBuyPriceCount.setText(String.format("%.0f", (Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice()))));
            } else if (searchList.get(i).equals("BSV")) {
                myCoinName.setText("비트코인에스브이");
                myCoinMarginCount.setText(String.format("%.0f", (Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice()) -
                        Double.parseDouble(currentPrice) * Double.parseDouble(transactionList.get(i).getQuantity()))));
                myCoinPercentCount.setText(String.format("%.0f", (Double.parseDouble(currentPrice) *
                        Double.parseDouble(transactionList.get(i).getQuantity())) -
                        Double.parseDouble(transactionList.get(i).getQuantity()) * 100));
                myCoinAmountCount.setText(transactionList.get(i).getQuantity());
                myCoinAveragePriceCount.setText(String.format("%.0f", Double.parseDouble(transactionList.get(i).getPrice())
                        / Double.parseDouble(transactionList.get(i).getQuantity())));
                myCoinValueCount.setText(String.format("%.0f", (Double.parseDouble(currentPrice)
                        * Double.parseDouble(transactionList.get(i).getQuantity()))));
                myCoinBuyPriceCount.setText(String.format("%.0f", (Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice()))));
            } else if (searchList.get(i).equals("AXS")) {
                myCoinName.setText("엑시인피니티");
                myCoinMarginCount.setText(String.format("%.0f", (Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice()) -
                        Double.parseDouble(currentPrice) * Double.parseDouble(transactionList.get(i).getQuantity()))));
                myCoinPercentCount.setText(String.format("%.0f", (Double.parseDouble(currentPrice) *
                        Double.parseDouble(transactionList.get(i).getQuantity())) -
                        Double.parseDouble(transactionList.get(i).getQuantity()) * 100));
                myCoinAmountCount.setText(transactionList.get(i).getQuantity());
                myCoinAveragePriceCount.setText(String.format("%.0f", Double.parseDouble(transactionList.get(i).getPrice())
                        / Double.parseDouble(transactionList.get(i).getQuantity())));
                myCoinValueCount.setText(String.format("%.0f", (Double.parseDouble(currentPrice)
                        * Double.parseDouble(transactionList.get(i).getQuantity()))));
                myCoinBuyPriceCount.setText(String.format("%.0f", (Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice()))));
            } else if (searchList.get(i).equals("BTG")) {
                myCoinName.setText("비트코인골드");
                myCoinMarginCount.setText(String.format("%.0f", (Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice()) -
                        Double.parseDouble(currentPrice) * Double.parseDouble(transactionList.get(i).getQuantity()))));
                myCoinPercentCount.setText(String.format("%.0f", (Double.parseDouble(currentPrice) *
                        Double.parseDouble(transactionList.get(i).getQuantity())) -
                        Double.parseDouble(transactionList.get(i).getQuantity()) * 100));
                myCoinAmountCount.setText(transactionList.get(i).getQuantity());
                myCoinAveragePriceCount.setText(String.format("%.0f", Double.parseDouble(transactionList.get(i).getPrice())
                        / Double.parseDouble(transactionList.get(i).getQuantity())));
                myCoinValueCount.setText(String.format("%.0f", (Double.parseDouble(currentPrice)
                        * Double.parseDouble(transactionList.get(i).getQuantity()))));
                myCoinBuyPriceCount.setText(String.format("%.0f", (Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice()))));
            } else if (searchList.get(i).equals("ETC")) {
                myCoinName.setText("이더리움클래식");
                myCoinMarginCount.setText(String.format("%.0f", (Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice()) -
                        Double.parseDouble(currentPrice) * Double.parseDouble(transactionList.get(i).getQuantity()))));
                myCoinPercentCount.setText(String.format("%.0f", (Double.parseDouble(currentPrice) *
                        Double.parseDouble(transactionList.get(i).getQuantity())) -
                        Double.parseDouble(transactionList.get(i).getQuantity()) * 100));
                myCoinAmountCount.setText(transactionList.get(i).getQuantity());
                myCoinAveragePriceCount.setText(String.format("%.0f", Double.parseDouble(transactionList.get(i).getPrice())
                        / Double.parseDouble(transactionList.get(i).getQuantity())));
                myCoinValueCount.setText(String.format("%.0f", (Double.parseDouble(currentPrice)
                        * Double.parseDouble(transactionList.get(i).getQuantity()))));
                myCoinBuyPriceCount.setText(String.format("%.0f", (Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice()))));
            } else if (searchList.get(i).equals("DOT")) {
                myCoinName.setText("폴카닷");
                myCoinMarginCount.setText(String.format("%.0f", (Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice()) -
                        Double.parseDouble(currentPrice) * Double.parseDouble(transactionList.get(i).getQuantity()))));
                myCoinPercentCount.setText(String.format("%.0f", (Double.parseDouble(currentPrice) *
                        Double.parseDouble(transactionList.get(i).getQuantity())) -
                        Double.parseDouble(transactionList.get(i).getQuantity()) * 100));
                myCoinAmountCount.setText(transactionList.get(i).getQuantity());
                myCoinAveragePriceCount.setText(String.format("%.0f", Double.parseDouble(transactionList.get(i).getPrice())
                        / Double.parseDouble(transactionList.get(i).getQuantity())));
                myCoinValueCount.setText(String.format("%.0f", (Double.parseDouble(currentPrice)
                        * Double.parseDouble(transactionList.get(i).getQuantity()))));
                myCoinBuyPriceCount.setText(String.format("%.0f", (Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice()))));
            } else if (searchList.get(i).equals("ATOM")) {
                myCoinName.setText("코스모스");
                myCoinMarginCount.setText(String.format("%.0f", (Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice()) -
                        Double.parseDouble(currentPrice) * Double.parseDouble(transactionList.get(i).getQuantity()))));
                myCoinPercentCount.setText(String.format("%.0f", (Double.parseDouble(currentPrice) *
                        Double.parseDouble(transactionList.get(i).getQuantity())) -
                        Double.parseDouble(transactionList.get(i).getQuantity()) * 100));
                myCoinAmountCount.setText(transactionList.get(i).getQuantity());
                myCoinAveragePriceCount.setText(String.format("%.0f", Double.parseDouble(transactionList.get(i).getPrice())
                        / Double.parseDouble(transactionList.get(i).getQuantity())));
                myCoinValueCount.setText(String.format("%.0f", (Double.parseDouble(currentPrice)
                        * Double.parseDouble(transactionList.get(i).getQuantity()))));
                myCoinBuyPriceCount.setText(String.format("%.0f", (Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice()))));
            } else if (searchList.get(i).equals("WAVES")) {
                myCoinName.setText("웨이브");
                myCoinMarginCount.setText(String.format("%.0f", (Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice()) -
                        Double.parseDouble(currentPrice) * Double.parseDouble(transactionList.get(i).getQuantity()))));
                myCoinPercentCount.setText(String.format("%.0f", (Double.parseDouble(currentPrice) *
                        Double.parseDouble(transactionList.get(i).getQuantity())) -
                        Double.parseDouble(transactionList.get(i).getQuantity()) * 100));
                myCoinAmountCount.setText(transactionList.get(i).getQuantity());
                myCoinAveragePriceCount.setText(String.format("%.0f", Double.parseDouble(transactionList.get(i).getPrice())
                        / Double.parseDouble(transactionList.get(i).getQuantity())));
                myCoinValueCount.setText(String.format("%.0f", (Double.parseDouble(currentPrice)
                        * Double.parseDouble(transactionList.get(i).getQuantity()))));
                myCoinBuyPriceCount.setText(String.format("%.0f", (Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice()))));
            } else if (searchList.get(i).equals("LINK")) {
                myCoinName.setText("체인링크");
                myCoinMarginCount.setText(String.format("%.0f", (Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice()) -
                        Double.parseDouble(currentPrice) * Double.parseDouble(transactionList.get(i).getQuantity()))));
                myCoinPercentCount.setText(String.format("%.0f", (Double.parseDouble(currentPrice) *
                        Double.parseDouble(transactionList.get(i).getQuantity())) -
                        Double.parseDouble(transactionList.get(i).getQuantity()) * 100));
                myCoinAmountCount.setText(transactionList.get(i).getQuantity());
                myCoinAveragePriceCount.setText(String.format("%.0f", Double.parseDouble(transactionList.get(i).getPrice())
                        / Double.parseDouble(transactionList.get(i).getQuantity())));
                myCoinValueCount.setText(String.format("%.0f", (Double.parseDouble(currentPrice)
                        * Double.parseDouble(transactionList.get(i).getQuantity()))));
                myCoinBuyPriceCount.setText(String.format("%.0f", (Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice()))));
            } else if (searchList.get(i).equals("REP")) {
                myCoinName.setText("어거");
                myCoinMarginCount.setText(String.format("%.0f", (Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice()) -
                        Double.parseDouble(currentPrice) * Double.parseDouble(transactionList.get(i).getQuantity()))));
                myCoinPercentCount.setText(String.format("%.0f", (Double.parseDouble(currentPrice) *
                        Double.parseDouble(transactionList.get(i).getQuantity())) -
                        Double.parseDouble(transactionList.get(i).getQuantity()) * 100));
                myCoinAmountCount.setText(transactionList.get(i).getQuantity());
                myCoinAveragePriceCount.setText(String.format("%.0f", Double.parseDouble(transactionList.get(i).getPrice())
                        / Double.parseDouble(transactionList.get(i).getQuantity())));
                myCoinValueCount.setText(String.format("%.0f", (Double.parseDouble(currentPrice)
                        * Double.parseDouble(transactionList.get(i).getQuantity()))));
                myCoinBuyPriceCount.setText(String.format("%.0f", (Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice()))));
            } else if (searchList.get(i).equals("OMG")) {
                myCoinName.setText("오미세고");
                myCoinMarginCount.setText(String.format("%.0f", (Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice()) -
                        Double.parseDouble(currentPrice) * Double.parseDouble(transactionList.get(i).getQuantity()))));
                myCoinPercentCount.setText(String.format("%.0f", (Double.parseDouble(currentPrice) *
                        Double.parseDouble(transactionList.get(i).getQuantity())) -
                        Double.parseDouble(transactionList.get(i).getQuantity()) * 100));
                myCoinAmountCount.setText(transactionList.get(i).getQuantity());
                myCoinAveragePriceCount.setText(String.format("%.0f", Double.parseDouble(transactionList.get(i).getPrice())
                        / Double.parseDouble(transactionList.get(i).getQuantity())));
                myCoinValueCount.setText(String.format("%.0f", (Double.parseDouble(currentPrice)
                        * Double.parseDouble(transactionList.get(i).getQuantity()))));
                myCoinBuyPriceCount.setText(String.format("%.0f", (Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice()))));
            } else if (searchList.get(i).equals("QTUM")) {
                myCoinName.setText("퀀텀");
                myCoinMarginCount.setText(String.format("%.0f", (Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice()) -
                        Double.parseDouble(currentPrice) * Double.parseDouble(transactionList.get(i).getQuantity()))));
                myCoinPercentCount.setText(String.format("%.0f", (Double.parseDouble(currentPrice) *
                        Double.parseDouble(transactionList.get(i).getQuantity())) -
                        Double.parseDouble(transactionList.get(i).getQuantity()) * 100));
                myCoinAmountCount.setText(transactionList.get(i).getQuantity());
                myCoinAveragePriceCount.setText(String.format("%.0f", Double.parseDouble(transactionList.get(i).getPrice())
                        / Double.parseDouble(transactionList.get(i).getQuantity())));
                myCoinValueCount.setText(String.format("%.0f", (Double.parseDouble(currentPrice)
                        * Double.parseDouble(transactionList.get(i).getQuantity()))));
                myCoinBuyPriceCount.setText(String.format("%.0f", (Double.parseDouble(transactionList.get(i).getQuantity()) *
                        Double.parseDouble(transactionList.get(i).getPrice()))));
            }
        }
        return view;
    }
}
