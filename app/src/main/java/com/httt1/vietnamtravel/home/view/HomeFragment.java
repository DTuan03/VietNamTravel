package com.httt1.vietnamtravel.home.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.httt1.vietnamtravel.R;
import com.httt1.vietnamtravel.common.utils.SharedPrefsHelper;
import com.httt1.vietnamtravel.home.adapter.ComboAdapter;
import com.httt1.vietnamtravel.home.adapter.DiscoverAdapter;
import com.httt1.vietnamtravel.home.adapter.ViewPager2Adapter;
import com.httt1.vietnamtravel.home.adapter.VoucherAdapter;
import com.httt1.vietnamtravel.home.model.HomeModel;
import com.httt1.vietnamtravel.home.presenter.HomeContract;
import com.httt1.vietnamtravel.home.presenter.HomePresenter;

import java.util.List;

public class HomeFragment extends Fragment implements HomeContract.View {
    private RecyclerView rcvCombo, rcvVoucher, rcvDiscover;
    private ComboAdapter comboAdapter;
    private VoucherAdapter voucherAdapter;
    private HomePresenter homePresenter;
    private ViewPager2 viewPager2;
    private TabLayout tabLayout;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        SharedPrefsHelper sharedPrefsHelper = new SharedPrefsHelper(getContext());
        String userId = sharedPrefsHelper.getString("UserId");
        init(view);

        homePresenter = new HomePresenter(this, getContext());

        setComboAdapter(homePresenter);

        setVoucherAdapter(homePresenter);
        setViewPager2Adapter();

        return view;
    }
    private void init(View view){
        rcvCombo = view.findViewById(R.id.fragment_home_rcv_combo);
        rcvVoucher = view.findViewById(R.id.fragment_home_rcv_voucher);
        viewPager2 = view.findViewById(R.id.fragment_home_view_page2);
        tabLayout = view.findViewById(R.id.fragment_home_tab_layout);
    }
    private void setComboAdapter(HomePresenter homePresenter){
        comboAdapter = new ComboAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        rcvCombo.setLayoutManager(linearLayoutManager);
        rcvCombo.setAdapter(comboAdapter);

        homePresenter.getDataCombo("CB");
    }

    @Override
    public void showDataCombo(List<HomeModel> list) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Cập nhật dữ liệu vào adapter và RecyclerView
                comboAdapter.setDataCombo(getContext(), list);
                comboAdapter.notifyDataSetChanged();
            }
        });
    }
    private void setVoucherAdapter(HomePresenter homePresenter){
        voucherAdapter = new VoucherAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        rcvVoucher.setLayoutManager(linearLayoutManager);
        rcvVoucher.setAdapter(voucherAdapter);

        homePresenter.getDataVoucher();
    }
    @Override
    public void showDataVoucher(List<HomeModel> list) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                voucherAdapter.setDataVoucher(getContext(), list);
                voucherAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void showDataDiscover(List<HomeModel> list) {
    }

    private void setViewPager2Adapter(){
        ViewPager2Adapter viewPager2Adapter = new ViewPager2Adapter(requireActivity());// Lấy hoạt động liên quan đến fragment
        viewPager2.setAdapter(viewPager2Adapter);
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Đề xuất");
                        break;
                    case 1:
                        tab.setText("Không thể bỏ lỡ");
                        break;
                    case 2:
                        tab.setText("Được yêu thích nhiều nhất");
                        break;
                }
            }
        }).attach();
    }
}