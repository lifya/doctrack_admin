package com.pln.www.alert;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pln.www.Helper.Constant;
import com.pln.www.R;
import com.pln.www.activity.AddDocumentActivity;
import com.pln.www.model.DetailProses;
import com.pln.www.model.DetailProsesModel;
import com.pln.www.model.PekerjaanModel;
import com.pln.www.model.UploadFileModel;
import android.support.v7.widget.AppCompatButton;

import java.util.Calendar;

import static android.app.Activity.RESULT_OK;



/**
 * A simple {@link Fragment} subclass.
 */
public class FormDocumentDialog extends AppCompatDialogFragment implements View.OnClickListener{

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
    final static int PICK_PDF_CODE = 2342;
    private Uri dataUri;
    private String idPekerjaan;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_form_document, null);
        setCancelable(false);

        Bundle bundle = getArguments();
        if (bundle != null)
            idPekerjaan = bundle.getString("idPekerjaan");

        bCancel = (Button) v.findViewById(R.id.btnCancel);
        bAddDoc = (Button) v.findViewById(R.id.btnAdd);
        spinnerProses = (Spinner) v.findViewById(R.id.spProses1);
        spinnerStatus = (Spinner) v.findViewById(R.id.spStatus);
        ivGetFile = (ImageView) v.findViewById(R.id.ivGetFile);
        etFile = (EditText) v.findViewById(R.id.etUploadFile);
        etKeterangan = (EditText) v.findViewById(R.id.etKeterangan);
        databaseReference = FirebaseDatabase.getInstance().getReference(Constant.DATABASE_PATH_UPLOADS);
        storageReference = FirebaseStorage.getInstance().getReference();
        Ed = (EditText) v.findViewById(R.id.etTglProses);
        mCurrentDate = Calendar.getInstance();
        day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month = mCurrentDate.get(Calendar.MONTH);
        year = mCurrentDate.get(Calendar.YEAR);

        month=month+1;
        Ed.setText(day+"/"+month+"/"+year);

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

    public void onWait(){
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
    }
    @Override
    public void onClick(View v) {
        if(v == bCancel){
            getDialog().dismiss();
        }
        if(v == bAddDoc){
            addDetailData();
            //uploadFile(dataUri);
            //Toast.makeText(getActivity(), "" + getSpinnerProses, Toast.LENGTH_LONG).show();
        }
    }

    private void addDetailData(){

        final String etTanggal = Ed.getText().toString();
        final String etKet = etKeterangan.getText().toString();

        getSpinnerProses = spinnerProses.getItemAtPosition(0).toString();
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

        getSpinnerStatus = spinnerStatus.getItemAtPosition(0).toString();
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

        if(TextUtils.isEmpty(getSpinnerProses)){
            Toast.makeText(getActivity(), "Please Enter The Process", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(getSpinnerStatus)){
            Toast.makeText(getActivity(), "Please Enter The Status", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(etTanggal)){
            Toast.makeText(getActivity(), "Please Enter The Date", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(etKet)){
            Toast.makeText(getActivity(), "Please Enter The Date", Toast.LENGTH_LONG).show();
            return;
        }

        final DatabaseReference dbDetailProses;
        dbDetailProses = FirebaseDatabase.getInstance().getReference("Detail_Proses");

        DetailProsesModel detailProsesModel = new DetailProsesModel(idPekerjaan, getSpinnerProses, getSpinnerStatus, etTanggal, etKet);
        dbDetailProses.child(idPekerjaan).child(getSpinnerProses).setValue(detailProsesModel);
        Toast.makeText(getActivity(), "Successed !", Toast.LENGTH_LONG).show();
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
            }else{
                Toast.makeText(this.getContext(), "No file chosen", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void btnAddClick(View v) {
        //final String fileName = etUploadFile.getText().toString().trim();

        StorageReference sRef = storageReference.child(Constant.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + ".pdf");
        sRef.putFile(dataUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        progressBar.setVisibility(View.GONE);
                        //textViewStatus.setText("File Uploaded Successfully");
                        String id_file = databaseReference.push().getKey();

                        UploadFileModel upload = new UploadFileModel(id_file, dataUri.getLastPathSegment().toString(), taskSnapshot.getDownloadUrl().toString());
                        databaseReference.child(id_file).setValue(upload);
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
