package kr.or.mrhi.myCoin.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import kr.or.mrhi.myCoin.fragment.Fragment_AllCoins;
import kr.or.mrhi.myCoin.fragment.Fragment_LikeCoin;

public class TradingCoinAdapter extends FragmentStateAdapter {
    private final static int NUM_PAGES = 2;
    public TradingCoinAdapter(FragmentActivity fa) {
        super(fa);
    }
//detail 코인에서 매수 매도 뷰페이저
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            Fragment fragment1 = new Fragment_LikeCoin();
            return fragment1;

        } else if (position == 1) {
            Fragment fragment2 = new Fragment_AllCoins();

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
