package com.example.fotogallery.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fotogallery.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import com.example.fotogallery.model.KullaniciBilgi;


public class RegisterActivity extends AppCompatActivity {

    EditText userName;
    EditText emailRegister;
    EditText passwordRegister;
    Button buttonRegister;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    KullaniciBilgi kullaniciBilgi;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userName = findViewById(R.id.inputUsername);
        emailRegister = findViewById(R.id.emailRegister);
        passwordRegister = findViewById(R.id.passwordRegister);
        buttonRegister = findViewById(R.id.buttonRegister);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        kullaniciBilgi = KullaniciBilgi.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();


    }

    public void kayitOl(View view) {

        if ("".equals(userName.getText().toString().trim())) {
            userName.setError("Kullanıcı adı giriniz! ");
            return;
        }
        if ("".equals(emailRegister.getText().toString().trim())) {
            emailRegister.setError("Email adresinizi giriniz! ");
            return;
        }
        if ("".equals(passwordRegister.getText().toString().trim())) {
            passwordRegister.setError("Şifre giriniz! ");
            return;
        }

        kullaniciBilgi.setUserName(userName.getText().toString());
        kullaniciBilgi.setEmail(emailRegister.getText().toString());
        kullaniciBilgi.setPassword(passwordRegister.getText().toString());

        firebaseAuth.createUserWithEmailAndPassword(kullaniciBilgi.getEmail(), kullaniciBilgi.getPassword())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(RegisterActivity.this, "Hoşgeldiniz", Toast.LENGTH_LONG).show();

                        HashMap<String, Object> userinfo = new HashMap<>();
                        userinfo.put("Username", kullaniciBilgi.getUserName());
                        userinfo.put("Email", kullaniciBilgi.getEmail());
                        userinfo.put("Password", kullaniciBilgi.getPassword());

                        firebaseFirestore.collection("userInformation").document(kullaniciBilgi.getEmail()).set(userinfo)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(RegisterActivity.this, "Kullanıcı oluşturuldu", Toast.LENGTH_LONG);
                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(RegisterActivity.this, e.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                                    }
                                });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, e.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });
        
    }

}