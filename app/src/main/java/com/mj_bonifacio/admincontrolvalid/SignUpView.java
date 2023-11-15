package com.mj_bonifacio.admincontrolvalid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignUpView extends AppCompatActivity {
    // Initialize elements
    Button regBtn, backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_view);

        // Stuffs
        regBtn = (Button) findViewById(R.id.btnRegister);
        backBtn = (Button) findViewById(R.id.btnBack);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBack = new Intent(SignUpView.this, MainActivity.class);
                startActivity(goBack);
            }
        });
    }
}