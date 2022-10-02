package com.example.mapandscanner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRScanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    ZXingScannerView zXingScannerView;
    Button buttonToPop;
    protected int standID = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        standID = intent.getIntExtra("Stand ID", 0);
        setContentView(R.layout.activity_qr_scanner);
        zXingScannerView = findViewById(R.id.ZXingScannerView_QRCode);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
        } else {
            openQRCamera();
        }
        // Add button to finish activity and go back - 11 August
        buttonToPop = findViewById(R.id.back_to_map_button);
        buttonToPop.setOnClickListener(v -> finish());
    }

    public void openQRCamera() {
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults[0] == 0) {
            openQRCamera();
        } else {
            Toast.makeText(this, "Permission to camera is required...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        zXingScannerView.stopCamera();
        super.onStop();
    }

    @Override
    public void handleResult(Result rawResult) {
        Intent intent = new Intent(QRScanner.this, ScannerResultActivity.class);
        System.out.println(rawResult.getText());
        intent.putExtra("Scanner Result", rawResult.getText());
        startActivity(intent);
    }
}
