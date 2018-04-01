package com.pln.www.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pln.www.R;
import com.pln.www.model.KonsultanModel;
import com.pln.www.model.KontrakModel;
import com.pln.www.model.PekerjaanModel;


import java.util.Calendar;

public class AddWorkActivity extends AppCompatActivity{

    private ImageView ivBack;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private EditText Ed1, Ed2, etJudul, etTegangan, etKms, etProvinsi, etKonsultan, etKontrak, etUploadFile;
    private TextView tvSave;
    private Calendar mCurrentDate;
    private int day, month, year;
    String idKonsultan, idKontrak, idPekerjaan;
    private DatabaseReference dbKonsultan, dbKontrak, dbPekerjaan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_work);
        dbKonsultan = FirebaseDatabase.getInstance().getReference("Konsultan");
        dbKontrak = FirebaseDatabase.getInstance().getReference("Kontrak");
        dbPekerjaan = FirebaseDatabase.getInstance().getReference("Pekerjaan");
        tvSave = (TextView) findViewById(R.id.tvSave);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        radioGroup = (RadioGroup) findViewById(R.id.rgTipe);
        etJudul = (EditText) findViewById(R.id.etJudul);
        etTegangan = (EditText) findViewById(R.id.etTegangan);
        etKms = (EditText) findViewById(R.id.etKms);
        etProvinsi = (EditText) findViewById(R.id.etProvinsi);
        etKonsultan = (EditText) findViewById(R.id.etKonsultan);
        etKontrak = (EditText) findViewById(R.id.etKontrak);
        etUploadFile = (EditText) findViewById(R.id.etUploadFile);
        Ed1 = (EditText) findViewById(R.id.etTglMulai);
        Ed2 = (EditText) findViewById(R.id.etTglAkhir);
        mCurrentDate = Calendar.getInstance();
        day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month = mCurrentDate.get(Calendar.MONTH);
        year = mCurrentDate.get(Calendar.YEAR);

        month=month+1;
        Ed1.setText(day+"/"+month+"/"+year);
        Ed2.setText(day+"/"+month+"/"+year);

        Ed1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddWorkActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int montOfYear, int dayOfMonth) {
                        montOfYear = montOfYear+1;
                        Ed1.setText(dayOfMonth+"/"+montOfYear+"/"+year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        Ed2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddWorkActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int montOfYear, int dayOfMonth) {
                        montOfYear = montOfYear+1;
                        Ed2.setText(dayOfMonth+"/"+montOfYear+"/"+year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    saveData();

                }
        });

    }

    @Override
    public void onBackPressed() {
        AddWorkActivity doc = new AddWorkActivity();
        sentoDocumentList();
    }

    private void sentoDocumentList() {
        Intent startIntent = new Intent(AddWorkActivity.this, WorkListActivity.class);
        startActivity(startIntent);
        finish();
    }

    private String addListeneronButton(){
        int radiobuttonid = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(radiobuttonid);
        String jenisDoc = radioButton.getText().toString().trim();
        return jenisDoc;
    }

    private void saveData(){
        String jenisDoc = addListeneronButton();
        String judul = etJudul.getText().toString().trim();
        String tegangan = etTegangan.getText().toString().trim();
        String kms = etKms.getText().toString().trim();
        String provinsi = etProvinsi.getText().toString().trim();
        String konsultan = etKonsultan.getText().toString().trim();
        String kontrak = etKontrak.getText().toString().trim();
        String tglMulai = Ed1.getText().toString().trim();
        String tglAkhir = Ed2.getText().toString().trim();


        if(TextUtils.isEmpty(judul)){
            Toast.makeText(this, "Please Enter The Title", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(tegangan)){
            Toast.makeText(this, "Please Enter The Tegangan", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(kms)){
            Toast.makeText(this, "Please Enter The KMS", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(provinsi)){
            Toast.makeText(this, "Please Enter The Provinsi", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(konsultan)){
            Toast.makeText(this, "Please Enter The Consultant", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(kontrak)){
            Toast.makeText(this, "Please Enter The Contract", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(tglMulai)){
            Toast.makeText(this, "Please Enter The Date", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(tglAkhir)){
            Toast.makeText(this, "Please Enter The Date", Toast.LENGTH_LONG).show();
            return;
        }

        onWait();

        idKonsultan = dbKonsultan.push().getKey();
        final KonsultanModel konsultanModel = new KonsultanModel(idKonsultan, konsultan);
        dbKonsultan.child(idKonsultan).setValue(konsultanModel);

        idKontrak = dbKontrak.push().getKey();
        final KontrakModel kontrakModel = new KontrakModel(idKontrak, kontrak, tglMulai, tglAkhir);
        dbKontrak.child(idKontrak).setValue(kontrakModel);

        idPekerjaan = dbPekerjaan.push().getKey();
        final PekerjaanModel pekerjaanModel = new PekerjaanModel(idPekerjaan, idKonsultan, idKontrak, judul, tegangan, kms, provinsi, jenisDoc);
        //dbPekerjaan.child(idPekerjaan).child(idKonsultan).child(idKontrak).setValue(pekerjaanModel);
        dbPekerjaan.child(idPekerjaan).setValue(pekerjaanModel);

        //progressDialog.dismiss();

        //startActivity(getIntent());
//        Toast.makeText(this, "Successes", Toast.LENGTH_LONG).show();
//        return;

        dbPekerjaan.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Intent intent = new Intent(AddWorkActivity.this, DetailWorkDocumentActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("id_pekerjaan",idPekerjaan);
//                bundle.putString("id_konsultan",idKonsultan);
//                bundle.putString("id_kontrak",idKontrak);
                intent.putExtra("id_pekerjaan", idPekerjaan);
                intent.putExtra("id_konsultan", idKonsultan);
                intent.putExtra("id_kontrak", idKontrak);
//                intent.putExtra("Key1",bundle);
                startActivity(intent);
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

        finish();
    }

    public void onWait(){
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void checkData(String judul, String tegangan, String kms, String provinsi, String konsultan, String kontrak, String tglMulai, String tglAkhir){
        if(TextUtils.isEmpty(judul)){
            Toast.makeText(this, "Please Enter The Title", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(tegangan)){
            Toast.makeText(this, "Please Enter The Tegangan", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(kms)){
            Toast.makeText(this, "Please Enter The KMS", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(provinsi)){
            Toast.makeText(this, "Please Enter The Provinsi", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(konsultan)){
            Toast.makeText(this, "Please Enter The Consultant", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(kontrak)){
            Toast.makeText(this, "Please Enter The Contract", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(tglMulai)){
            Toast.makeText(this, "Please Enter The Date", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(tglAkhir)){
            Toast.makeText(this, "Please Enter The Date", Toast.LENGTH_LONG).show();
            return;
        }
    }


}
