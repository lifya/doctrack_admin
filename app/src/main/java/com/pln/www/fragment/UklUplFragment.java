package com.pln.www.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pln.www.R;
import com.pln.www.activity.DetailWorkDocumentActivity;
import com.pln.www.model.KontrakModel;
import com.pln.www.model.PekerjaanModel;
import com.pln.www.viewholder.PekerjaanViewHolder;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class UklUplFragment extends Fragment {


    private static final String TAG = "RecyclerViewFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;
    private static final int DATASET_COUNT = 60; // menampilkan data sebanyak value
    protected UklUplFragment.LayoutManagerType mCurrentLayoutManagerType;
    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    private FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    private DatabaseReference dbPekerjaan;
    private SearchView searchView;
    private ArrayList<PekerjaanModel> listPekerjaan;
    private RecycleAdapterPekerjaan adapterPekerjaan;


    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public class RecycleAdapterPekerjaan extends RecyclerView.Adapter<PekerjaanViewHolder>{
    ArrayList<PekerjaanModel> dataPekerjaan = new ArrayList<>();

    public RecycleAdapterPekerjaan(ArrayList<PekerjaanModel> list) {
        dataPekerjaan = list;
    }

        @Override
        public PekerjaanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_work, parent, false);

            return new PekerjaanViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final PekerjaanViewHolder holder, int position) {
            final String id_Pekerjaan = dataPekerjaan.get(position).getIdPekerjaan();
            final String id_Konsultan = dataPekerjaan.get(position).getIdKonsultan();
            final String id_Kontrak = dataPekerjaan.get(position).getIdKontrak();
            holder.setNamaPekerjaan(dataPekerjaan.get(position).getNamaJalur());
            holder.setTegangan(dataPekerjaan.get(position).getTegangan());
            holder.setKms(dataPekerjaan.get(position).getKms());
            holder.setProvinsi(dataPekerjaan.get(position).getProvinsi());

            dbPekerjaan.child("Kontrak").child(id_Kontrak).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    KontrakModel kontrakModel = dataSnapshot.getValue(KontrakModel.class);
                    String noKontrak = kontrakModel.getNoKontrak();
                    holder.setNoKontrak(noKontrak);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getActivity(), "Failed to Get Contract ID", Toast.LENGTH_LONG).show();
                    return;
                }
            });

            holder.setOnClickListener(new PekerjaanViewHolder.ClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(getActivity(), DetailWorkDocumentActivity.class);
                    intent.putExtra("id_pekerjaan", id_Pekerjaan);
                    intent.putExtra("id_konsultan", id_Konsultan);
                    intent.putExtra("id_kontrak", id_Kontrak);
                    startActivity(intent);
                }

                @Override
                public void onItemLongClick(View view, final int positionItem) {
                    final AlertDialog.Builder alertDelete = new AlertDialog.Builder(getActivity());
                    alertDelete.setMessage("Are you sure want to delete this document ?").setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String id = dataPekerjaan.get(positionItem).getIdPekerjaan();
                                    dbPekerjaan.child("Pekerjaan").child(id).removeValue();
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
            });
        }
        @Override
        public int getItemCount() {
            return dataPekerjaan.size();
        }

        public void setFilter(ArrayList<PekerjaanModel> list){
            dataPekerjaan = new ArrayList<>();
            dataPekerjaan.addAll(list);
            notifyDataSetChanged();
        }
}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ukl_upl, container, false);
        rootView.setTag(TAG);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        mLayoutManager = new LinearLayoutManager(getActivity());

        mCurrentLayoutManagerType = UklUplFragment.LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {

            mCurrentLayoutManagerType = (UklUplFragment.LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);

        dbPekerjaan = FirebaseDatabase.getInstance().getReference();

        searchView = (SearchView) rootView.findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase();

                ArrayList<PekerjaanModel> list = new ArrayList<>();
                for(PekerjaanModel pekerjaan : listPekerjaan){
                    String nama_pekerjaan = pekerjaan.getNamaJalur().toLowerCase();
                    if(nama_pekerjaan.contains(newText)){
                        list.add(pekerjaan);
                    }
                }

                adapterPekerjaan.setFilter(list);
                return true;
            }
        });

        initialize();
        initializeCRUD();

        return rootView;
    }

    private void initializeCRUD() {
        dbPekerjaan = FirebaseDatabase.getInstance().getReference();

        dbPekerjaan.child("Pekerjaan").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();

                for(PekerjaanModel pekerjaan : listPekerjaan){
                    String id = pekerjaan.getIdPekerjaan();
                    if(id.equals(key)){
                        listPekerjaan.remove(pekerjaan);
                        break;
                    }
                }

                adapterPekerjaan = new UklUplFragment.RecycleAdapterPekerjaan(listPekerjaan);
                mRecyclerView.setAdapter(adapterPekerjaan);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void initialize(){
        listPekerjaan = new ArrayList<>();
        dbPekerjaan.child("Pekerjaan").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listPekerjaan = new ArrayList<>();
                for(DataSnapshot pekerjaanSnapshot : dataSnapshot.getChildren()){
                    if(pekerjaanSnapshot.child("jenisPekerjaan").getValue().toString().equals("UKL-UPL")){
                        PekerjaanModel pekerjaanModel = pekerjaanSnapshot.getValue(PekerjaanModel.class);
                        listPekerjaan.add(pekerjaanModel);
                    }
                }

                adapterPekerjaan = new UklUplFragment.RecycleAdapterPekerjaan(listPekerjaan);
                mRecyclerView.setAdapter(adapterPekerjaan);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        //searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener(){

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                return true;
            }
        });
    }


    public void setRecyclerViewLayoutManager(UklUplFragment.LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
                mCurrentLayoutManagerType = UklUplFragment.LayoutManagerType.GRID_LAYOUT_MANAGER;
                break;
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = UklUplFragment.LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = UklUplFragment.LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
    }


}
