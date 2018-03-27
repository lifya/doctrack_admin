
package com.pln.www.alert;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pln.www.Helper.Constant;
import com.pln.www.R;
import com.pln.www.activity.DetailDocumentActivity;
import com.pln.www.model.DetailProsesModel;
import com.pln.www.model.UploadFileModel;

import java.util.Calendar;

import static android.app.Activity.RESULT_OK;



/**
 * A simple {@link Fragment} subclass.
 */
public class FormDocumentDialog2 extends AppCompatDialogFragment implements View.OnClickListener{

    private ImageView imageView, ivGetFile;
    private Button bCancel, bAddDoc;
    public EditText Ed, etFile, etKeterangan;
    public Spinner spinnerProses, spinnerStatus;
    private String getSpinnerProses, getSpinnerStatus;
    private Calendar mCurrentDate;
    private int day, month, year;
    private ProgressDialog progressDialog;
    private Intent intent;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private DatabaseReference dbDetailProses, dbUploadFile;
    final static int PICK_PDF_CODE = 2342;
    private Uri dataUri;
    private String idPekerjaan, idKonsultan, idKontrak, idFile, namaFile;
    private EditText etUploadFile;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_form_document2, null);
        setCancelable(false);

        Bundle bundle = getArguments();
        if (bundle != null){
            idPekerjaan = bundle.getString("idPekerjaan");
            idKonsultan = bundle.getString("idKonsultan");
            idKontrak = bundle.getString("idKontrak");
        }

        bCancel = (Button) v.findViewById(R.id.btnCancel);
        bAddDoc = (Button) v.findViewById(R.id.btnAdd);
        progressDialog = new ProgressDialog(getActivity());
        spinnerProses = (Spinner) v.findViewById(R.id.spProses1);
        spinnerStatus = (Spinner) v.findViewById(R.id.spStatus);
        ivGetFile = (ImageView) v.findViewById(R.id.ivGetFile);
        etFile = (EditText) v.findViewById(R.id.etUploadFile);
        etKeterangan = (EditText) v.findViewById(R.id.etKeterangan);
        Ed = (EditText) v.findViewById(R.id.etTglProses);
        etUploadFile = (EditText) v.findViewById(R.id.etUploadFile);
        storageReference = FirebaseStorage.getInstance().getReference();
        mCurrentDate = Calendar.getInstance();
        day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month = mCurrentDate.get(Calendar.MONTH);
        year = mCurrentDate.get(Calendar.YEAR);

        month=month+1;
        Ed.setText(day+"/"+month+"/"+year);

        spinnerListener();
        Ed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int montOfYear, int dayOfMonth) {
                        montOfYear = montOfYear+1;
                        Ed.setText(dayOfMonth+"/"+montOfYear+"/"+year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        ivGetFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPDF();
            }
        });
        bAddDoc.setOnClickListener(this);
        bCancel.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        if(v == bCancel){
            getDialog().dismiss();
        }
        if(v == bAddDoc){
            btnAddClick(v);
            addDetailData();
        }
    }

    private void spinnerListener(){
        getSpinnerProses = spinnerProses.getItemAtPosition(0).toString();
        getSpinnerStatus = spinnerStatus.getItemAtPosition(0).toString();

        spinnerProses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getSpinnerProses = spinnerProses.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getSpinnerStatus = spinnerStatus.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });
    }
    private void addDetailData(){
        String etTanggal = Ed.getText().toString();
        String etKet = etKeterangan.getText().toString();

        if(TextUtils.isEmpty(etKet)){
            Toast.makeText(getActivity(), "Isi keterangan", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        dbUploadFile = FirebaseDatabase.getInstance().getReference("Uploads");
        idFile = dbUploadFile.push().getKey();
        namaFile = dataUri.getLastPathSegment().toString();
        final UploadFileModel fileModel = new UploadFileModel(idFile, namaFile, dataUri.toString());
        dbUploadFile.child(idFile).setValue(fileModel);

        dbDetailProses = FirebaseDatabase.getInstance().getReference("DetailProses");
        final DetailProsesModel detailProsesModel = new DetailProsesModel(idPekerjaan, getSpinnerProses, getSpinnerStatus, etTanggal, etKet, idFile, namaFile);
        dbDetailProses.child(idPekerjaan).child(getSpinnerProses).setValue(detailProsesModel);
        progressDialog.dismiss();

        Intent intent = new Intent(getActivity(), DetailDocumentActivity.class);
        intent.putExtra("id_pekerjaan", idPekerjaan);
        intent.putExtra("id_konsultan", idKonsultan);
        intent.putExtra("id_kontrak", idKontrak);
        startActivity(intent);

        getDialog().dismiss();
    }

    private void getPDF() {
        //creating an intent for file chooser
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Document"), PICK_PDF_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //when the user choses the file
        if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            dataUri = data.getData();
            if (dataUri != null) {
                Toast.makeText(this.getContext(), "one file chosen", Toast.LENGTH_SHORT).show();
//                UploadFileModel uploadFileModel = new UploadFileModel();
//                String namaFile = uploadFileModel.getName();
//                etUploadFile.setText(namaFile);

            }else{
                Toast.makeText(this.getContext(), "No file chosen", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void btnAddClick(View v) {
        StorageReference sRef = storageReference.child(Constant.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + ".pdf");
        sRef.putFile(dataUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    }
                });

    }
}
