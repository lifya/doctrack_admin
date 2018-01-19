package com.pln.www.alert;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import com.pln.www.R;
import com.pln.www.activity.AddDocumentActivity;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class FormDocumentDialog extends AppCompatDialogFragment implements View.OnClickListener{

    private ImageView imageView;
    private Button bCancel, bAddDoc;
    public EditText Ed, etFile, etKeterangan;
    public Spinner spinnerProses, spinnerStatus;
    private String getSpinnerProses, getSpinnerStatus;
    private Calendar mCurrentDate;
    private int day, month, year;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_form_document, null);
        setCancelable(false);

        bCancel = (Button) v.findViewById(R.id.btnCancel);
        bAddDoc = (Button) v.findViewById(R.id.btnAdd);
        spinnerProses = (Spinner) v.findViewById(R.id.spProses1);
        spinnerStatus = (Spinner) v.findViewById(R.id.spStatus);
        etKeterangan = (EditText) v.findViewById(R.id.etKeterangan);
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

//        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                switch(which) {
//                    case DialogInterface.BUTTON_POSITIVE :
//                        TextView tv = (TextView) getActivity().findViewById(R.id.message);
//                        tv.setText("Hay Achi");
//                        break;
//
//                    case DialogInterface.BUTTON_NEGATIVE:
//                        break;
//                }
//            }
//        };
        bCancel.setOnClickListener(this);
        bAddDoc.setOnClickListener(this);
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
            Toast.makeText(getActivity(), "" + getSpinnerProses, Toast.LENGTH_LONG).show();
            return;
        }
    }

    private void addDetailData(){
        String etTanggal = Ed.getText().toString();
        String etKet = etKeterangan.getText().toString();
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

        if(getSpinnerProses.equals("Permohonan RTRW")){
            if(getSpinnerStatus.equals("Belum")){
                Intent intent = new Intent(getActivity(), AddDocumentActivity.class);
                intent.putExtra("namaProses_1", getSpinnerProses);
                intent.putExtra("namaStatus_1", getSpinnerStatus);
                intent.putExtra("tanggal_1", etTanggal);
                intent.putExtra("keterangan_1", etKet);
            }
            if(getSpinnerStatus.equals("Proses")){
                Intent intent = new Intent(getActivity(), AddDocumentActivity.class);
                intent.putExtra("namaProses_1", getSpinnerProses);
                intent.putExtra("namaStatus_1", getSpinnerStatus);
                intent.putExtra("tanggal_1", etTanggal);
                intent.putExtra("keterangan_1", etKet);
            }
            if(getSpinnerStatus.equals("Sudah")){
                Intent intent = new Intent(getActivity(), AddDocumentActivity.class);
                intent.putExtra("namaProses_1", getSpinnerProses);
                intent.putExtra("namaStatus_1", getSpinnerStatus);
                intent.putExtra("tanggal_1", etTanggal);
                intent.putExtra("keterangan_1", etKet);
            }
        }
        if(getSpinnerProses.equals("Pembahasan RTRW")){
            if(getSpinnerStatus.equals("Belum")){
                Intent intent = new Intent(getActivity(), AddDocumentActivity.class);
                intent.putExtra("namaProses_2", getSpinnerProses);
                intent.putExtra("namaStatus_2", getSpinnerStatus);
                intent.putExtra("tanggal_2", etTanggal);
                intent.putExtra("keterangan_2", etKet);
            }
            if(getSpinnerStatus.equals("Proses")){
                Intent intent = new Intent(getActivity(), AddDocumentActivity.class);
                intent.putExtra("namaProses_2", getSpinnerProses);
                intent.putExtra("namaStatus_2", getSpinnerStatus);
                intent.putExtra("tanggal_2", etTanggal);
                intent.putExtra("keterangan_2", etKet);
            }
            if(getSpinnerStatus.equals("Sudah")){
                Intent intent = new Intent(getActivity(), AddDocumentActivity.class);
                intent.putExtra("namaProses_2", getSpinnerProses);
                intent.putExtra("namaStatus_2", getSpinnerStatus);
                intent.putExtra("tanggal_2", etTanggal);
                intent.putExtra("keterangan_2", etKet);
            }
        }
        if(getSpinnerProses.equals("RTRW")){
            if(getSpinnerStatus.equals("Belum")){
                Intent intent = new Intent(getActivity(), AddDocumentActivity.class);
                intent.putExtra("namaProses_3", getSpinnerProses);
                intent.putExtra("namaStatus_3", getSpinnerStatus);
                intent.putExtra("tanggal_3", etTanggal);
                intent.putExtra("keterangan_3", etKet);
            }
            if(getSpinnerStatus.equals("Proses")){
                Intent intent = new Intent(getActivity(), AddDocumentActivity.class);
                intent.putExtra("namaProses_3", getSpinnerProses);
                intent.putExtra("namaStatus_3", getSpinnerStatus);
                intent.putExtra("tanggal_3", etTanggal);
                intent.putExtra("keterangan_3", etKet);
            }
            if(getSpinnerStatus.equals("Sudah")){
                Intent intent = new Intent(getActivity(), AddDocumentActivity.class);
                intent.putExtra("namaProses_3", getSpinnerProses);
                intent.putExtra("namaStatus_3", getSpinnerStatus);
                intent.putExtra("tanggal_3", etTanggal);
                intent.putExtra("keterangan_3", etKet);
            }
        }
        if(getSpinnerProses.equals("Draft UKL-UPL")){
            if(getSpinnerStatus.equals("Belum")){
                Intent intent = new Intent(getActivity(), AddDocumentActivity.class);
                intent.putExtra("namaProses_4", getSpinnerProses);
                intent.putExtra("namaStatus_4", getSpinnerStatus);
                intent.putExtra("tanggal_4", etTanggal);
                intent.putExtra("keterangan_4", etKet);
            }
            if(getSpinnerStatus.equals("Proses")){
                Intent intent = new Intent(getActivity(), AddDocumentActivity.class);
                intent.putExtra("namaProses_4", getSpinnerProses);
                intent.putExtra("namaStatus_4", getSpinnerStatus);
                intent.putExtra("tanggal_4", etTanggal);
                intent.putExtra("keterangan_4", etKet);
            }
            if(getSpinnerStatus.equals("Sudah")){
                Intent intent = new Intent(getActivity(), AddDocumentActivity.class);
                intent.putExtra("namaProses_4", getSpinnerProses);
                intent.putExtra("namaStatus_4", getSpinnerStatus);
                intent.putExtra("tanggal_4", etTanggal);
                intent.putExtra("keterangan_4", etKet);
            }
        }
        if(getSpinnerProses.equals("Pembahasan")){
            if(getSpinnerStatus.equals("Belum")){
                Intent intent = new Intent(getActivity(), AddDocumentActivity.class);
                intent.putExtra("namaProses_5", getSpinnerProses);
                intent.putExtra("namaStatus_5", getSpinnerStatus);
                intent.putExtra("tanggal_5", etTanggal);
                intent.putExtra("keterangan_5", etKet);
            }
            if(getSpinnerStatus.equals("Proses")){
                Intent intent = new Intent(getActivity(), AddDocumentActivity.class);
                intent.putExtra("namaProses_5", getSpinnerProses);
                intent.putExtra("namaStatus_5", getSpinnerStatus);
                intent.putExtra("tanggal_5", etTanggal);
                intent.putExtra("keterangan_5", etKet);
            }
            if(getSpinnerStatus.equals("Sudah")){
                Intent intent = new Intent(getActivity(), AddDocumentActivity.class);
                intent.putExtra("namaProses_5", getSpinnerProses);
                intent.putExtra("namaStatus_5", getSpinnerStatus);
                intent.putExtra("tanggal_5", etTanggal);
                intent.putExtra("keterangan_5", etKet);
            }
        }
        if(getSpinnerProses.equals("Perbaikan Dok")){
            if(getSpinnerStatus.equals("Belum")){
                Intent intent = new Intent(getActivity(), AddDocumentActivity.class);
                intent.putExtra("namaProses_6", getSpinnerProses);
                intent.putExtra("namaStatus_6", getSpinnerStatus);
                intent.putExtra("tanggal_6", etTanggal);
                intent.putExtra("keterangan_6", etKet);
            }
            if(getSpinnerStatus.equals("Proses")){
                if(getSpinnerStatus.equals("Belum")){
                    Intent intent = new Intent(getActivity(), AddDocumentActivity.class);
                    intent.putExtra("namaProses_6", getSpinnerProses);
                    intent.putExtra("namaStatus_6", getSpinnerStatus);
                    intent.putExtra("tanggal_6", etTanggal);
                    intent.putExtra("keterangan_6", etKet);
                }
            }
            if(getSpinnerStatus.equals("Sudah")){
                if(getSpinnerStatus.equals("Belum")){
                    Intent intent = new Intent(getActivity(), AddDocumentActivity.class);
                    intent.putExtra("namaProses_6", getSpinnerProses);
                    intent.putExtra("namaStatus_6", getSpinnerStatus);
                    intent.putExtra("tanggal_6", etTanggal);
                    intent.putExtra("keterangan_6", etKet);
                }
            }
        }
    }
}
