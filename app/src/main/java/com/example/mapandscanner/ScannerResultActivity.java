package com.example.mapandscanner;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class ScannerResultActivity extends AppCompatActivity {
    protected String scanningResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        scanningResult = intent.getStringExtra("Scanner Result");
        System.out.println(scanningResult);
    }
}
