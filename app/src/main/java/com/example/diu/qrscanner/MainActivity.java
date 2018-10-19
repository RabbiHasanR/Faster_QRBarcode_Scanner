package com.example.diu.qrscanner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import static junit.framework.Assert.assertNotNull;


public class MainActivity extends AppCompatActivity{

    private static final int REQUEST_CAMERA = 1;
    private static int camId = Camera.CameraInfo.CAMERA_FACING_BACK;
    private TextView scan_result;
    private Button scan_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
        Intent intent=getIntent();
        String qr_result=intent.getStringExtra("qr_result");
        if(qr_result==null){
            scan_result.setText("Result");
        }
        else{
            Log.i("Result",qr_result);
            scan_result.setText(qr_result);
        }

        scan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();

            }
        });

    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Log.d("Camera permission" , "Camera permission granted");
            Intent intent=new Intent(MainActivity.this,ScannerActivity.class);
            startActivity(intent);
        } else {
            requestCameraPermission();

        }
    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            Snackbar.make(findViewById(android.R.id.content), "Camera permission request",
                    Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                @Override public void onClick(View view) {
                    ActivityCompat.requestPermissions(MainActivity.this,  new String[] {Manifest.permission.CAMERA}, REQUEST_CAMERA);
                }
            }).show();
        } else{
            Snackbar.make(findViewById(android.R.id.content), "Camera permission request",
                    Snackbar.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.CAMERA},REQUEST_CAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode==REQUEST_CAMERA && grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Snackbar.make(findViewById(android.R.id.content), "Thanks for permission", Snackbar.LENGTH_SHORT).show();
            Intent intent=new Intent(MainActivity.this,ScannerActivity.class);
            startActivity(intent);

        } else {
            Snackbar.make(findViewById(android.R.id.content),"Not permission", Snackbar.LENGTH_SHORT)
                    .show();
        }

    }

    private void initializeViews() {
        scan_result = (TextView) findViewById(R.id.scan_result);
        scan_button=(Button)findViewById(R.id.scan_btn);
        assertNotNull(scan_result);
        assertNotNull(scan_button);
    }

}
