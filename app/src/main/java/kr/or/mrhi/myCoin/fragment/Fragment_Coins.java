package kr.or.mrhi.myCoin.fragment;

import static kr.or.mrhi.myCoin.MainActivity.namePositionMap;
import static kr.or.mrhi.myCoin.MainActivity.stringSymbol;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import kr.or.mrhi.myCoin.DBController;
import kr.or.mrhi.myCoin.R;
import kr.or.mrhi.myCoin.adapter.CoinListAdapter;
import kr.or.mrhi.myCoin.model.Transaction;
import kr.or.mrhi.myCoin.viewModel.CoinViewModel;


public class Fragment_Coins extends Fragment {
    private TextView tvTotalBuyCount, tvEvaluationProfitCount, tvTotalEvaluationCount, tvYieldCount;
    private TabLayout tabLayout;
    private ViewPager2 pager;
    private double totalBuyCount,evaluationProfitCount,totalEvaluationCount,yieldCount;

    private DBController dbController;
    private List<Transaction> transactionList;
    private CoinViewModel model;
    private String[] myCoinName;
    private List<String> priceList;
    private final static String[] tabElement = {"전체", "관심"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        model = new ViewModelProvider(this).get(CoinViewModel.class);
        dbController = new DBController(requireActivity());
        priceList = new ArrayList<>();
        transactionList = dbController.getMyWallet();
        myCoinName = new String[transactionList.size()];
        for (int i = 0; i < transactionList.size(); i++) {
            myCoinName[i] = transactionList.get(i).getCoinName();
        }

        model.getTransactionCoinData("BTC").observe(requireActivity(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> transactionData) {
                double transactionPrice = 0.0;
                double buyCount = 0.0;
                double curruntPrice = 0.0;
                totalBuyCount=0.0;
                evaluationProfitCount=0.0;


                for (int i=0; i<transactionList.size(); i++){
                    priceList.add(transactionData.get(namePositionMap.get(myCoinName[i])));
                }

                for (int i=0; i<transactionList.size(); i++) {
                    transactionPrice = Double.parseDouble(priceList.get(i));
                    buyCount = Double.parseDouble(transactionList.get(i).getQuantity());
                    totalBuyCount += transactionPrice*buyCount;//총매수
                    curruntPrice = Double.parseDouble(transactionData.get(namePositionMap.get(myCoinName[i])));
                    evaluationProfitCount += curruntPrice * buyCount;//총평가

                }
                    tvTotalBuyCount.setText(String.format("%.0f",totalBuyCount));//총매수
                    Log.i("총매수", transactionPrice+"/"+buyCount);
                    tvTotalEvaluationCount.setText(String.format("%.0f",evaluationProfitCount));//총평가
                    tvEvaluationProfitCount.setText(String.format("%.0f",evaluationProfitCount - totalBuyCount));
                    tvYieldCount.setText(String.format("%.2f%%",((evaluationProfitCount - totalBuyCount)/totalBuyCount*100)));

            }
        });
        model.refrashTransactionDataThread(myCoinName);

        // tvTotalBuyCount.setText();

        tabLayout = view.findViewById(R.id.tabLayout2);
        pager = view.findViewById(R.id.pager);
        tvTotalBuyCount = view.findViewById(R.id.tvTotalBuyCount);
        tvEvaluationProfitCount = view.findViewById(R.id.tvEvaluationProfitCount);
        tvTotalEvaluationCount = view.findViewById(R.id.tvTotalEvaluationCount);
        tvYieldCount = view.findViewById(R.id.tvYieldCount);


        CoinListAdapter screenSlidePagerAdapter = new CoinListAdapter(getActivity());
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