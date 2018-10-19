package com.example.diu.qrscanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.zxing.Result;

import java.util.Scanner;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    private ZXingScannerView mScannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }


    @Override
    public void handleResult(Result result) {
        if(result==null){
            Intent intent = new Intent(ScannerActivity.this, MainActivity.class);
            intent.putExtra("qr_result","Fuck");
            startActivity(intent);
            onBackPressed();
        }
        else{
            Intent intent = new Intent(ScannerActivity.this, MainActivity.class);
            intent.putExtra("qr_result",result.getText());
            startActivity(intent);
            onBackPressed();
        }


    }
}
