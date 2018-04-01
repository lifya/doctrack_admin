package com.pln.www.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pln.www.R;

/**
 * Created by User on 18/01/2018.
 */

public class PekerjaanViewHolder extends RecyclerView.ViewHolder {
    private PekerjaanViewHolder.ClickListener mClickListener;
    public TextView tvNamaPekerjaan, tvTegangan, tvKms, tvNoKontrak, tvProvinsi, tvLastUpdate;
    public ImageView ivStatus;
    public View mView;

    public PekerjaanViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        tvNamaPekerjaan = (TextView) mView.findViewById(R.id.tvJudul);
        tvTegangan = (TextView) mView.findViewById(R.id.tvTegangan);
        tvKms = (TextView) mView.findViewById(R.id.tvKms);
        tvNoKontrak = (TextView) mView.findViewById(R.id.tvKontrak);
        tvProvinsi = (TextView) mView.findViewById(R.id.tvProvinsi);

        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());
            }
        });

        mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mClickListener.onItemLongClick(v, getAdapterPosition());
                return true;
            }
        });

    }

    public interface ClickListener{
        public void onItemClick(View view, int position);
        public void onItemLongClick(View view, int position);
    }

    public void setOnClickListener(PekerjaanViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }

    public void setNamaPekerjaan(String namaPekerjaan) {
        tvNamaPekerjaan.setText(namaPekerjaan);
    }

    public void setTegangan(String tegangan) {
        tvTegangan.setText(tegangan);
    }

    public void setKms(String kms) { tvKms.setText(kms); }

    public void setNoKontrak(String noKontrak) {
        tvNoKontrak.setText(noKontrak);
    }

    public void setProvinsi(String provinsi) {
        tvProvinsi.setText(provinsi);
    }
}
