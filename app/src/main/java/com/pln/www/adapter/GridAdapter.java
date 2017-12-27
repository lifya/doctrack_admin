package com.pln.www.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pln.www.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ACHI on 01/09/2017.
 */

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {

    List<ItemModel> mItems;

    public GridAdapter() {
        super();
        mItems = new ArrayList<ItemModel>();
        ItemModel nama = new ItemModel();
        nama.setmName("Surat Al-Fatihah Ayat 1-7");
        nama.setmThumbnail(R.drawable.ic_home_black_24dp);
        mItems.add(nama);

        nama = new ItemModel();
        nama.setmName("Surat Al-Maidah Ayat 1-10");
        nama.setmThumbnail(R.drawable.ic_home_black_24dp);
        mItems.add(nama);

        nama = new ItemModel();
        nama.setmName("Surat Al-Baqoroh Ayat 1-10");
        nama.setmThumbnail(R.drawable.ic_home_black_24dp);
        mItems.add(nama);

        nama = new ItemModel();
        nama.setmName("Surat Al-Maidah Ayat 1-10");
        nama.setmThumbnail(R.drawable.ic_home_black_24dp);
        mItems.add(nama);

        nama = new ItemModel();
        nama.setmName("Surat Al-Baqoroh Ayat 1-10");
        nama.setmThumbnail(R.drawable.ic_home_black_24dp);
        mItems.add(nama);

        nama = new ItemModel();
        nama.setmName("Surat Al-Maidah Ayat 1-10");
        nama.setmThumbnail(R.drawable.ic_home_black_24dp);
        mItems.add(nama);

        nama = new ItemModel();
        nama.setmName("Achi Aprilia A anak Ayahndut");
        nama.setmThumbnail(R.drawable.ic_home_black_24dp);
        mItems.add(nama);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.grid_view, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ItemModel nature = mItems.get(i);
        viewHolder.tvspecies.setText(nature.getmName());
        viewHolder.imgThumbnail.setImageResource(nature.getmThumbnail());
    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder  {


        public ImageView imgThumbnail;
        public TextView tvspecies;

        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = (ImageView)itemView.findViewById(R.id.img_thumbnail);
            tvspecies = (TextView)itemView.findViewById(R.id.status);

        }
    }
}

