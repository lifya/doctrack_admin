package com.pln.www;

import android.app.DatePickerDialog;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.pln.www.alert.FormDocumentDialog;

import java.util.Calendar;

public class AddDocumentActivity extends AppCompatActivity {

    ImageView imageView;
    EditText Ed1;
    EditText Ed2;
    Calendar mCurrentDate;
    int day, month, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_document);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tbDoc);
        setSupportActionBar(toolbar);

        ImageView imageView = (ImageView) findViewById(R.id.imagev1);

        setupSetForm();

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

    }

    private void setupSetForm () {
        Button btn = (Button) findViewById(R.id.buttonProses);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                FormDocumentDialog dialog = new FormDocumentDialog();
                dialog.show(manager, "MessageDialog");

                Log.i("TAG", "Just so the text");
            }
        });
    }

}
