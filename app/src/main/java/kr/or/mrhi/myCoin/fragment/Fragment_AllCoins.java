package kr.or.mrhi.myCoin.fragment;


import static kr.or.mrhi.myCoin.MainActivity.namePositionMap;
import static kr.or.mrhi.myCoin.MainActivity.stringSymbol;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private RecyclerView recyclerView2;
    private MainCoinAdapter mainCoinAdapter;
    private List<String> priceList;
    private List<String> searchList;
    private CoinViewModel model;


    @SuppressLint("FragmentLiveDataObserve")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment1_2, container, false);
        // Inflate the layout for this fragment
        model = new ViewModelProvider(requireActivity()).get(CoinViewModel.class);
        priceList = new ArrayList<>();
        searchList = new ArrayList<>();
        for (int i = 0; i < stringSymbol.length; i++) {
            priceList.add("0.00");
        }
        recyclerView2 = view.findViewById(R.id.recyclerView2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(requireActivity()));
        mainCoinAdapter = new MainCoinAdapter(priceList, searchList);
        recyclerView2.setAdapter(mainCoinAdapter);

        model.getTickerCoinData().observe(requireActivity(), new Observer<TickerData>() {
            @Override
            public void onChanged(TickerData tickerData) {
                mainCoinAdapter.setTickerData(tickerData);
                mainCoinAdapter.notifyDataSetChanged();
            }
        });

        model.getTransactionCoinData("BTC").observe(requireActivity(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> transactionData) {

                priceList.removeAll(priceList);
                if (searchList.size()!=0){
                    for (int i = 0; i< searchList.size(); i++){
                        priceList.add(transactionData.get(namePositionMap.get(searchList.get(i))));
                    }
                }else{
                    priceList.addAll(transactionData);
                }
                mainCoinAdapter.notifyDataSetChanged();
            }
        });
        model.refrashTransactionDataThread(stringSymbol);

        model.getSearchName().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                searchList.removeAll(searchList);
                for (int i=0; i<stringSymbol.length; i++){
                    if (stringSymbol[i].contains(s)){
                        searchList.add(stringSymbol[i]);
                    }
                }
                mainCoinAdapter.notifyDataSetChanged();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        model.stopThread();
    }
}