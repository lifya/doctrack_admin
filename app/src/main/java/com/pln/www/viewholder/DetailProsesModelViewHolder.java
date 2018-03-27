package com.pln.www.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pln.www.R;

/**
 * Created by User on 12/03/2018.
 */

public class DetailProsesModelViewHolder extends RecyclerView.ViewHolder {
    private View mView;
    private TextView tvNamaProses, tvStatusProses, tvTanggalProses, tvKeteranganProses, tvFIleProses;

    public DetailProsesModelViewHolder(View itemView){
        super(itemView);
        mView = itemView;
        tvNamaProses = (TextView) mView.findViewById(R.id.tvNamaProses);
        tvStatusProses = (TextView) mView.findViewById(R.id.tvStatusProses);
        tvTanggalProses = (TextView) mView.findViewById(R.id.tvTanggalProses);
        tvKeteranganProses = (TextView) mView.findViewById(R.id.tvKeteranganProses);
        tvFIleProses = (TextView) mView.findViewById(R.id.tvFileProses);

    }

    public void setNamaProses (String namaProses){
        tvNamaProses.setText(namaProses);
    }

    public void setStatusProses (String statusProses){
        tvStatusProses.setText(statusProses);
    }

    public void setTanggalProses (String tanggalProses){ tvTanggalProses.setText(tanggalProses); }

    public void setKeteranganProses (String keteranganProses){ tvKeteranganProses.setText(keteranganProses); }

    public void setTvFIleProses(String fileProses) { tvFIleProses.setText(fileProses); }
}
