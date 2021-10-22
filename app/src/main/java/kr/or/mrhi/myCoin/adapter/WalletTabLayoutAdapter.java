package kr.or.mrhi.myCoin.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import kr.or.mrhi.myCoin.fragment.Fragment_LikeCoin;
import kr.or.mrhi.myCoin.fragment.Fragment_Wallet;
import kr.or.mrhi.myCoin.fragment.Fragment_transactionHistory;
import kr.or.mrhi.myCoin.fragment.OwnBank;

public class WalletTabLayoutAdapter extends FragmentStateAdapter {
    private final static int NUM_PAGES = 2;
    public WalletTabLayoutAdapter(FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            Fragment fragment1 = new OwnBank();
            return fragment1;

        } else if (position == 1) {
            Fragment fragment2 = new Fragment_transactionHistory();
            return fragment2;
        } else {
            Log.d("프레그먼트", "createFragment() 프레그먼트 생성 오류");
            return null;
        }
    }

    //뷰페이지 개수
    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}//end of adapter

