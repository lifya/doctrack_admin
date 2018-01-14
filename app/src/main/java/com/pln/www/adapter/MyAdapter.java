package com.pln.www.adapter;

/**
 * Created by ACHI on 01/09/2017.
 */

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pln.www.interfacee.ItemClickListener;
import com.pln.www.R;
import com.pln.www.model.ItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Provide views to RecyclerView with data from mDataSet.
 */
class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    public TextView tvJudul,tvKonsultan,tvTanggal, tvWaktu;
    public ImageView ivStatus;
    public CardView cvMain;

    private ItemClickListener itemClickListener;

    public ViewHolder(View itemView) {
        super(itemView);
        tvJudul = (TextView) itemView.findViewById(R.id.judul);
        tvKonsultan = (TextView) itemView.findViewById(R.id.konsultan);
        tvTanggal = (TextView) itemView.findViewById(R.id.tanggal);
        tvWaktu = (TextView) itemView.findViewById(R.id.waktu);
        ivStatus = (ImageView) itemView.findViewById(R.id.status);
        cvMain = (CardView) itemView.findViewById(R.id.cv_User);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }


    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {

        itemClickListener.onClick(v, getAdapterPosition(), false);

    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), true);
        return false;
    }
}

public class MyAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List<ItemModel> rvData;
    private Context context;

    public MyAdapter(List<ItemModel> documentModels) {
        rvData = documentModels;
        this.context = context;
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

        viewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View v, int position, boolean isLongClick) {
                if(isLongClick)
                    Snackbar.make(v, "Clicked element " + name.getmJudul(), Snackbar.LENGTH_LONG).show();
                else
                    Snackbar.make(v, "Clicked element " + name.getmJudul(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {

        return rvData.size();
    }

    public void setFilter(List<ItemModel> documentModels) {
        rvData = new ArrayList<>();
        rvData.addAll(documentModels);
        notifyDataSetChanged();
    }

}
