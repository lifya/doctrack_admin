package com.pln.www.fragment;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.pln.www.R;
//import com.pln.www.TambahDokumentActivity;
import com.pln.www.adapter.ItemModel;
import com.pln.www.adapter.MyAdapter;

import java.util.ArrayList;

/**
 * Created by ACHI on 27/08/2017.
 */

public class DokumenFragment extends Fragment {
    private static final String TAG = "RecyclerViewFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;
    private static final int DATASET_COUNT = 60; // menampilkan data sebanyak value

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    protected LayoutManagerType mCurrentLayoutManagerType;

    protected RecyclerView mRecyclerView;
    protected MyAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<ItemModel> dataSet;


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


        return rootView;

//        FloatingActionButton fabs = (FloatingActionButton)rootView.findViewById(R.id.fabs);
//        fabs.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), TambahDokumentActivity.class);
//                startActivity(intent);
//            }
//        });

    }

    /**
     * Set RecyclerView's LayoutManager to the one given.
     *
     * @param layoutManagerType Type of layout manager to switch to.
     */
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
    /**
     * Generates Strings for RecyclerView's adapter. This data would usually come
     * from a local content provider or remote server.
     */
    private void initDataset() {

        dataSet = new ArrayList<>();
        dataSet.add(new ItemModel("Studi UKL-UPL Pembangunan SUT 70 KV Dukong- Manggar Tanjung batu Itam dan GI...", "PT. Adi Banuwa", "25-02-2018", "18:30", R.mipmap.on_process));
        dataSet.add(new ItemModel("Studi UKL-UPL Pembangunan SUT 70 KV Dukong- Manggar Tanjung batu Itam dan GI...", "PT. Adi Banuwa", "25-02-2018", "18:30", R.mipmap.on_process));
        dataSet.add(new ItemModel("Studi UKL-UPL Pembangunan SUT 70 KV Dukong- Manggar Tanjung batu Itam dan GI...", "PT. Adi Banuwa", "25-02-2018", "18:30", R.mipmap.on_process));
        dataSet.add(new ItemModel("Studi UKL-UPL Pembangunan SUT 70 KV Dukong- Manggar Tanjung batu Itam dan GI...", "PT. Adi Banuwa", "25-02-2018", "18:30", R.mipmap.on_process));
        dataSet.add(new ItemModel("Studi UKL-UPL Pembangunan SUT 70 KV Dukong- Manggar Tanjung batu Itam dan GI...", "PT. Adi Banuwa", "25-02-2018", "18:30", R.mipmap.on_process));
    }
}

