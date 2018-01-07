package com.pln.www.adapter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pln.www.R;

import java.util.ArrayList;

/**
 * Created by ACHI on 04/01/2018.
 */

public class UserAdpter extends RecyclerView.Adapter<UserAdpter.ViewHolder> {
    private ArrayList<UserModel> rvUser;
    private Activity context;

    public UserAdpter(Activity newActivity, ArrayList<UserModel> inputUser) {
        super();
        context = newActivity;
        rvUser = inputUser;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNama, tvEmail;
        public CardView cvUser;

        public ViewHolder(View v) {
            super(v);
            tvNama = (TextView) v.findViewById(R.id.tvNama);
            tvEmail = (TextView) v.findViewById(R.id.tvEmail);
            cvUser = (CardView) v.findViewById(R.id.cv_User);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_user, parent, false);

        return new UserAdpter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final UserModel name = rvUser.get(position);

        //holder.tvNama.setText(rvUser.get(position).getNama());
        //holder.tvEmail.setText(rvUser.get(position).getEmail());
        holder.tvNama.setText(name.getNama());
        holder.tvEmail.setText(name.getEmail());
    }

    @Override
    public int getItemCount() {

        return (rvUser != null) ? rvUser.size() : 0;
    }


}
