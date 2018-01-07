package com.pln.www.fragment;


import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.pln.www.R;
import com.pln.www.adapter.ItemModel;
import com.pln.www.adapter.MyAdapter;
import com.pln.www.adapter.UserAdpter;
import com.pln.www.adapter.UserModel;
import com.pln.www.alert.FormUserDialog;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "RecyclerViewFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;
    private static final int DATASET_COUNT = 60; // menampilkan data sebanyak value
    private Button bAddUser;
    private ListView listviewUsers;
    private DatabaseReference dbUsers;
    private List<UserModel> userList;

    public UserFragment(){

    }

    @Override
    public void onClick(View v) {
        if(v == bAddUser){
            AddUser();
        }
    }

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    protected UserFragment.LayoutManagerType mCurrentLayoutManagerType;

    protected RecyclerView mRecyclerView;
    protected UserAdpter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart(){
        super.onStart();

        FirebaseRecyclerAdapter<UserModel, UserModelViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<UserModel, UserModelViewHolder>
                        (
                                UserModel.class,
                                R.layout.list_user,
                                UserModelViewHolder.class,
                                dbUsers
                        )
                {
                    @Override
                    protected void populateViewHolder(UserModelViewHolder viewHolder, UserModel model, int position) {
                        viewHolder.setEmail(model.getEmail());
                        viewHolder.setNama(model.getNama());
                    }
                };

        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class UserModelViewHolder extends RecyclerView.ViewHolder{
        TextView tvEmail, tvNama;
        View mView;

        public UserModelViewHolder(View itemView){
            super(itemView);
            mView = itemView;
            tvEmail = (TextView) mView.findViewById(R.id.tvEmail);
            tvNama = (TextView) mView.findViewById(R.id.tvNama);
        }
        public void setEmail(String email){
            tvEmail.setText(email);

        }
        public void setNama(String nama){
            tvNama.setText(nama);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_user, container, false);
        rootView.setTag(TAG);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rvUser);

        mLayoutManager = new LinearLayoutManager(getActivity());

        mCurrentLayoutManagerType = UserFragment.LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {

            mCurrentLayoutManagerType = (UserFragment.LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);

        mAdapter = new UserAdpter(getActivity(), (ArrayList<UserModel>) userList);

        mRecyclerView.setAdapter(mAdapter);

        dbUsers = FirebaseDatabase.getInstance().getReferenceFromUrl("https://tracking-user.firebaseio.com/Users");

        listviewUsers = (ListView) rootView.findViewById(R.layout.list_user);

        bAddUser = (Button) rootView.findViewById(R.id.bAddUser);
        bAddUser.setOnClickListener(this);

        return rootView;

    }

    public void setRecyclerViewLayoutManager(UserFragment.LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
                mCurrentLayoutManagerType = UserFragment.LayoutManagerType.GRID_LAYOUT_MANAGER;
                break;
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = UserFragment.LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = UserFragment.LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }

    private void AddUser(){
        android.support.v4.app.FragmentManager manager = getFragmentManager();
        FormUserDialog dialogAdd = new FormUserDialog();
        dialogAdd.show(manager, dialogAdd.getTag());
    }

}
