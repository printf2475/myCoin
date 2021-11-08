package kr.or.mrhi.myCoin.fragment;

import static kr.or.mrhi.myCoin.activity.MainActivity.namePositionMap;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import kr.or.mrhi.myCoin.network.DBController;
import kr.or.mrhi.myCoin.R;
import kr.or.mrhi.myCoin.adapter.CoinListAdapter;
import kr.or.mrhi.myCoin.model.Transaction;
import kr.or.mrhi.myCoin.viewModel.CoinViewModel;

public class Fragment_Coins extends Fragment {
    private TextView tvTotalBuyCount, tvEvaluationProfitCount, tvTotalEvaluationCount, tvYieldCount;
    private EditText edtTextSearchCoin;

    private TabLayout tabLayout;
    private ViewPager2 pager;
    private double totalBuyCount, evaluationProfitCount, totalEvaluationCount, yieldCount;

    private DBController dbController;
    private CoinViewModel model;
    private CoinListAdapter screenSlidePagerAdapter;

    private List<Transaction> transactionList;
    private List<String> myCoinName;
    private List<String> myCoinAmong;
    private List<String> myCoinPrice;
    private List<String> priceList;

    private final static String[] tabElement = {"전체", "관심"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        model = new ViewModelProvider(requireActivity()).get(CoinViewModel.class);
        model.stopThread();
        dbController = new DBController(requireActivity());
        transactionList = dbController.getMyWallet();
        myCoinName = new ArrayList<>();

        tabLayout = view.findViewById(R.id.tabLayout2);
        pager = view.findViewById(R.id.pager);
        tvTotalBuyCount = view.findViewById(R.id.tvTotalBuyCount);
        tvEvaluationProfitCount = view.findViewById(R.id.tvEvaluationProfitCount);
        tvTotalEvaluationCount = view.findViewById(R.id.tvTotalEvaluationCount);
        tvYieldCount = view.findViewById(R.id.tvYieldCount);
        edtTextSearchCoin = view.findViewById(R.id.edtText_SearchCoin);

        for (int i = 0; i < transactionList.size(); i++) {
            myCoinName.add(transactionList.get(i).getCoinName());
        }

        model.getTransactionCoinData("BTC").observe(requireActivity(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> currentData) {
                double transactionPrice = 0.0;
                double buyCount = 0.0;
                double buyPrice = 0.0;
                double curruntPrice = 0.0;
                totalBuyCount = 0.00;
                evaluationProfitCount = 0.00;

                priceList = new ArrayList<>();
                myCoinAmong = new ArrayList<>();
                myCoinPrice = new ArrayList<String>();

                //코인별 거래들을 각각 list에 넣음
                for (int i = 0; i < transactionList.size(); i++) {
                    myCoinAmong.add(transactionList.get(i).getQuantity());
                    myCoinPrice.add(transactionList.get(i).getPrice());
                }

                for (String price : myCoinPrice){
                    transactionPrice+=Double.parseDouble(price);
                }

                //보유코인의 현재가격
                for (int i = 0; i < myCoinName.size(); i++) {
                    if (namePositionMap.get(myCoinName.get(i))!=null){
                        priceList.add(currentData.get(namePositionMap.get(myCoinName.get(i))));
                    }
                }

                if (!priceList.isEmpty() && transactionList.size() != 0) {
                    for (int i = 0; i < priceList.size(); i++) {
                        buyCount = Double.parseDouble(myCoinAmong.get(i));
                        buyPrice = Double.parseDouble(myCoinPrice.get(i));
                        curruntPrice = Double.parseDouble(priceList.get(i));

                        totalBuyCount += buyPrice;//총매수
                        evaluationProfitCount += curruntPrice * buyCount;//총평가
                    }

                    tvTotalBuyCount.setText(String.format("%.0f", totalBuyCount) );//총매수
                    tvTotalEvaluationCount.setText(String.format("%.0f", evaluationProfitCount));//총평가
                    tvEvaluationProfitCount.setText(String.format("%.0f", evaluationProfitCount - totalBuyCount));
                    tvYieldCount.setText(String.format("%.2f%%", ((evaluationProfitCount - totalBuyCount) / totalBuyCount * 100)));
                }
            }
        });
        model.refrashTransactionDataThread(myCoinName.toArray(new String[myCoinName.size()]));



        screenSlidePagerAdapter = new CoinListAdapter(getActivity());
        edtTextSearchCoin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                model.setSearchName(charSequence.toString().toUpperCase());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        pager.setAdapter(screenSlidePagerAdapter);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, pager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                TextView textView = new TextView(requireActivity());
                textView.setGravity(Gravity.CENTER);
                textView.setText(tabElement[position]);
                tab.setCustomView(textView);
            }
        });
        tabLayoutMediator.attach();
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        model.stopThread();
    }

}