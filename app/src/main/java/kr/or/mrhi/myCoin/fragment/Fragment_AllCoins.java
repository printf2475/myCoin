package kr.or.mrhi.myCoin.fragment;


import static kr.or.mrhi.myCoin.activity.MainActivity.namePositionMap;
import static kr.or.mrhi.myCoin.activity.MainActivity.stringSymbol;

import android.annotation.SuppressLint;
import android.content.Intent;
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

import kr.or.mrhi.myCoin.model.TickerDTO;
import kr.or.mrhi.myCoin.adapter.MainCoinAdapter;
import kr.or.mrhi.myCoin.R;
import kr.or.mrhi.myCoin.viewModel.CoinViewModel;


public class Fragment_AllCoins extends Fragment {
    private RecyclerView recyclerView2;
    private MainCoinAdapter mainCoinAdapter;
    private List<String> priceList;
    private List<String> nameList;
    private CoinViewModel model;
    private List<TickerDTO> tickerDataList;


    @SuppressLint("FragmentLiveDataObserve")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment1_2, container, false);
        // Inflate the layout for this fragment
        model = new ViewModelProvider(requireActivity()).get(CoinViewModel.class);
        priceList = new ArrayList<>();
        nameList = new ArrayList<>();

        recyclerView2 = view.findViewById(R.id.recyclerView2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(requireActivity()));
        mainCoinAdapter = new MainCoinAdapter(priceList, nameList, tickerDataList);
        recyclerView2.setAdapter(mainCoinAdapter);


        model.getTickerCoinData().observe(requireActivity(), tickerDataList -> {
            mainCoinAdapter.setTickerData(tickerDataList);
            mainCoinAdapter.notifyDataSetChanged();
        });

        model.getTransactionCoinData("BTC").observe(requireActivity(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> transactionData) {
                priceList.removeAll(priceList);
                if (nameList.size()!=0){
                    for (int i = 0; i< nameList.size(); i++){
                        priceList.add(transactionData.get(namePositionMap.get(nameList.get(i))));
                    }
                }else{
                    priceList.addAll(transactionData);
                }
                mainCoinAdapter.notifyDataSetChanged();
            }
        });


        model.getSearchName().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                nameList.removeAll(nameList);
                for (int i=0; i<stringSymbol.length; i++){
                    if (stringSymbol[i].contains(s)){
                        nameList.add(stringSymbol[i]);
                    }
                }
                mainCoinAdapter.notifyDataSetChanged();
            }
        });
        model.refrashTransactionDataThread(stringSymbol);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        model.stopThread();
        Log.i("라이프사이클", "onPause");
    }
}