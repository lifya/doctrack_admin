package com.pln.www.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.pln.www.R;
//import com.pln.www.adapter.UserAdpter;
import com.pln.www.adapter.RecycleAdapter;
import com.pln.www.model.KontrakModel;
import com.pln.www.model.UserModel;
import com.pln.www.viewholder.UserViewHolder;
import com.pln.www.alert.FormUserDialog;

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
    //private ImageView ivHapus;
    private ListView listviewUsers;
    private DatabaseReference dbUsers, dbKontrak;
    private List<UserModel> userList;
    private FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;


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
    protected RecycleAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart(){
        super.onStart();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<UserModel, UserViewHolder>
                        (
                                UserModel.class,
                                R.layout.list_user,
                                UserViewHolder.class,
                                dbUsers
                        )
                {
                    @Override
                    protected void populateViewHolder(final UserViewHolder viewHolder, final UserModel model, int position) {
                        viewHolder.setEmail(model.getEmail());
                        viewHolder.setNama(model.getNama());
                        viewHolder.setOnClickListener(new UserViewHolder.ClickListener() {
                            @Override
                            public void onItemClick(View view, final int position) {
                                final AlertDialog.Builder alertDelete = new AlertDialog.Builder(getActivity());
                                alertDelete.setMessage("Are you sure want to delete this user ?").setCancelable(false)
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                int selectedItem = position;
                                                firebaseRecyclerAdapter.getRef(selectedItem).removeValue();
                                                firebaseRecyclerAdapter.notifyItemRemoved(selectedItem);
                                                mRecyclerView.invalidate();
                                                //Task<UserModel> task = FirebaseAuth.getInstance().deleteUser();
                                                //firebaseAuth = FirebaseAuth.getInstance().deleteUser();

                                                onStart();
                                            }

                                        })
                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                                AlertDialog alert = alertDelete.create();
                                alert.setTitle("Warning");
                                alert.show();
                            }

                            @Override
                            public void onItemLongClick(View view, int position) {
                                Toast.makeText(getActivity(), "Successed", Toast.LENGTH_LONG).show();
                                return;
                            }
                        });
                    }


                };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
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

        mAdapter = new RecycleAdapter(userList, getActivity());

        mRecyclerView.setAdapter(mAdapter);

        dbUsers = FirebaseDatabase.getInstance().getReferenceFromUrl("https://tracking-user.firebaseio.com/Users");

        listviewUsers = (ListView) rootView.findViewById(R.layout.list_user);

        //ivHapus = (ImageView) rootView.findViewById(R.id.ivHapus);
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
