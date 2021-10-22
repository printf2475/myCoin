package kr.or.mrhi.myCoin.fragment;

import static kr.or.mrhi.myCoin.MainActivity.namePositionMap;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kr.or.mrhi.myCoin.DBController;
import kr.or.mrhi.myCoin.R;
import kr.or.mrhi.myCoin.adapter.WalletAdapter;
import kr.or.mrhi.myCoin.model.Transaction;
import kr.or.mrhi.myCoin.viewModel.CoinViewModel;


public class OwnBank extends Fragment {

    private TextView textTotalBuyCount, textTotalEvaluationCount, textEvaluationProfitCount, textYieldCount, holdings, KRWHoldings;
    private double totalBuyCount, evaluationProfitCount, totalEvaluationCount, yieldCount;

    private DBController dbController;
    private List<Transaction> transactionList;
    private CoinViewModel model;
    private String[] myCoinName;
    private List<String> priceList;
    private Transaction transaction;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_own_bank, container, false);
        dbController = new DBController(getActivity().getApplicationContext());
        model = new ViewModelProvider(requireActivity()).get(CoinViewModel.class);
        priceList = new ArrayList<>();
        textTotalBuyCount = view.findViewById(R.id.textTotalBuyCount);
        textTotalEvaluationCount = view.findViewById(R.id.textTotalEvaluationCount);
        textEvaluationProfitCount = view.findViewById(R.id.textEvaluationProfitCount);
        textYieldCount = view.findViewById(R.id.textYieldCount);
        holdings = view.findViewById(R.id.holdings);
        KRWHoldings = view.findViewById(R.id.KRWHoldings);
        listView= view.findViewById(R.id.mycoinlist);

        WalletAdapter adapter = new WalletAdapter(dbController.getMyWallet());
        listView.setAdapter(adapter);



        transaction = dbController.getCoinTransaction("BTC");
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
                int balance=0;
                totalBuyCount = 0.0;
                evaluationProfitCount = 0.0;

                for (int i = 0; i < transactionList.size(); i++) {
                    priceList.add(transactionData.get(namePositionMap.get(myCoinName[i])));
                }

                for (int i = 0; i < transactionList.size(); i++) {
                    transactionPrice = Double.parseDouble(priceList.get(i));
                    buyCount = Double.parseDouble(transactionList.get(i).getQuantity());
                    totalBuyCount += transactionPrice * buyCount;//총매수
                    curruntPrice = Double.parseDouble(transactionData.get(namePositionMap.get(myCoinName[i])));
                    evaluationProfitCount += curruntPrice * buyCount;//총평가
                    balance+=transactionList.get(i).getBalance();

                }
                textTotalBuyCount.setText(String.format("%.0f", totalBuyCount));//총매수
                textTotalEvaluationCount.setText(String.format("%.0f", evaluationProfitCount));//총평가
                textEvaluationProfitCount.setText(String.format("%.0f", evaluationProfitCount - totalBuyCount));
                textYieldCount.setText(String.format("%.2f%%", ((evaluationProfitCount - totalBuyCount) / totalBuyCount * 100)));

                if (transactionList.size() != 0) {
                    KRWHoldings.setText(String.valueOf(balance));
                    holdings.setText(String.format("%.0f", evaluationProfitCount + Double.parseDouble(KRWHoldings.getText().toString())));
                }


            }
        });
        model.refrashTransactionDataThread(myCoinName);

        return view;
    }
}