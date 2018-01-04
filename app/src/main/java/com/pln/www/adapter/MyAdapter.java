package com.pln.www.adapter;

/**
 * Created by ACHI on 01/09/2017.
 */

import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pln.www.MainActivity;
import com.pln.www.R;
import java.util.ArrayList;

/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<ItemModel> rvData;

    public MyAdapter(ArrayList<ItemModel> inputData) {
        rvData = inputData;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvJudul,tvKonsultan,tvTanggal, tvWaktu;
        public ImageView ivStatus;
        public CardView cvMain;

        public ViewHolder(View v) {
            super(v);
            tvJudul = (TextView) v.findViewById(R.id.judul);
            tvKonsultan = (TextView) v.findViewById(R.id.konsultan);
            tvTanggal = (TextView) v.findViewById(R.id.tanggal);
            tvWaktu = (TextView) v.findViewById(R.id.waktu);
            ivStatus = (ImageView) v.findViewById(R.id.status);
            cvMain = (CardView) v.findViewById(R.id.cv_User);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_view, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        final ItemModel name = rvData.get(position);

        viewHolder.tvJudul.setText(rvData.get(position).getmJudul());
        viewHolder.tvKonsultan.setText(rvData.get(position).getmKonsultan());
        viewHolder.tvTanggal.setText(rvData.get(position).getmTanggal());
        viewHolder.tvWaktu.setText(rvData.get(position).getmWaktu());
        viewHolder.ivStatus.setImageResource(rvData.get(position).getmStatus());

//        viewHolder.cvMain.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Snackbar.make(v, "Clicked element " + name.getmJudul(), Snackbar.LENGTH_LONG).show();
//            }
//        });
    }

    @Override
    public int getItemCount() {

        return (rvData != null) ? rvData.size() : 0;
    }

}
