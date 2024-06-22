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

public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.DiscoverViewHolder> {
    private List<HomeModel> listDiscover;
    private Context context;
    public void setDataDiscover(Context context, List<HomeModel> listDiscover) {
        this.context = context;
        this.listDiscover = listDiscover;
    }
    @NonNull
    @Override
    public DiscoverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_discover,parent, false);
        return new DiscoverViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscoverViewHolder holder, int position) {
        HomeModel discover = listDiscover.get(position);
        String imgUrl = discover.geturlImg();
        String tvNameTour = discover.getnameTour();
        String tvAvgRate = String.valueOf(discover.getavgrStar());
        String tvPrice = String.valueOf(discover.getPrice());
        if (discover != null){
            Picasso.with(context).load(imgUrl).error(R.drawable.hue5).into(holder.imgDiscover);
            holder.tvName.setText(tvNameTour);
            holder.tvAvgRate.setText(tvAvgRate);
            holder.tvPrice.setText(tvPrice);
            if(discover.getIsFavorite() != 0){
                holder.imgFav.setImageResource(R.mipmap.icon_favorite_color);
                Log.d("ABCDCUDBHCFUDH", "HUIEHFDIEHFIEHFIEFH" + discover.getIsFavorite());
            }
        }
    }

    @Override
    public int getItemCount() {
        if (listDiscover != null){
            return listDiscover.size();
        }
        return 0;
    }

    public class DiscoverViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgDiscover;
        private TextView tvName;
        private TextView tvAvgRate;
        private TextView tvPrice;
        private ImageView imgFav;

        public DiscoverViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDiscover = itemView.findViewById(R.id.item_discover_img);
            tvName = itemView.findViewById(R.id.item_discover_tv_name_tour);
            tvAvgRate = itemView.findViewById(R.id.item_discover_tv_avg_star);
            tvPrice = itemView.findViewById(R.id.item_discover_tv_price);
            imgFav = itemView.findViewById(R.id.item_discover_img_favorite);
        }
    }
}
