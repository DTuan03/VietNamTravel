package com.httt1.vietnamtravel.home.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.httt1.vietnamtravel.home.view.FavoriteFragment;
import com.httt1.vietnamtravel.home.view.NotMissedFragment;
import com.httt1.vietnamtravel.home.view.RecommendFragment;

public class ViewPager2Adapter  extends FragmentStateAdapter {
    public ViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new RecommendFragment();
            case 1:
                return new NotMissedFragment();
            case 2:
                return new FavoriteFragment();
            default:
                return new RecommendFragment();
        }
    }

    //tra ve so luong TAB co
    @Override
    public int getItemCount() {
        return 3;
    }
}

