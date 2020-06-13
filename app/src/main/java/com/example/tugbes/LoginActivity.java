package com.example.tugbes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tugbes.models.Users;
import com.example.tugbes.prevalent.prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    private EditText inputNohp, inputPassword;
    private Button btn_login;
    private ProgressDialog loading;
    private TextView AdminLink, NotAdminLink;

    private String parentDbName = "Users";
    private com.rey.material.widget.CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = (Button)findViewById(R.id.login_login_btn);
        inputNohp = (EditText)findViewById(R.id.login_input_nohp);
        inputPassword = (EditText)findViewById(R.id.login_input_password);
        AdminLink = (TextView)findViewById(R.id.admin_link);
        NotAdminLink = (TextView)findViewById(R.id.bukan_admin_link);

        loading = new ProgressDialog(this);

        checkBox = (CheckBox) findViewById(R.id.ingat_saya);
        Paper.init(this);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });
        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_login.setText("Login Admin");
                AdminLink.setVisibility(View.INVISIBLE);
                NotAdminLink.setVisibility(View.VISIBLE);
                parentDbName = "Admins";

            }
        });

        NotAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_login.setText("Login");
                AdminLink.setVisibility(View.VISIBLE);
                NotAdminLink.setVisibility(View.INVISIBLE);
                parentDbName = "Users";
            }
        });
    }

    private void LoginUser() {
        String nohp = inputNohp.getText().toString();
        String password = inputPassword.getText().toString();

        if (TextUtils.isEmpty(nohp)){
            Toast.makeText(this, "Tulis Nomor Telepon kamu....", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Tulis Password kamu....", Toast.LENGTH_SHORT).show();
        }
        else{
            loading.setTitle("Login Akun");
            loading.setMessage("Silahkan tunggu, mengecek ketersediaan akun");
            loading.setCanceledOnTouchOutside(false);
            loading.show();

            AllowAccesToAccount(nohp, password);
        }
    }

    private void AllowAccesToAccount(final String nohp, final String password) {

        if (checkBox.isChecked()){
            Paper.book().write(prevalent.UserPhoneKey, nohp);
            Paper.book().write(prevalent.UserPasswordKey,password);
        }

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(parentDbName).child(nohp).exists()){
                    Users userData = dataSnapshot.child(parentDbName).child(nohp).getValue(Users.class);

                    if (userData.getNohp().equals(nohp)){
                        if (userData.getPassword().equals(password)){
                            if (parentDbName.equals("Admins")){
                                Toast.makeText(LoginActivity.this, "Selamat datang Admin,Login berhasil...", Toast.LENGTH_SHORT).show();
                                loading.dismiss();

                                Intent intent = new Intent(LoginActivity.this, AdminCategoryActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                            else if(parentDbName.equals("Users")){
                                Toast.makeText(LoginActivity.this, "Login berhasil...", Toast.LENGTH_SHORT).show();
                                loading.dismiss();

                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                prevalent.currentOnlineUser = userData;
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Password Salah...", Toast.LENGTH_SHORT).show();
                            loading.dismiss();
                        }
                    }
                }
                else{
                    Toast.makeText(LoginActivity.this, "Nomor ini tidak terdaftar...", Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
