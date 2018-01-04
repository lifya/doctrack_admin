package com.pln.www;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.pln.www.fragment.FormFragment;

public class AddDocumentActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_document);

        ImageView imageView = (ImageView) findViewById(R.id.imagev1);

        setupSetForm();
    }

    private void setupSetForm () {
        Button btn = (Button) findViewById(R.id.buttonProses);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                FormFragment dialog = new FormFragment();
                dialog.show(manager, "MessageDialog");

                Log.i("TAG", "Just so the text");
            }
        });
    }

}
