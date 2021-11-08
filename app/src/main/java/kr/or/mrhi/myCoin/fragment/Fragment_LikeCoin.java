package kr.or.mrhi.myCoin.fragment;


import static kr.or.mrhi.myCoin.activity.MainActivity.namePositionMap;
import static kr.or.mrhi.myCoin.activity.MainActivity.stringSymbol;

import android.content.Intent;
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

import kr.or.mrhi.myCoin.network.DBController;
import kr.or.mrhi.myCoin.model.TickerDTO;
import kr.or.mrhi.myCoin.R;
import kr.or.mrhi.myCoin.adapter.LikeCoinAdapter;
import kr.or.mrhi.myCoin.viewModel.CoinViewModel;


public class Fragment_LikeCoin extends Fragment {
    private RecyclerView recyclerView;
    private LikeCoinAdapter likeCoinAdapter;
    private List<String> priceList;
    private List<TickerDTO> tickerDataList;
    private DBController dbController;
    private List<String> favoriteList;
    private List<String> favoriteListTemp;

    private CoinViewModel model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment1_1, container, false);
        model = new ViewModelProvider(requireActivity()).get(CoinViewModel.class);
        dbController = new DBController(requireActivity());
        priceList = new ArrayList<>();
        favoriteList = new ArrayList<>();
        favoriteListTemp = new ArrayList<>();
        favoriteList = dbController.getFavoritesList();

        favoriteListTemp.addAll(favoriteList);
        priceList.removeAll(priceList);

        recyclerView = view.findViewById(R.id.recyclerView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        likeCoinAdapter = new LikeCoinAdapter(priceList, tickerDataList, favoriteListTemp);
        recyclerView.setAdapter(likeCoinAdapter);

        model.getTickerCoinData().observe(requireActivity(), new Observer<List<TickerDTO>>() {
            @Override
            public void onChanged(List<TickerDTO> tickerDataList) {
                likeCoinAdapter.setTickerData(tickerDataList);
                likeCoinAdapter.notifyDataSetChanged();
            }
        });

        model.getTransactionCoinData("BTC").observe(requireActivity(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> transactionData) {
                priceList.removeAll(priceList);
                for (int i = 0; i < favoriteListTemp.size(); i++) {
                    priceList.add(transactionData.get(namePositionMap.get(favoriteListTemp.get(i))));
                }
                likeCoinAdapter.notifyDataSetChanged();
            }
        });

        model.getSearchName().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String name) {
                favoriteListTemp.removeAll(favoriteListTemp);
                for (int i = 0; i < favoriteList.size(); i++) {
                    if (favoriteList.get(i).contains(name)) {
                        favoriteListTemp.add(favoriteList.get(i));
                    }
                }
                likeCoinAdapter.notifyDataSetChanged();
            }
        });

        model.refrashTransactionDataThread(stringSymbol);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        model.stopThread();
    }
}