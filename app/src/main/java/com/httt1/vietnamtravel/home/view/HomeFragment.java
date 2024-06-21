package com.httt1.vietnamtravel.home.view;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;

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
import java.util.Objects;

public class HomeFragment extends Fragment implements HomeContract.View {
    private RecyclerView rcvCombo, rcvVoucher;
    private ComboAdapter comboAdapter;
    private VoucherAdapter voucherAdapter;
    private ViewPager2 viewPager2;
    private TabLayout tabLayout;
    private View fragmentHome;
    private ScrollView scrollView;
    private ConstraintLayout constraintLayoutSearch;
    private ImageView imgCart;
    private EditText etSearch;
    private boolean isScrolled = false; //Dùng để kiểm tra xem có cuộn lại trở về đầu khi chuyển sang fragment khác không, vì mình set cho kiểu khi chuyển sang
    //fragment khác thì khi quay lại nó vẫn màu trắng, nhưng khi người dùng cuộn về đầu mới chuyển sang fragment khác thì khi quay lại nó lại là màu trắng
    //trong khi ở đầu sẽ là màu cam, vì vậy phải bắt cái sự kiện này, nếu quay về đầu sẽ là false và kh thực hiện trong onReSum là đổi màu trắng
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

        SharedPrefsHelper sharedPrefsHelper = new SharedPrefsHelper(requireContext()); //requireContext tuong tu getContext() nhung neu null no se tra ra loi...
        int userId = sharedPrefsHelper.getInt("UserId");
        Log.d("Thong tin UserId 18:03", "Thong tin: " + userId);
        init(view);

        HomePresenter homePresenter = new HomePresenter(this, getContext());

        setComboAdapter(homePresenter, userId);

        setVoucherAdapter(homePresenter);

        setViewPager2Adapter();

        setStatusBar(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isScrolled){
            Log.d("dd", "Dang o trang thai quay tro lai");
            Window window = getActivity().getWindow();
            // Màu của status bar
            window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.white));
            constraintLayoutSearch.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
            imgCart.setImageResource(R.mipmap.fragment_home_cart_black_adaptive_fore);
            etSearch.setBackgroundResource(R.drawable.fragment_home_bacground_search_after);
            fragmentHome.setBackgroundResource(R.color.white);
        }
    }

    private void init(View view){
        rcvCombo = view.findViewById(R.id.fragment_home_rcv_combo);
        rcvVoucher = view.findViewById(R.id.fragment_home_rcv_voucher);
        viewPager2 = view.findViewById(R.id.fragment_home_view_page2);
        tabLayout = view.findViewById(R.id.fragment_home_tab_layout);
        scrollView = view.findViewById(R.id.fragment_home_sv);
        constraintLayoutSearch = view.findViewById(R.id.fragment_home_contrainLayout_search);
        imgCart = view.findViewById(R.id.fragmet_home_img_cart);
        etSearch = view.findViewById(R.id.fragment_home_et_search);
        fragmentHome =  view.findViewById(R.id.fragment_home);
    }
    private void setComboAdapter(HomePresenter homePresenter, int userId ){
        comboAdapter = new ComboAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        rcvCombo.setLayoutManager(linearLayoutManager);
        rcvCombo.setAdapter(comboAdapter);

        homePresenter.getDataCombo("CB", userId);
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

    private void setStatusBar(View view){
        Window window = getActivity().getWindow();
        //Mau cua status bar
        window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.fragment_home_status_bar));
        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY > 0){
                    isScrolled = true;
                    window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.white));
                    imgCart.setImageResource(R.mipmap.fragment_home_cart_black_adaptive_fore);
                    etSearch.setBackgroundResource(R.drawable.fragment_home_bacground_search_after);
                    fragmentHome.setBackgroundResource(R.color.white);
                }
                if(scrollY < 5){
                    window.setStatusBarColor(ContextCompat.getColor(getActivity(),R.color.fragment_home_status_bar));
                    constraintLayoutSearch.setBackgroundResource(R.drawable.fragment_home_background_cl_search_after);
                    scrollView.setBackgroundResource(R.drawable.fragment_home_background_cl_search_after);
                    imgCart.setImageResource(R.mipmap.fragment_home_cart_adaptive_fore);
                    fragmentHome.setBackgroundResource(R.drawable.activity_home_background);
                }
                //kiểm tra nếu cuộn trở lại về đầu thì kh thực hiện đổi màu khi resum
                if (scrollY == 0){
                    isScrolled = false;
                }
//                if (scrollY > 1800) {
//                    scrollView.scrollTo(scrollX, 1800); // Dừng cuộn khi y > 250
//                }
            }
        });
    }
}