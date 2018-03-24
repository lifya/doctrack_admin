package com.pln.www.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.pln.www.R;
import com.pln.www.alert.FormDocumentDialog;
import com.pln.www.fragment.AmdalFragment;
import com.pln.www.model.DetailProses;
import com.pln.www.model.DetailProsesModel;
import com.pln.www.model.KonsultanModel;
import com.pln.www.model.KontrakModel;
import com.pln.www.model.PekerjaanModel;
import com.pln.www.viewholder.DetailProsesModelViewHolder;

import java.util.ArrayList;

/**
 * Created by User on 13/01/2018.
 */

public class DetailDocumentActivity extends AppCompatActivity {
    private ImageView ivBack, ivProses;
    private Button bAddDoc;
    private TextView tvSave, tvJudul, tvKonsultan, tvTanggalMulai, tvTanggalAKhir, tvTegangan, tvKms, tvProvinsi, tvKontrak;
    private ProgressDialog progressDialog;
    private DatabaseReference dbKonsultan, dbKontrak, dbPekerjaan, dbDetailProses;
    private Intent intent;
    private Bundle bundle;
    private String get_idPekerjaan, get_idKonsultan, get_idKontrak;
    private ArrayList<DetailProsesModel> listProses;
    private DetailDocumentActivity.RecycleAdapterProses adapterProses;

    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_dokumen);
        intent = getIntent();
        bundle = intent.getExtras();
        if(bundle != null){
            get_idPekerjaan = (String) bundle.get("id_pekerjaan");
            get_idKonsultan = (String) bundle.get("id_konsultan");
            get_idKontrak = (String) bundle.get("id_kontrak");
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.rvDetailProses);

        mLayoutManager = new LinearLayoutManager(DetailDocumentActivity.this);

        mRecyclerView.setLayoutManager(mLayoutManager);

        dbKonsultan = FirebaseDatabase.getInstance().getReference("Konsultan");
        dbKontrak = FirebaseDatabase.getInstance().getReference("Kontrak");
        dbPekerjaan = FirebaseDatabase.getInstance().getReference("Pekerjaan");
        dbDetailProses = FirebaseDatabase.getInstance().getReference("DetailProses");

        tvJudul = (TextView) findViewById(R.id.tvJudul);
        tvKonsultan = (TextView) findViewById(R.id.tvKonsultan);
        tvTegangan = (TextView) findViewById(R.id.tvTegangan);
        tvKms = (TextView) findViewById(R.id.tvKms);
        tvProvinsi = (TextView) findViewById(R.id.tvProvinsi);
        tvKontrak = (TextView) findViewById(R.id.tvKontrak);
        tvTanggalMulai = (TextView) findViewById(R.id.tvTanggalMulai);
        tvTanggalAKhir = (TextView) findViewById(R.id.tvTanggalAkhir);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivProses = (ImageView) findViewById(R.id.ivProses);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        initialize();
        initializeCRUD();

        ivProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddDoc();
            }
        });


    }

    @Override
    public void onBackPressed() {
        AddDocumentActivity doc = new AddDocumentActivity();
        sentoDocumentList();
    }

    private void sentoDocumentList() {
        Intent startIntent = new Intent(DetailDocumentActivity.this, DocumentTrackingActivity.class);
        startActivity(startIntent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(bundle != null){
            dbPekerjaan.child(get_idPekerjaan).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    PekerjaanModel pekerjaanModel = dataSnapshot.getValue(PekerjaanModel.class);
                    String namaPekerjaan = pekerjaanModel.getNamaPekerjaan();
                    String tegangan = pekerjaanModel.getTegangan();
                    String kms = pekerjaanModel.getKms();
                    String provinsi = pekerjaanModel.getProvinsi();
                    tvJudul.setText(namaPekerjaan);
                    tvTegangan.setText(tegangan);
                    tvKms.setText(kms);
                    tvProvinsi.setText(provinsi);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(DetailDocumentActivity.this, "Failed", Toast.LENGTH_LONG).show();
                    return;
                }
            });
            dbKonsultan.child(get_idKonsultan).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    KonsultanModel konsultanModel = dataSnapshot.getValue(KonsultanModel.class);
                    String namaKonsultan = konsultanModel.getNamaKonsultan();
                    tvKonsultan.setText(namaKonsultan);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(DetailDocumentActivity.this, "Failed", Toast.LENGTH_LONG).show();
                    return;
                }
            });
            dbKontrak.child(get_idKontrak).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    KontrakModel kontrakModel = dataSnapshot.getValue(KontrakModel.class);
                    String noKontrak = kontrakModel.getNoKontrak();
                    String tglMulai = kontrakModel.getTglMulai();
                    String tglAkhir = kontrakModel.getTglAkhir();
                    tvKontrak.setText(noKontrak);
                    tvTanggalMulai.setText(tglMulai);
                    tvTanggalAKhir.setText(tglAkhir);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(DetailDocumentActivity.this, "Failed", Toast.LENGTH_LONG).show();
                    return;
                }
            });

        }
    }

    public class RecycleAdapterProses extends RecyclerView.Adapter<DetailProsesModelViewHolder> {

        ArrayList<DetailProsesModel> dataProses = new ArrayList<>();

        public RecycleAdapterProses(ArrayList<DetailProsesModel> list) {
            dataProses = list;
        }

        @Override
        public DetailProsesModelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_proses, parent, false);

            return new DetailProsesModelViewHolder(view);
        }

        @Override
        public void onBindViewHolder(DetailProsesModelViewHolder holder, int position) {
            final String id_Pekerjaan = dataProses.get(position).getIdPekerjaan();
            holder.setNamaProses(dataProses.get(position).getNamaProses());
            holder.setStatusProses(dataProses.get(position).getStatus());
            holder.setTanggalProses(dataProses.get(position).getTanggal());
            holder.setKeteranganProses(dataProses.get(position).getKeterangan());

        }

        @Override
        public int getItemCount() {
            return dataProses.size();
        }

    }

    public void initialize(){
        listProses = new ArrayList<>();
        dbDetailProses.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listProses = new ArrayList<>();
                for(DataSnapshot detailProsesSnapshot : dataSnapshot.getChildren()){
                    String id = detailProsesSnapshot.getKey();

                    if(id.equals(get_idPekerjaan)){
                        for(DataSnapshot namaProses : detailProsesSnapshot.getChildren()) {
                            DetailProsesModel detailProsesModel = namaProses.getValue(DetailProsesModel.class);
                            listProses.add(detailProsesModel);
                        }
                    }
                }

                adapterProses = new RecycleAdapterProses(listProses);
                mRecyclerView.setAdapter(adapterProses);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initializeCRUD() {
        dbDetailProses.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                Toast.makeText(DetailDocumentActivity.this, "Added", Toast.LENGTH_LONG).show();
//                DetailProsesModel detailProsesModel = null;
//                String id = dataSnapshot.getKey();
//                boolean flag;
//
//                for(DataSnapshot namaProses : dataSnapshot.getChildren()) {
//                    flag = false;
//
//                    for(DetailProsesModel detailProses : listProses){
//                        if(detailProses.getNamaProses().equals(namaProses.getKey())){
//                           flag = true;
//                        }
//                    }
//
//                    if(flag == false){
//                        detailProsesModel = namaProses.getValue(DetailProsesModel.class);
//                        break;
//                    }
//                }
//
//                if(detailProsesModel != null){
//                    listProses.add(0, detailProsesModel);
//                }
//
//                adapterProses = new RecycleAdapterProses(listProses);
//                mRecyclerView.setAdapter(adapterProses);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void goToAddDoc(){
        if(bundle != null) {
            get_idPekerjaan = (String) bundle.get("id_pekerjaan");
        }
        FragmentManager manager = getSupportFragmentManager();
        FormDocumentDialog dialogAdd = new FormDocumentDialog();
        Bundle bundle = new Bundle();
        bundle.putString("idPekerjaan", get_idPekerjaan);
        bundle.putString("idKonsultan", get_idKonsultan);
        bundle.putString("idKontrak", get_idKontrak);
        dialogAdd.setArguments(bundle);
        dialogAdd.show(manager, dialogAdd.getTag());
    }

}
