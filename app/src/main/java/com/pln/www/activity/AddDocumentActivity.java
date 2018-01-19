package com.pln.www.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pln.www.R;
import com.pln.www.model.KonsultanModel;
import com.pln.www.model.KontrakModel;
import com.pln.www.model.PekerjaanModel;
import com.pln.www.alert.FormDocumentDialog;

import java.util.Calendar;

public class AddDocumentActivity extends AppCompatActivity{

    private ImageView ivGetFile, ivBack;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private EditText Ed1, Ed2, etJudul, etKonsultan, etKontrak, etUploadFile;
    private TextView tvSave;
    private Calendar mCurrentDate;
    private int day, month, year;
    private Button bAddDoc;
    private ProgressDialog progressDialog;
    private DatabaseReference dbKonsultan, dbKontrak, dbPekerjaan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_document);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.tbAddDoc);
//        setSupportActionBar(toolbar);
       RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        dbKonsultan = FirebaseDatabase.getInstance().getReference("Konsultan");
        dbKontrak = FirebaseDatabase.getInstance().getReference("Kontrak");
        dbPekerjaan = FirebaseDatabase.getInstance().getReference("Pekerjaan");
        //ImageView imageView = (ImageView) findViewById(R.id.imagev1);
        tvSave = (TextView) findViewById(R.id.tvSave);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        radioGroup = (RadioGroup) findViewById(R.id.rgTipe);
        etJudul = (EditText) findViewById(R.id.etJudul);
        etKonsultan = (EditText) findViewById(R.id.etKonsultan);
        etKontrak = (EditText) findViewById(R.id.etKontrak);
        etUploadFile = (EditText) findViewById(R.id.etUploadFile);
        etUploadFile.setEnabled(false);
        Ed1 = (EditText) findViewById(R.id.etTglMulai);
        Ed2 = (EditText) findViewById(R.id.etTglAkhir);
        bAddDoc = (Button) findViewById(R.id.buttonProses);
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddDocumentActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddDocumentActivity.this, new DatePickerDialog.OnDateSetListener() {
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

        bAddDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddDoc();
            }
        });
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
        String konsultan = etKonsultan.getText().toString().trim();
        String kontrak = etKontrak.getText().toString().trim();
        String tglMulai = Ed1.getText().toString().trim();
        String tglAkhir = Ed2.getText().toString().trim();

//        Toast.makeText(this, " " + jenisDoc, Toast.LENGTH_LONG).show();
//        return;

        if(TextUtils.isEmpty(judul)){
            Toast.makeText(this, "Please Enter The Title", Toast.LENGTH_LONG).show();
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

        String idKonsultan = dbKonsultan.push().getKey();
        KonsultanModel konsultanModel = new KonsultanModel(idKonsultan, konsultan);
        dbKonsultan.child(idKonsultan).setValue(konsultanModel);

        String idKontrak = dbKontrak.push().getKey();
        KontrakModel kontrakModel = new KontrakModel(idKontrak, kontrak, tglMulai, tglAkhir);
        dbKontrak.child(idKontrak).setValue(kontrakModel);

        String idPekerjaan = dbPekerjaan.push().getKey();
        PekerjaanModel pekerjaanModel = new PekerjaanModel(idPekerjaan, idKonsultan, idKontrak, judul, jenisDoc);
        //dbPekerjaan.child(idPekerjaan).child(idKonsultan).child(idKontrak).setValue(pekerjaanModel);
        dbPekerjaan.child(idPekerjaan).setValue(pekerjaanModel);

        progressDialog.dismiss();
        finish();
        startActivity(getIntent());
        Toast.makeText(this, "Successed", Toast.LENGTH_LONG).show();
        return;

    }

    public void onWait(){
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
    }

    private void checkData(String jenisDoc, String judul, String konsultan, String kontrak, String tglMulai, String tglAkhir){
        if(TextUtils.isEmpty(jenisDoc)){
            Toast.makeText(this, "Please Enter The Document Type", Toast.LENGTH_LONG).show();
        }
        if(TextUtils.isEmpty(judul)){
            Toast.makeText(this, "Please Enter The Title", Toast.LENGTH_LONG).show();
        }
        if(TextUtils.isEmpty(konsultan)){
            Toast.makeText(this, "Please Enter The Consultant", Toast.LENGTH_LONG).show();
        }
        if(TextUtils.isEmpty(kontrak)){
            Toast.makeText(this, "Please Enter The Contract", Toast.LENGTH_LONG).show();
        }
        if(TextUtils.isEmpty(tglMulai)){
            Toast.makeText(this, "Please Enter The Date", Toast.LENGTH_LONG).show();
        }
        if(TextUtils.isEmpty(tglAkhir)){
            Toast.makeText(this, "Please Enter The Date", Toast.LENGTH_LONG).show();
        }
    }

    private void goToAddDoc(){
        FragmentManager manager = getSupportFragmentManager();
        FormDocumentDialog dialogAdd = new FormDocumentDialog();
        dialogAdd.show(manager, dialogAdd.getTag());
    }
}
