package com.pln.www.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import com.pln.www.R;

/**
 * Created by User on 07/01/2018.
 */

public class UserModelViewHolder extends RecyclerView.ViewHolder{
    private UserModelViewHolder.ClickListener mClickListener;
    public TextView tvEmail, tvNama;
    public ImageView ivHapus;
    public View mView;

    public UserModelViewHolder(View itemView){
        super(itemView);
        mView = itemView;
        tvEmail = (TextView) mView.findViewById(R.id.tvEmail);
        tvNama = (TextView) mView.findViewById(R.id.tvNama);
        ivHapus = (ImageView) mView.findViewById(R.id.ivHapus);

        ivHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
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

    public void setOnClickListener(UserModelViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }

    public void setEmail(String email){
        tvEmail.setText(email);
    }

    public void setNama(String nama){
        tvNama.setText(nama);
    }

}
