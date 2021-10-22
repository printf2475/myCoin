package kr.or.mrhi.myCoin.adapter;


import static kr.or.mrhi.myCoin.MainActivity.namePositionMap;

import android.content.Intent;
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
                        intent.putExtra("CoinID", list.get(position));
                        intent.putExtra("CoinData", transactionCoin.get(position));
                        intent.putExtra("Position", namePositionMap.get(list.get(position)));
                        view.getContext().startActivity(intent);
//
                }
            });
        }

        public void onBind(int position) {
            if (list.size()>position){
                tvCoinNameList.setText(list.get(position));
                tvCurrentPriceList.setText(transactionCoin.get(position));
            }
//            coinCompareYesterday.setText(String.valueOf(currentPrice/closingPrice*100-100));
        }
    }
//    private void startActivity(Intent intent) {
//        startActivity(intent);
//    }
}
