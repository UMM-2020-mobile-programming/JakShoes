package com.example.tugbes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.tugbes.models.Users;
import com.example.tugbes.prevalent.prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private Button bergabung, login;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bergabung = (Button)findViewById(R.id.join_btn);
        login = (Button)findViewById(R.id.login_btn);
        loading = new ProgressDialog(this);


        Paper.init(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        bergabung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


        String NohpUser = Paper.book().read(prevalent.UserPhoneKey);
        String Password =  Paper.book().read(prevalent.UserPasswordKey);

        if (NohpUser != "" && Password != ""){
            if (!TextUtils.isEmpty(NohpUser) && !TextUtils.isEmpty(Password)){
                AllowAcces(NohpUser, Password);

                loading.setTitle("Loading...");
                loading.setMessage("Anda sudah terhubung...");
                loading.setCanceledOnTouchOutside(false);
                loading.show();
            }
        }
    }

    private void AllowAcces(final String nohp, final String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Users").child(nohp).exists()){
                    Users userData = dataSnapshot.child("Users").child(nohp).getValue(Users.class);

                    if (userData.getNohp()!= null && userData.getNohp().equals(nohp)){
                        if (userData.getPassword()!= null && userData.getPassword().equals(password)){
                            Toast.makeText(MainActivity.this, "Login berhasil...", Toast.LENGTH_SHORT).show();
                            loading.dismiss();

                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            prevalent.currentOnlineUser = userData;
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Password Salah...", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "Nomor ini tidak terdaftar...", Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
