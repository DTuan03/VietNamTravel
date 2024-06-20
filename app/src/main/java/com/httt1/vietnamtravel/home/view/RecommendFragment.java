package com.httt1.vietnamtravel.home.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.httt1.vietnamtravel.R;
import com.httt1.vietnamtravel.home.adapter.DiscoverAdapter;
import com.httt1.vietnamtravel.home.model.HomeModel;
import com.httt1.vietnamtravel.home.presenter.HomeContract;
import com.httt1.vietnamtravel.home.presenter.HomePresenter;

import java.util.List;

public class RecommendFragment extends Fragment implements HomeContract.View {
    private DiscoverAdapter discoverAdapter;
    private RecyclerView rcvRecommend;
    private HomePresenter homePresenter;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public RecommendFragment() {
        // Required empty public constructor
    }

    public static RecommendFragment newInstance(String param1, String param2) {
        RecommendFragment fragment = new RecommendFragment();
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
        View view = inflater.inflate(R.layout.fragment_recommend, container, false);
        rcvRecommend = view.findViewById(R.id.fragment_recommend_rcv);
        homePresenter = new HomePresenter(this, getContext());

        setDiscoverAdapter(homePresenter);

        return view;
    }

    @Override
    public void showDataCombo(List<HomeModel> list) {

    }

    @Override
    public void showDataVoucher(List<HomeModel> list) {

    }

    private void setDiscoverAdapter(HomePresenter homePresenter){
        discoverAdapter = new DiscoverAdapter();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rcvRecommend.setLayoutManager(gridLayoutManager);
        rcvRecommend.setAdapter(discoverAdapter);

        homePresenter.getDataDiscover("recommend");
    }

    @Override
    public void showDataDiscover(List<HomeModel> list) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                discoverAdapter.setDataDiscover(getContext(), list);
                discoverAdapter.notifyDataSetChanged();
            }
        });
    }
}