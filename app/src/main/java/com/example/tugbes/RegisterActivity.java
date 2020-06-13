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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.DatabaseMetaData;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private Button buatAkun;
    private EditText inputNama, inputNohp, inputPassword;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        buatAkun = (Button)findViewById(R.id.register_register_btn);
        inputNama = (EditText)findViewById(R.id.register_input_nama);
        inputNohp = (EditText)findViewById(R.id.register_input_nohp);
        inputPassword = (EditText)findViewById(R.id.register_input_password);
        loading = new ProgressDialog(this);

        buatAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuatAkun();
            }
        });

    }

    private void BuatAkun() {
        String nama = inputNama.getText().toString();
        String nohp = inputNohp.getText().toString();
        String password = inputPassword.getText().toString();

        if (TextUtils.isEmpty(nama)){
            Toast.makeText(this, "Tulis Nama kamu....", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(nohp)){
            Toast.makeText(this, "Tulis Nomor Telepon kamu....", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Tulis Password kamu....", Toast.LENGTH_SHORT).show();
        }
        else{
            loading.setTitle("Membuat Akun");
            loading.setMessage("Silahkan tunggu, mengecek ketersediaan akun");
            loading.setCanceledOnTouchOutside(false);
            loading.show();
            validasiNomorTelepon(nama ,nohp, password);
        }
    }

    private void validasiNomorTelepon(final String nama, final String nohp, final String password) {
    final DatabaseReference RootRef;
    RootRef = FirebaseDatabase.getInstance().getReference();

    RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            //jika nohp tidak ada
            if ((!dataSnapshot.child("User").child(nohp).exists())){
                HashMap<String, Object> userdataMap = new HashMap<>();
                userdataMap.put("nohp",nohp);
                userdataMap.put("password",password);
                userdataMap.put("nama",nama);

                RootRef.child("Users").child(nohp).updateChildren(userdataMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(RegisterActivity.this, "Akun berhasil dibuat...", Toast.LENGTH_SHORT).show();
                                    loading.dismiss();

                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(RegisterActivity.this,"Register gagal...", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
            else{
                Toast.makeText(RegisterActivity.this, "Nomor ini sudah terdaftar...", Toast.LENGTH_SHORT).show();
                loading.dismiss();
                Toast.makeText(RegisterActivity.this, "Gunakan nomor yang berbeda...", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });
    }
}
