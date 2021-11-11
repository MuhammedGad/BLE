package com.tr.blebutton.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tr.blebutton.AdminRegister;
import com.tr.blebutton.R;

public class AdminLogin extends AppCompatActivity {
    EditText email;
    EditText pass;
    FirebaseAuth mAuth;
    public String mail;
    public String password;
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
    private String mDeviceAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        TextView Kayt = (TextView)findViewById(R.id.kaytol);
        Button adminLogin = (Button)findViewById(R.id.adminlogin);
        Button back = (Button)findViewById(R.id.adminback);
        email = (EditText)findViewById(R.id.Email);
        pass = (EditText)findViewById(R.id.Password);
        final Intent intent = getIntent();
        mDeviceAddress = intent.getStringExtra(EXTRAS_DEVICE_ADDRESS);

        mAuth = FirebaseAuth.getInstance();
        adminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mail = email.getText().toString().trim();
                 password = pass.getText().toString().trim();
                if(mail.isEmpty()){
                    email.setError("Email boş bırakılmaz");
                    email.requestFocus();
                } else if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
                    email.setError("Lütfen Doğru Email Yazınız");
                    email.requestFocus();
                }
                if(password.isEmpty()){
                    pass.setError("Şifre boş bırakılmaz");
                    pass.requestFocus();
                }
                if(!mail.isEmpty()&&!password.isEmpty()&&Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                        // TODO : Open loging page && Cloud control
                        mAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    if(!user.isEmailVerified()){
                                        user.sendEmailVerification();
                                        Toast.makeText(AdminLogin.this, "Mailinizi Onaylayaniz", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Intent adminpage = new Intent(AdminLogin.this, AdminPage.class);
                                        adminpage.putExtra(AdminPage.EXTRAS_DEVICE_ADDRESS, mDeviceAddress);
                                        startActivity(adminpage);
                                    }

                                }
                            }
                        });
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent anasayfa = new Intent(AdminLogin.this, com.tr.blebutton.anasayfa.class);

                startActivity(anasayfa);
            }
        });
        Kayt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent RegisterAdmin = new Intent(AdminLogin.this, AdminRegister.class);
                RegisterAdmin.putExtra(AdminRegister.EXTRAS_DEVICE_ADDRESS, mDeviceAddress);
                startActivity(RegisterAdmin);
                Kayt.setTextColor(Color.parseColor("#FF0000"));
            }
        });
    }
}