package com.httt1.vietnamtravel.home.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.httt1.vietnamtravel.R;
import com.httt1.vietnamtravel.home.model.HomeModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ComboAdapter extends RecyclerView.Adapter<ComboAdapter.ComboViewHolder> {
    private List<HomeModel> listCombo;
    private Context context;
    public void setData(Context context, List<HomeModel> listCombo){
        this.context = context;
        this.listCombo = listCombo;
    }
    @NonNull
    @Override
    public ComboViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_combo, parent, false);
        return new ComboViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComboViewHolder holder, int position) {
        HomeModel combo = listCombo.get(position);
        String uriImgCombo = combo.geturlImg();
        Log.d("abc", uriImgCombo);
        String tvNameTour = combo.getnameTour();
        int tvPriceTour = combo.getPrice();
        if (combo != null){
            //placeholder để đặt hình ảnh mặc định khi đang tải và error() để đặt hình ảnh mặc định khi xảy ra lỗi khi tải ảnh
            Picasso.with(context).load(uriImgCombo).error(R.drawable.hue5).into(holder.image);
            holder.tvNameToure.setText(tvNameTour);
            holder.tvPriceToure.setText(String.valueOf(tvPriceTour));
        }
    }

    @Override
    public int getItemCount() {
        if(listCombo != null){
            return listCombo.size();
        }
        return 0;
    }

    public class ComboViewHolder extends RecyclerView.ViewHolder{
        private ImageView image;
        private TextView tvNameToure;
        private TextView tvPriceToure;
        public ComboViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.item_combo_img);
            tvNameToure = itemView.findViewById(R.id.item_combo_tv_name_tour);
            tvPriceToure = itemView.findViewById(R.id.item_combo_price_tour);
        }
    }

}
