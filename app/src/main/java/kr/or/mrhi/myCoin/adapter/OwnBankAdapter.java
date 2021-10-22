package kr.or.mrhi.myCoin.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import kr.or.mrhi.myCoin.fragment.Fragment_Coins;
import kr.or.mrhi.myCoin.fragment.Fragment_LikeCoin;
import kr.or.mrhi.myCoin.fragment.OwnBank;

public class OwnBankAdapter extends FragmentStateAdapter {
    private final static int NUM_PAGES = 2;

    public OwnBankAdapter(FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            Fragment fragment3 = new OwnBank();
            return fragment3;

        } else if (position == 1) {
            Fragment fragment4 = new Fragment_Coins();

            return fragment4;
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
}
