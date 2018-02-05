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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ValueEventListener;
import com.pln.www.R;
//import com.pln.www.adapter.UserAdpter;
import com.pln.www.adapter.RecycleAdapter;
import com.pln.www.interfacee.ItemClickListener;
import com.pln.www.model.KontrakModel;
import com.pln.www.model.UserModel;
import com.pln.www.viewholder.UserViewHolder;
import com.pln.www.alert.FormUserDialog;

import org.w3c.dom.Text;

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
    //private ImageView ivHapus;
    private ListView listviewUsers;
    private DatabaseReference dbUsers, dbKontrak;
    private ArrayList<UserModel> usermodelList = new ArrayList<>();
//    private FirebaseRecyclerAdapter firebaseRecyclerAdapter;
//    private FirebaseAuth firebaseAuth;
//    private FirebaseUser firebaseUser;


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
        initializeList();
    }

//    @Override
//    public void onStart(){
//        super.onStart();
//
//        dbUsers.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    if(snapshot.child("status").getValue() == 1){
//                        //userList = snapshot.getValue(UserModel.class);
//
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<UserModel, UserViewHolder>
//                        (
//                                UserModel.class,
//                                R.layout.list_user,
//                                UserViewHolder.class,
//                                dbUsers
//                        )
//                {
//                    @Override
//                    protected void populateViewHolder(final UserViewHolder viewHolder, final UserModel model, int position) {
//
//                        viewHolder.setEmail(model.getEmail());
//                        viewHolder.setNama(model.getNama());
//                        viewHolder.setOnClickListener(new UserViewHolder.ClickListener() {
//                            @Override
//                            public void onItemClick(View view, final int position) {
//
//                            }
//
//                            @Override
//                            public void onItemLongClick(View view,final int position) {
//                                final AlertDialog.Builder alertDelete = new AlertDialog.Builder(getActivity());
//                                alertDelete.setMessage("Are you sure want to deactivate this user ?").setCancelable(false)
//                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                int selectedItem = position;
//                                                firebaseRecyclerAdapter.getRef(selectedItem).removeValue();
//                                                firebaseRecyclerAdapter.notifyItemRemoved(selectedItem);
//                                                mRecyclerView.invalidate();
//                                                //FirebaseAuth.getInstance().deleteUserAsync(model.getUser_id());
//                                                onStart();
//                                            }
//
//                                        })
//                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                dialog.cancel();
//                                            }
//                                        });
//                                AlertDialog alert = alertDelete.create();
//                                alert.setTitle("Warning");
//                                alert.show();
//                            }
//                        });
//                    }
//
//
//                };
//        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
//    }

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

        mAdapter = new RecycleAdapter(usermodelList, getActivity());

        mRecyclerView.setAdapter(mAdapter);

        dbUsers = FirebaseDatabase.getInstance().getReference("Users");
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

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
        private ArrayList<UserModel> userList;


        public MyAdapter(ArrayList<UserModel> userList) {
            this.userList = userList;
        }

        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_user,parent,false);
            MyAdapter.MyViewHolder holder = new MyAdapter.MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tvEmail.setText(userList.get(position).getEmail());
            holder.tvName.setText(userList.get(position).getNama());
            holder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    if(isLongClick){
                        final AlertDialog.Builder alertDelete = new AlertDialog.Builder(getActivity());
                                alertDelete.setMessage("Are you sure want to deactivate this user ?").setCancelable(false)
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //ubah status menjadi 0
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
                    }
                }

            });
        }

        @Override
        public int getItemCount() {
            return userList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{

            public TextView tvName, tvEmail;
            private ItemClickListener itemClickListener;

            public MyViewHolder(View itemView) {
                super(itemView);
                tvName = (TextView) itemView.findViewById(R.id.tvNama);
                tvEmail = (TextView) itemView.findViewById(R.id.tvEmail);
                itemView.setOnLongClickListener(this);
            }
            public void setItemClickListener(ItemClickListener itemClickListener) {
                this.itemClickListener = itemClickListener;
            }

            @Override
            public boolean onLongClick(View v) {
                itemClickListener.onClick(v, getAdapterPosition(), true);
                return true;
            }
        }
    }

    private void initializeList(){
        usermodelList.clear();
        final DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usermodelList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if(snapshot.child("status").getValue().toString().equals("1")){
                        UserModel userModel = new UserModel();
                        userModel.setEmail(snapshot.child("email").getValue().toString());
                        userModel.setNama(snapshot.child("nama").getValue().toString());
                        usermodelList.add(userModel);
                    }
                }
                mRecyclerView.setAdapter(new MyAdapter(usermodelList));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
