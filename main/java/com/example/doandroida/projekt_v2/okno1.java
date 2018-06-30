package com.example.doandroida.projekt_v2;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class okno1 extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;
    private MediaPlayer mediaPlayer;
    private String mFileName;
    private static final int RECORD_REQUEST_CODE = 101;
    public String TAG;
    int numer=0;
    int numer2=0;
    private TextView tv;
    private EditText ET;
    private DatabaseReference mDatabase;
    private ArrayList<String> notatki;
    private Notatka wszystkienotatki;
    private FirebaseUser user;
    public String email;
    public Uzytkownik wszystko;
    public Uzytkownik wszystko2;
    public ArrayList<Notatka> uzytkownicy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.okno1);


        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //editText = findViewById(R.id.editText);
        tv = findViewById(R.id.tv);
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    startActivity(new Intent(okno1.this,
                            projekt_v2.class));
                    finish();
                }
                else{
                    //editText.setText("Welcome "+user.getEmail());
                    email=user.getEmail();
                    tv.setText("Welcome "+user.getEmail());
                }
            }
        };


        int permissionCheck = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionCheck2 = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED || permissionCheck2 !=
                PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission to record denied");
            makeRequest();
        }

        tv = (TextView)findViewById(R.id.textView1);
        tv.setText("");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        notatki=new ArrayList<String>();
        uzytkownicy=new ArrayList<Notatka>();
        //final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Button button = (Button) findViewById(R.id.back);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Context context;
                context = getApplicationContext();
                Intent intent = new Intent(context, klasa1.class);
                startActivity(intent); }});
    }

    public void Clear (View view) throws FileNotFoundException {
        //deleteFile("myfilename.txt");

       /* File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard.getAbsolutePath() + "/MojePliki/myfilename.txt");
        file.delete();
        //Ustawiamy nasz wczytany tekst w TextView
        tv.setText("");
        ET = (EditText)findViewById(R.id.editText1);
        ET.setText("");*/
    }

    public void Czysc(View view){
        ET = (EditText)findViewById(R.id.editText1);
        ET.setText("");
    }

    public void SaveSD(View view){
// Potrzebujemy ścieżki do karty SD:
    /*    File sdcard = Environment.getExternalStorageDirectory();
// Dodajemy do ścieżki własny folder:
        File dir = new File(sdcard.getAbsolutePath() + "/MojePliki/");
// jeżeli go nie ma to tworzymy:
        dir.mkdir();
// Zapiszmy do pliku nasz tekst:
        File file = new File(dir, "myfilename.txt");
        ET = (EditText)findViewById(R.id.editText1);
        String text = ET.getText().toString();
        try {
            FileOutputStream os = new FileOutputStream(file);
            os.write(text.getBytes());
            os.close();
        }catch (IOException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }*/
        ET = (EditText)findViewById(R.id.editText1);
        String notatka=ET.getText().toString();

        notatki.add(notatka);
        wszystkienotatki=new Notatka(notatki, email);

        uzytkownicy.add(wszystkienotatki);
        wszystko=new Uzytkownik(uzytkownicy);

        dodaj(wszystko);
    }

    public void ViewSD(View view){
        /*File sdcard = Environment.getExternalStorageDirectory();
        File dir = new File(sdcard.getAbsolutePath() + "/MojePliki/");
        File file = new File(dir, "myfilename.txt");
        int length = (int) file.length();
        byte[] bytes = new byte[length];
        FileInputStream in;
        try {
            in = new FileInputStream(file);
            in.read(bytes);
            in.close();
        } catch (FileNotFoundException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        String contents = new String(bytes);
        //tv = (TextView)findViewById(R.id.textView1);
        //tv.setText(contents);
        ET = (EditText)findViewById(R.id.editText1);
        ET.setText(contents);*/
    }

    protected void makeRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.RECORD_AUDIO,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
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

    public void dodaj(Uzytkownik all){
        mDatabase.child("Baza").setValue(all);
        //mDatabase.child(user.getEmail()).setValue(wszystkienotatki);
    }

    /*public void pobierz(){
        wszystko2=mDatabase.child("Baza").getValue();
    }*/
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



