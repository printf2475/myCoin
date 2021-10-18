package kr.or.mrhi.myCoin.fragment;

import static kr.or.mrhi.myCoin.MainActivity.stringSymbol;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import kr.or.mrhi.myCoin.adapter.MainCoinAdapter;
import kr.or.mrhi.myCoin.POJO.TickerData;
import kr.or.mrhi.myCoin.R;
import kr.or.mrhi.myCoin.viewModel.CoinViewModel;


public class Fragment_AllCoins extends Fragment {
    private Context context;
    private RecyclerView recyclerView2;
    private MainCoinAdapter mainCoinAdapter;
    private List<String> priceList;
    private TickerData ticker;

//    public static Boolean TRANSACTIONFLAG=false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.i("에러줘", "탭레이아웃 들어옴");
        View view = inflater.inflate(R.layout.fragment_fragment1_2, container, false);
        // Inflate the layout for this fragment
        CoinViewModel model = new ViewModelProvider(requireActivity()).get(CoinViewModel.class);

        priceList = new ArrayList<>();
        for (int i = 0; i< stringSymbol.length; i++){
            priceList.add("0.00");
        }
        recyclerView2 = view.findViewById(R.id.recyclerView2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(requireActivity()));
        mainCoinAdapter = new MainCoinAdapter(priceList , ticker);
        recyclerView2.setAdapter(mainCoinAdapter);

        model.getTickerCoinData().observe(requireActivity(), new Observer<TickerData>() {
            @Override
            public void onChanged(TickerData tickerData) {
                ticker=tickerData;
                mainCoinAdapter.notifyDataSetChanged();
            }
        });

        model.getTransactionCoinData("BTC").observe(requireActivity(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> transactionData) {
                priceList.removeAll(priceList);
                priceList.addAll(transactionData);
                mainCoinAdapter.notifyDataSetChanged();
            }
        });
        model.refrashTransactionDataThread(stringSymbol);

        return view;

    }
}