package com.httt1.vietnamtravel.home.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.httt1.vietnamtravel.R;
import com.httt1.vietnamtravel.common.utils.SharedPrefsHelper;
import com.httt1.vietnamtravel.home.adapter.DiscoverAdapter;
import com.httt1.vietnamtravel.home.model.HomeModel;
import com.httt1.vietnamtravel.home.presenter.HomeContract;
import com.httt1.vietnamtravel.home.presenter.HomePresenter;

import java.util.List;


public class NotMissedFragment extends Fragment implements HomeContract.View {
    private HomePresenter homePresenter;
    private DiscoverAdapter discoverAdapter;
    private RecyclerView rcvNotMissed;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public NotMissedFragment() {
        // Required empty public constructor
    }

    public static NotMissedFragment newInstance(String param1, String param2) {
        NotMissedFragment fragment = new NotMissedFragment();
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
        View view = inflater.inflate(R.layout.fragment_not_missed, container, false);
        SharedPrefsHelper sharedPrefsHelper = new SharedPrefsHelper(requireContext()); //requireContext tuong tu getContext() nhung neu null no se tra ra loi...
        int userId = sharedPrefsHelper.getInt("UserId");
        Log.d("Thong tin UserId 18:03", "Thong tin: " + userId);

        init(view);
        homePresenter = new HomePresenter(this, getContext());

        setDiscoverAdapter(homePresenter, userId);
        return view;
    }
    private void init(View view){
        rcvNotMissed = view.findViewById(R.id.fragment_not_missed_rcv);
    }

    @Override
    public void showDataCombo(List<HomeModel> list) {

    }

    @Override
    public void showDataVoucher(List<HomeModel> list) {

    }

    private void setDiscoverAdapter(HomePresenter homePresenter, int userId){
        discoverAdapter = new DiscoverAdapter();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rcvNotMissed.setLayoutManager(gridLayoutManager);
        rcvNotMissed.setAdapter(discoverAdapter);

        homePresenter.getDataDiscover("noMissed", userId);
    }
    @Override
    public void showDataDiscover(List<HomeModel> list) {
        discoverAdapter.setDataDiscover(getContext(),list);
        discoverAdapter.notifyDataSetChanged();
    }
}