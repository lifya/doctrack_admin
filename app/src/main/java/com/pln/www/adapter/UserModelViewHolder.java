package com.pln.www.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pln.www.R;

/**
 * Created by User on 07/01/2018.
 */

public class UserModelViewHolder extends RecyclerView.ViewHolder{

    TextView tvEmail, tvNama;
    ImageView ivHapus;
    View mView;

    public UserModelViewHolder(View itemView){
        super(itemView);
        mView = itemView;
        tvEmail = (TextView) mView.findViewById(R.id.tvEmail);
        tvNama = (TextView) mView.findViewById(R.id.tvNama);
        ivHapus = (ImageView) mView.findViewById(R.id.ivHapus);
    }
    public void setEmail(String email){
        tvEmail.setText(email);

    }
    public void setNama(String nama){
        tvNama.setText(nama);
    }
}
