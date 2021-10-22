package kr.or.mrhi.myCoin.fragment;

import static kr.or.mrhi.myCoin.MainActivity.namePositionMap;
import static kr.or.mrhi.myCoin.MainActivity.stringSymbol;

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
import java.util.Collections;
import java.util.List;

import kr.or.mrhi.myCoin.DBController;
import kr.or.mrhi.myCoin.POJO.TickerData;
import kr.or.mrhi.myCoin.R;
import kr.or.mrhi.myCoin.adapter.LikeCoinAdapter;
import kr.or.mrhi.myCoin.viewModel.CoinViewModel;


public class Fragment_LikeCoin extends Fragment {
    private RecyclerView recyclerView;
    private LikeCoinAdapter likeCoinAdapter;
    private List<String> priceList;
    private TickerData ticker;
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
        likeCoinAdapter = new LikeCoinAdapter(priceList, ticker, favoriteListTemp);
        recyclerView.setAdapter(likeCoinAdapter);

        model.getTickerCoinData().observe(requireActivity(), new Observer<TickerData>() {
            @Override
            public void onChanged(TickerData tickerData) {
                ticker = tickerData;
                likeCoinAdapter.notifyDataSetChanged();
            }
        });

        model.getTransactionCoinData("BTC").observe(requireActivity(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> transactionData) {
                priceList.removeAll(priceList);
                for (int i=0; i<favoriteListTemp.size(); i++){
                   priceList.add(transactionData.get(namePositionMap.get(favoriteListTemp.get(i))));
                }
                likeCoinAdapter.notifyDataSetChanged();
            }
        });

        model.getSearchName().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.i("검색", s);
                List<String> list = new ArrayList<>();
                for (String str:favoriteList){
                    if (str.contains(s)){
                        list.add(str);
                    }
                }
                favoriteListTemp=list;
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