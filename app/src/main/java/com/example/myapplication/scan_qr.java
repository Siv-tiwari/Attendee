package com.example.myapplication;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.VIBRATE;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class scan_qr extends AppCompatActivity {
    private CodeScannerView scanner;
    private TextView text;
    private CodeScanner codeScanner;
    private attendence_db_handler handler;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);
        scanner = findViewById(R.id.scanner);
        text = findViewById(R.id.Scan_tv);
        handler = new attendence_db_handler(scan_qr.this);

        Intent intent = getIntent();
        String sap_user = intent.getStringExtra(MainActivity.KEY_SAP);

        if(check_permission()){
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }
        else{
            request_permission();
        }

        codeScanner = new CodeScanner(this, scanner);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void run() {
                        if(result.getText().replace("AXYFIEAIGILAL ARYGYGLBDLIG AGddtgiedfer bidgidfe ","").equals("1")){
                            // setting data to attendence database
                            attendence_data data_present = new attendence_data();
                            data_present.setSap(sap_user.replaceAll("\\p{P}",""));
                            data_present.setDate(dtf.format(now));
                            data_present.setPresent(Integer.parseInt(result.getText().replace("AXYFIEAIGILAL ARYGYGLBDLIG AGddtgiedfer bidgidfe ","")));
                            data_present.setAbsent(0);
                            data_present.setLeave(0);
                            handler.add_user(data_present);
                            text.setText("Present Marked");
                        }
                        else{
                            text.setText(result.getText());
                        }
                    }
                });
            }
        });
        scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codeScanner.startPreview();
            }
        });
    }

    private boolean check_permission() {
        int camera_permission = ContextCompat.checkSelfPermission(getApplicationContext(),CAMERA);
        int vibrate_permission = ContextCompat.checkSelfPermission(getApplicationContext(),VIBRATE);
        return camera_permission == PackageManager.PERMISSION_GRANTED && vibrate_permission == PackageManager.PERMISSION_GRANTED;
    }
    private void request_permission(){
        int PERMISSION_CODE = 200;
        ActivityCompat.requestPermissions(this,new String[]{CAMERA,VIBRATE},PERMISSION_CODE);
    }

    @Override
    protected void onResume(){
        super.onResume();
        codeScanner.startPreview();
    }

    @Override
    protected void onPause(){
        codeScanner.releaseResources();
        super.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length>0){
            boolean camera_accepted = grantResults[0]== PackageManager.PERMISSION_GRANTED;
            boolean vibration_accepted = grantResults[1]== PackageManager.PERMISSION_GRANTED;
            if(camera_accepted && vibration_accepted){
                Toast.makeText(scan_qr.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(scan_qr.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}