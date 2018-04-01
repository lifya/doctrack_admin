package com.pln.www.activity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pln.www.R;
import com.pln.www.alert.FormDocumentDialog;
import com.pln.www.model.DocumentModel;
import com.pln.www.model.KontrakModel;
import com.pln.www.model.PekerjaanModel;
import com.pln.www.viewholder.DocumentViewHolder;

import java.util.ArrayList;

/**
 * Created by User on 13/01/2018.
 */

public class DetailWorkDocumentActivity extends AppCompatActivity {
    private ImageView ivBack, ivProses;
    private TextView tvJalur, tvKonsultan, tvTanggalMulai, tvTanggalAKhir, tvTegangan, tvKms, tvProvinsi, tvKontrak;
    private DatabaseReference dbKontrak, dbPekerjaan, dbDetailProses;
    private Intent intent;
    private Bundle bundle;
    private String get_idPekerjaan, get_idKontrak;
    private ArrayList<DocumentModel> listProses;
    private DetailWorkDocumentActivity.RecycleAdapterProses adapterProses;
    private DownloadManager downloadManager;

    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_work_document);
        intent = getIntent();
        bundle = intent.getExtras();
        if(bundle != null){
            get_idPekerjaan = (String) bundle.get("id_pekerjaan");
            get_idKontrak = (String) bundle.get("id_kontrak");
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.rvDetailProses);

        mLayoutManager = new LinearLayoutManager(DetailWorkDocumentActivity.this);

        mRecyclerView.setLayoutManager(mLayoutManager);

        dbKontrak = FirebaseDatabase.getInstance().getReference("Kontrak");
        dbPekerjaan = FirebaseDatabase.getInstance().getReference("Pekerjaan");
        dbDetailProses = FirebaseDatabase.getInstance().getReference("DetailProses");

        tvJalur = (TextView) findViewById(R.id.tvJalur);
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
        sentoDocumentList();
    }

    private void sentoDocumentList() {
        Intent startIntent = new Intent(DetailWorkDocumentActivity.this, WorkListActivity.class);
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
                    String namaPekerjaan = pekerjaanModel.getNamaJalur();
                    String tegangan = pekerjaanModel.getTegangan();
                    String kms = pekerjaanModel.getKms();
                    String provinsi = pekerjaanModel.getProvinsi();
                    String jenisPekerjaan = pekerjaanModel.getJenisPekerjaan();
                    tvJalur.setText(namaPekerjaan);
                    tvTegangan.setText(tegangan);
                    tvKms.setText(kms);
                    tvProvinsi.setText(provinsi);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(DetailWorkDocumentActivity.this, "Failed", Toast.LENGTH_LONG).show();
                    return;
                }
            });

            dbKontrak.child(get_idKontrak).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    KontrakModel kontrakModel = dataSnapshot.getValue(KontrakModel.class);
                    String noKontrak = kontrakModel.getNoKontrak();
                    String namaKonsultan = kontrakModel.getNamaKonsultan();
                    String tglMulai = kontrakModel.getTglMulai();
                    String tglAkhir = kontrakModel.getTglAkhir();
                    tvKontrak.setText(noKontrak);
                    tvKonsultan.setText(namaKonsultan);
                    tvTanggalMulai.setText(tglMulai);
                    tvTanggalAKhir.setText(tglAkhir);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(DetailWorkDocumentActivity.this, "Failed", Toast.LENGTH_LONG).show();
                    return;
                }
            });

        }
    }

    public class RecycleAdapterProses extends RecyclerView.Adapter<DocumentViewHolder> {

        ArrayList<DocumentModel> dataProses = new ArrayList<>();

        public RecycleAdapterProses(ArrayList<DocumentModel> list) {
            dataProses = list;
        }

        @Override
        public DocumentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_document, parent, false);

            return new DocumentViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final DocumentViewHolder holder, final int position) {
            final String id_Pekerjaan = dataProses.get(position).getIdPekerjaan();
            holder.setTvFIleProses(dataProses.get(position).getNamaFile());
            holder.setNamaProses(dataProses.get(position).getNamaDokumen());
            holder.setStatusProses(dataProses.get(position).getStatus());
            holder.setTanggalProses(dataProses.get(position).getTanggal());
            holder.setKeteranganProses(dataProses.get(position).getKeterangan());

            holder.getTvFIleProses(dataProses.get(position).getNamaFile()).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    downloadManager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
                    Uri uri = Uri.parse(dataProses.get(position).getUriFile());

                    DownloadManager.Request request = new DownloadManager.Request(uri);
                    request.setDescription("Download Completed").setTitle(dataProses.get(position).getNamaFile());
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, dataProses.get(position).getNamaFile());
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    Long reference = downloadManager.enqueue(request);
                }
            });



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
                            DocumentModel documentModel = namaProses.getValue(DocumentModel.class);
                            listProses.add(documentModel);
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
            bundle.putString("idKontrak", get_idKontrak);
            dialogAdd.setArguments(bundle);
            dialogAdd.show(manager, dialogAdd.getTag());




    }

}
