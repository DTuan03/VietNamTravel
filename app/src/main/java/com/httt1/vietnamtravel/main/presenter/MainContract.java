package com.httt1.vietnamtravel.main.presenter;

import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;

import androidx.fragment.app.Fragment;

public interface MainContract {
    interface View{
        void loadFragment(Fragment fragment, int setStatusBarColor);

    }
    interface Presenter{
        boolean onClickItem(MenuItem item);
    }
}
