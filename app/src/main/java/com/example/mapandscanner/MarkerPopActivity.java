package com.example.mapandscanner;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.TextView;

public class MarkerPopActivity extends Activity {
    private TextView stationName;
    private TextView bikeStatus;
    private Button unlockButton;
    private int markerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Activity on...");
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_marker);
        init();
        setInfo(intent);

        unlockButton.setOnClickListener(v -> {
            Intent intent1 = new Intent(MarkerPopActivity.this, QRScanner.class);
            intent1.putExtra("Bike ID", markerID);
            startActivity(intent1);
        });
    }

    public void init() {
        markerID = 0;
        stationName = findViewById(R.id.marker_title);
        bikeStatus = findViewById(R.id.marker_status);
        unlockButton = findViewById(R.id.marker_button);
    }

    @SuppressLint("ResourceAsColor")
    public void setInfo(Intent intent) {
        markerID = intent.getIntExtra("Bike ID", 0);
        int markerStatus = intent.getIntExtra("Availability", 0);
        stationName.setText("Bike "+String.valueOf(markerID));
        String availability;
        int buttonColor;
        if (markerStatus > 0) {
            availability = "Available";
            // If unavailable, make the unlock button teal and clickable - 11 August
            buttonColor = R.color.teal_700;
            unlockButton.setClickable(true);
        } else {
            availability = "Unavailable";
            // If unavailable, make the unlock button grey and non-clickable - 11 August
            buttonColor = R.color.grey;
            unlockButton.setEnabled(false);
        }
        unlockButton.setBackgroundResource(buttonColor);
        bikeStatus.setText(availability);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }
}
