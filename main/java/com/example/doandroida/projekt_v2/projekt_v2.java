package com.example.doandroida.projekt_v2;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class projekt_v2 extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText inputEmail, inputPassword;
    private Button btnSignUp, btnSignIn;
    public String TAG;
    private static final int RECORD_REQUEST_CODE = 101;
    private static final String DEBUG_TAG = "NetworkStatusExample";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projekt_v2);
        Firebase.setAndroidContext(this);

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.INTERNET);
        int permissionCheck2 = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_NETWORK_STATE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED || permissionCheck2 != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission to record denied");
            makeRequest();
        }

        btnSignUp = (Button) findViewById(R.id.signup);
        btnSignIn = (Button) findViewById(R.id.signin);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        auth = FirebaseAuth.getInstance();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(projekt_v2.this, new
                                OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull
                                                                   Task<AuthResult> task) {
                                        Toast.makeText(projekt_v2.this,
                                                "createUserWithEmail:onComplete:" + task.isSuccessful(),
                                                Toast.LENGTH_SHORT).show();
                                        if (!task.isSuccessful()) {
                                            Toast.makeText(projekt_v2.this,
                                                    "Authentication failed." + task.getException(),
                                                    Toast.LENGTH_SHORT).show();
                                        } else {
                                            startActivity(new Intent(projekt_v2.this,
                                                    projekt_v2.class));
                                            finish();
                                        }
                                    }
                                });
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(projekt_v2.this, new
                                OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (!task.isSuccessful()) {
                                            Toast.makeText(projekt_v2.this,
                                                    "Authentication failed.", Toast.LENGTH_LONG).show();
                                        }
                                        else {
                                            Intent intent = new Intent(projekt_v2.this,
                                                    klasa1.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                });
            }
        });

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isWifiConn = networkInfo.isConnected();
        networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isMobileConn = networkInfo.isConnected();
        Log.d(DEBUG_TAG, "Wifi connected: " + isWifiConn);
        Log.d(DEBUG_TAG, "Mobile connected: " + isMobileConn);
    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    protected void makeRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_NETWORK_STATE},
                RECORD_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RECORD_REQUEST_CODE: {
                if (grantResults.length == 0
                        || grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED || grantResults[1] !=
                        PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "Permission has been denied by user");
                }

            }
        }
    }
}