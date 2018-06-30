package com.example.doandroida.projekt_v2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class klasa1 extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;
    private Button signOut;
    //private EditText editText;
    private TextView tv;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        signOut = findViewById(R.id.signout);
        //editText = findViewById(R.id.editText);
        tv = findViewById(R.id.tv);
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    startActivity(new Intent(klasa1.this,
                            projekt_v2.class));
                    finish();
                }
                else{
                    //editText.setText("Welcome "+user.getEmail());
                    tv.setText("Welcome "+user.getEmail());
                }
            }
        };
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        Button notatnik=(Button)findViewById(R.id.button);

        notatnik.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Context context;
                context = getApplicationContext();
                Intent intent = new Intent(context, okno1.class);
                startActivity(intent);
            }
        });

        Button record=(Button)findViewById(R.id.rec);

        /*record.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Context context;
                context = getApplicationContext();
                Intent intent = new Intent(context, okno2.class);
                startActivity(intent);
            }
        });*/
    }
    //sign out method
    public void signOut() {
        auth.signOut();
    }
    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}