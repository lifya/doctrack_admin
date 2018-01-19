package com.pln.www.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pln.www.R;
import com.pln.www.model.KonsultanModel;
import com.pln.www.model.KontrakModel;
import com.pln.www.model.PekerjaanModel;

/**
 * Created by User on 13/01/2018.
 */

public class DetailDocumentActivity extends AppCompatActivity {
    private ImageView ivBack;
    private TextView tvSave, tvJudul, tvKonsultan, tvTanggalMulai, tvTanggalAKhir, tvJenis;
    private ProgressDialog progressDialog;
    private DatabaseReference dbKonsultan, dbKontrak, dbPekerjaan;
    private Intent intent;
    private Bundle bundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_dokumen);
        intent = getIntent();
        bundle = intent.getExtras();

        dbKonsultan = FirebaseDatabase.getInstance().getReference("Konsultan");
        dbKontrak = FirebaseDatabase.getInstance().getReference("Kontrak");
        dbPekerjaan = FirebaseDatabase.getInstance().getReference("Pekerjaan");

        tvJudul = (TextView) findViewById(R.id.tvJudul);
        tvKonsultan = (TextView) findViewById(R.id.tvKonsultan);
        tvJenis = (TextView) findViewById(R.id.tvJenis);
        tvTanggalMulai = (TextView) findViewById(R.id.tvTanggalMulai);
        tvTanggalAKhir = (TextView) findViewById(R.id.tvTanggalAkhir);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        tvSave = (TextView) findViewById(R.id.tvSave);

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
    }

    @Override
    protected void onStart() {
        super.onStart();
        String get_idPekerjaan, get_idKonsultan, get_idKontrak;

        if(bundle != null){
            get_idPekerjaan = (String) bundle.get("id_pekerjaan");
            get_idKonsultan = (String) bundle.get("id_konsultan");
            get_idKontrak = (String) bundle.get("id_kontrak");

            dbPekerjaan.child(get_idPekerjaan).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    PekerjaanModel pekerjaanModel = dataSnapshot.getValue(PekerjaanModel.class);
                    String namaPekerjaan = pekerjaanModel.getNamaPekerjaan();
                    String jenisPekerjaan = pekerjaanModel.getJenisPekerjaan();
                    tvJudul.setText(namaPekerjaan);
                    tvJenis.setText(jenisPekerjaan);
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
                    String tglMulai = kontrakModel.getTglMulai();
                    String tglAkhir = kontrakModel.getTglAkhir();
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
}
