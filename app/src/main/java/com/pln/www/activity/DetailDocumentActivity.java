package com.pln.www.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.pln.www.R;
import com.pln.www.alert.FormDocumentDialog;
import com.pln.www.model.DetailProsesModel;
import com.pln.www.model.KonsultanModel;
import com.pln.www.model.KontrakModel;
import com.pln.www.model.PekerjaanModel;
import com.pln.www.viewholder.DetailProsesModelViewHolder;

/**
 * Created by User on 13/01/2018.
 */

public class DetailDocumentActivity extends AppCompatActivity {
    private ImageView ivBack;
    private Button bAddDoc;
    private TextView tvSave, tvJudul, tvKonsultan, tvTanggalMulai, tvTanggalAKhir, tvTegangan, tvKms, tvProvinsi, tvKontrak;
    private ProgressDialog progressDialog;
    private DatabaseReference dbKonsultan, dbKontrak, dbPekerjaan, dbDetailProses;
    private Intent intent;
    private Bundle bundle;
    private String get_idPekerjaan, get_idKonsultan, get_idKontrak;
    private FirebaseRecyclerAdapter firebaseRecyclerAdapter;

    ExpandableTextView expandableTextView;
    TextView textView;
    String longText = "Detail " +
           "Amandemen" +
            "fcghjnkmjijiubhkmlljiugyvhnkjkmlkkloj" +
            "bgvhjn.lkklol8ghjnkjloiyftgbjnlkjiif" +
            "gfytuhloilkjcfyuuol, bctrdtuyj/o;lp897f5y4w6" +
            "cftcvhjbn.iui767fvhmmoiklkgtrx";

    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_dokumen);
        intent = getIntent();
        bundle = intent.getExtras();

        mRecyclerView = (RecyclerView) findViewById(R.id.rvDetailProses);

        dbKonsultan = FirebaseDatabase.getInstance().getReference("Konsultan");
        dbKontrak = FirebaseDatabase.getInstance().getReference("Kontrak");
        dbPekerjaan = FirebaseDatabase.getInstance().getReference("Pekerjaan");
        dbDetailProses = FirebaseDatabase.getInstance().getReference("Detail_Proses");

        tvJudul = (TextView) findViewById(R.id.tvJudul);
        tvKonsultan = (TextView) findViewById(R.id.tvKonsultan);
        tvTegangan = (TextView) findViewById(R.id.tvTegangan);
        tvKms = (TextView) findViewById(R.id.tvKms);
        tvProvinsi = (TextView) findViewById(R.id.tvProvinsi);
        tvKontrak = (TextView) findViewById(R.id.tvKontrak);
        tvTanggalMulai = (TextView) findViewById(R.id.tvTanggalMulai);
        tvTanggalAKhir = (TextView) findViewById(R.id.tvTanggalAkhir);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        tvSave = (TextView) findViewById(R.id.tvSave);
        bAddDoc = (Button) findViewById(R.id.buttonProses);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        bAddDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddDoc();
            }
        });

//        expandableTextView = (ExpandableTextView)findViewById(R.id.expandable_text_view);
//        expandableTextView.setText(longText);

//        expandableTextView.setOnExpandStateChangeListener(new ExpandableTextView.OnExpandStateChangeListener() {
//            @Override
//            public void onExpandStateChanged(TextView textView, boolean isExpanded) {
//                Toast.makeText(DetailDocumentActivity.this, "Expandable : "+isExpanded, Toast.LENGTH_SHORT).show();
//            }
//        });
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
            get_idPekerjaan = (String) bundle.get("id_pekerjaan");
            get_idKonsultan = (String) bundle.get("id_konsultan");
            get_idKontrak = (String) bundle.get("id_kontrak");

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

            firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<DetailProsesModel, DetailProsesModelViewHolder>(
                    DetailProsesModel.class,
                    R.layout.list_proses,
                    DetailProsesModelViewHolder.class,
                    dbDetailProses.child(get_idPekerjaan).orderByKey()
            ) {
                @Override
                protected void populateViewHolder(DetailProsesModelViewHolder viewHolder, DetailProsesModel model, int position) {
                    viewHolder.setNamaProses(model.getIdProses());
                    viewHolder.setStatusProses(model.getStatus());
                    viewHolder.setTanggalProses(model.getTanggal());
                    viewHolder.setKeteranganProses(model.getKeterangan());
                }
            };
            mRecyclerView.setAdapter(firebaseRecyclerAdapter);
        }
    }

    private void goToAddDoc(){
        if(bundle != null) {
            get_idPekerjaan = (String) bundle.get("id_pekerjaan");
        }
        FragmentManager manager = getSupportFragmentManager();
        FormDocumentDialog dialogAdd = new FormDocumentDialog();
        Bundle bundle = new Bundle();
        bundle.putString("idPekerjaan", get_idPekerjaan);
        dialogAdd.setArguments(bundle);
        dialogAdd.show(manager, dialogAdd.getTag());
    }
}
