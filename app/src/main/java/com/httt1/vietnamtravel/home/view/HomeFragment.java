package com.httt1.vietnamtravel.home.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.httt1.vietnamtravel.R;
import com.httt1.vietnamtravel.common.utils.SharedPrefsHelper;
import com.httt1.vietnamtravel.home.adapter.ComboAdapter;
import com.httt1.vietnamtravel.home.model.HomeModel;
import com.httt1.vietnamtravel.home.model.HomeRepository;
import com.httt1.vietnamtravel.home.presenter.HomeContract;
import com.httt1.vietnamtravel.home.presenter.HomePresenter;

import java.util.List;

public class HomeFragment extends Fragment implements HomeContract.View {
    private RecyclerView rcvCombo;
    private ComboAdapter comboAdapter;
    private HomePresenter homePresenter;
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

        comboAdapter = new ComboAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        rcvCombo.setLayoutManager(linearLayoutManager);
        rcvCombo.setAdapter(comboAdapter);

        homePresenter = new HomePresenter(this, getContext());
        homePresenter.getData("CB");

        return view;
    }
    private void init(View view){
        rcvCombo = view.findViewById(R.id.fragment_home_rcv_combo);
    }

    @Override
    public void showData(List<HomeModel> list) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Cập nhật dữ liệu vào adapter và RecyclerView
                comboAdapter.setData(getContext(), list);
                comboAdapter.notifyDataSetChanged();
            }
        });
    }
}