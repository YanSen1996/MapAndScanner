package com.example.mapandscanner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ScannerResultActivity extends AppCompatActivity {
    protected String scanningResult;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        scanningResult = intent.getStringExtra("Scanner Result");
        setContentView(R.layout.activity_after_scanning);
        tv = findViewById(R.id.textView_Showing);
        tv.setText(scanningResult);
        System.out.println(scanningResult);
    }
}
