package com.pln.www.fragment;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pln.www.activity.AddDocumentActivity;
import com.pln.www.R;
//import com.pln.www.TambahDokumentActivity;
import com.pln.www.activity.DetailDocumentActivity;
import com.pln.www.model.ItemModel;
import com.pln.www.adapter.MyAdapter;
import com.pln.www.model.KonsultanModel;
import com.pln.www.model.KontrakModel;
import com.pln.www.model.PekerjaanModel;
import com.pln.www.model.UserModel;
import com.pln.www.viewholder.PekerjaanModelViewHolder;
import com.pln.www.viewholder.UserViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ACHI on 27/08/2017.
 */

public class DokumenFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "RecyclerViewFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;
    private static final int DATASET_COUNT = 60; // menampilkan data sebanyak value
    private FloatingActionButton fabDocument;
    protected LayoutManagerType mCurrentLayoutManagerType;
    protected RecyclerView mRecyclerView;
    protected MyAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    private FirebaseRecyclerAdapter firebaseRecyclerAdapter1, firebaseRecyclerAdapter2;
    private DatabaseReference dbPekerjaan;
    private List<ItemModel> dataSet;

    @Override
    public void onClick(View v) {
        if(v == fabDocument){
            AddDocumentActivity addDocActivity = new AddDocumentActivity();
            gotoActivity(addDocActivity);
        }
    }

//    @Override
//    public boolean onQueryTextSubmit(String query) {
//        return false;
//    }
//
//    @Override
//    public boolean onQueryTextChange(String newText) {
//        final List<ItemModel> filteredModelList = filter(dataSet, newText);
//
//        mAdapter.setFilter(filteredModelList);
//        return true;
//    }

//    private List<ItemModel> filter(List<ItemModel> models, String query) {
//        query = query.toLowerCase(); final List<ItemModel> filteredModelList = new ArrayList<>();
//        for (ItemModel model : models){
//            final String text = model.getmJudul().toLowerCase();
//            if (text.contains(query)){
//                filteredModelList.add(model);
//            }
//        }
//        return filteredModelList;
//    }

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();

        firebaseRecyclerAdapter1 = new FirebaseRecyclerAdapter<PekerjaanModel, PekerjaanModelViewHolder>(
                PekerjaanModel.class,
                R.layout.list_view,
                PekerjaanModelViewHolder.class,
                dbPekerjaan.child("Pekerjaan")
        ) {
            @Override
            protected void populateViewHolder(final PekerjaanModelViewHolder viewHolder, final PekerjaanModel model, int position) {
                final String id_Pekerjaan = this.getRef(position).getKey();
                final String id_Konsultan = model.getIdKonsultan();
                final String id_Kontrak = model.getIdKontrak();
                viewHolder.setNamaPekerjaan(model.getNamaPekerjaan());
                dbPekerjaan.child("Konsultan").child(id_Konsultan).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        KonsultanModel konsultanModel = dataSnapshot.getValue(KonsultanModel.class);
                        String namaKonsultan = konsultanModel.getNamaKonsultan();
                        viewHolder.setNamaKonsultan(namaKonsultan);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getActivity(), "Failed to Get Consultant ID", Toast.LENGTH_LONG).show();
                        return;
                    }
                });
                dbPekerjaan.child("Kontrak").child(id_Kontrak).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        KontrakModel kontrakModel = dataSnapshot.getValue(KontrakModel.class);
                        String tanggalKontrak = kontrakModel.getTglMulai();
                        viewHolder.setTanggal(tanggalKontrak);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getActivity(), "Failed to Get Contract ID", Toast.LENGTH_LONG).show();
                        return;
                    }
                });

                viewHolder.setOnClickListener(new UserViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getActivity(), DetailDocumentActivity.class);
                        intent.putExtra("id_pekerjaan", id_Pekerjaan);
                        intent.putExtra("id_konsultan", id_Konsultan);
                        intent.putExtra("id_kontrak", id_Kontrak);
                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                });
            }
        };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dokumen, container, false);
        rootView.setTag(TAG);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        mLayoutManager = new LinearLayoutManager(getActivity());

        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {

            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);
        //mRecyclerView.setAdapter(firebaseRecyclerAdapter1);
        //mAdapter = new MyAdapter(dataSet);

        //mRecyclerView.setAdapter(mAdapter);

        dbPekerjaan = FirebaseDatabase.getInstance().getReferenceFromUrl("https://tracking-user.firebaseio.com");

        fabDocument = (FloatingActionButton) rootView.findViewById(R.id.fabDocument);
        fabDocument.setOnClickListener(this);

        return rootView;
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
                mAdapter.setFilter(dataSet);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                return true;
            }
        });
    }


    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
                mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
                break;
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
    }

    private void gotoActivity(Activity act){
        Intent intent = new Intent(getActivity(), act.getClass());
        startActivity(intent);
    }
}

