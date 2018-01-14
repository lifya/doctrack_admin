package com.pln.www.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pln.www.R;
import com.pln.www.model.UserModel;

import java.util.List;

/**
 * Created by User on 08/01/2018.
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyHoder>{
    List<UserModel> list;
    Context context;

    public RecycleAdapter(List<UserModel> list, Context context){
        this.list = list;
        this.context = context;
    }

    @Override
    public MyHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_user, parent, false);
        MyHoder myHoder = new MyHoder(view);
        return myHoder;
    }

    @Override
    public void onBindViewHolder(MyHoder holder, int position) {
        UserModel myList = list.get(position);
        holder.tvName.setText(myList.getNama());
        holder.tvEmail.setText(myList.getEmail());
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        int arr = 0;
        try{
            if(list.size()==0){
                arr = 0;
            }
            else{
                arr = list.size();
            }
        }
        catch (Exception e){

        }
        return 0;
    }


    public class MyHoder extends RecyclerView.ViewHolder {
        TextView tvEmail, tvName;
        ImageView ivDelete;

        public MyHoder(View itemView){
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvNama);
            tvEmail = (TextView) itemView.findViewById(R.id.tvEmail);
            ivDelete = (ImageView) itemView.findViewById(R.id.ivHapus);
        }
    }
}
