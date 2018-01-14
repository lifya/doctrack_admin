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

import com.pln.www.activity.AddDocumentActivity;
import com.pln.www.R;
//import com.pln.www.TambahDokumentActivity;
import com.pln.www.model.ItemModel;
import com.pln.www.adapter.MyAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ACHI on 27/08/2017.
 */

public class DokumenFragment extends Fragment implements View.OnClickListener, SearchView.OnQueryTextListener {
    private static final String TAG = "RecyclerViewFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;
    private static final int DATASET_COUNT = 60; // menampilkan data sebanyak value
    private FloatingActionButton fabDocument;
    protected LayoutManagerType mCurrentLayoutManagerType;
    protected RecyclerView mRecyclerView;
    protected MyAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    private List<ItemModel> dataSet;

    @Override
    public void onClick(View v) {
        if(v == fabDocument){
            AddDocumentActivity addDocActivity = new AddDocumentActivity();
            gotoActivity(addDocActivity);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<ItemModel> filteredModelList = filter(dataSet, newText);

        mAdapter.setFilter(filteredModelList);
        return true;
    }

    private List<ItemModel> filter(List<ItemModel> models, String query) {
        query = query.toLowerCase(); final List<ItemModel> filteredModelList = new ArrayList<>();
        for (ItemModel model : models){
            final String text = model.getmJudul().toLowerCase();
            if (text.contains(query)){
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDataset();
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

        mAdapter = new MyAdapter(dataSet);

        mRecyclerView.setAdapter(mAdapter);

        fabDocument = (FloatingActionButton) rootView.findViewById(R.id.fabDocument);
        fabDocument.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

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

    private void initDataset() {

        dataSet = new ArrayList<>();
        dataSet.add(new ItemModel("Studi UKL-UPL Pembangunan SUT 70 KV Dukong- Manggar Tanjung batu Itam dan GI...", "PT. Adi Banuwa", "25-02-2018", "18:30", R.mipmap.on_process));
        dataSet.add(new ItemModel("pipit UKL-UPL Pembangunan SUT 70 KV Dukong- Manggar Tanjung batu Itam dan GI...", "PT. Adi Banuwa", "25-02-2018", "18:30", R.mipmap.on_process));
        dataSet.add(new ItemModel("achi UKL-UPL Pembangunan SUT 70 KV Dukong- Manggar Tanjung batu Itam dan GI...", "PT. Adi Banuwa", "25-02-2018", "18:30", R.mipmap.on_process));
        dataSet.add(new ItemModel("lifya UKL-UPL Pembangunan SUT 70 KV Dukong- Manggar Tanjung batu Itam dan GI...", "PT. Adi Banuwa", "25-02-2018", "18:30", R.mipmap.on_process));
        dataSet.add(new ItemModel("sari UKL-UPL Pembangunan SUT 70 KV Dukong- Manggar Tanjung batu Itam dan GI...", "PT. Adi Banuwa", "25-02-2018", "18:30", R.mipmap.on_process));
    }

    private void gotoActivity(Activity act){
        Intent intent = new Intent(getActivity(), act.getClass());
        startActivity(intent);
    }
}

