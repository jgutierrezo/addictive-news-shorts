package com.example.addictive_news_shorts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText uname,email,pwd,cpwd;
    Button btnregister;
    TextView gologin;
    FirebaseAuth fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        uname= findViewById(R.id.uname);
        email=findViewById(R.id.email);
        pwd=findViewById(R.id.pwd);
        cpwd=findViewById(R.id.confirmpwd);
        btnregister=findViewById(R.id.registerbtn);
        gologin=findViewById(R.id.login);

        fauth=FirebaseAuth.getInstance();

        gologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        });
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username=uname.getText().toString();
                String useremail=email.getText().toString();
                String userpwd=pwd.getText().toString();
                String usercpwd=cpwd.getText().toString();

                if(username.isEmpty()){
                    uname.setError("Username Required");
                    return;
                }
                if(useremail.isEmpty()){
                    email.setError("Email Required");
                    return;
                }
                if(userpwd.isEmpty()){
                    pwd.setError("Password Required");
                    return;
                }
                if(usercpwd.isEmpty()){
                    cpwd.setError("Password Required");
                    return;
                }
                if(!userpwd.equals(usercpwd)){
                    cpwd.setError("Password does not match");
                    return;
                }

                //Toast.makeText(RegisterActivity.this,"Data Valid",Toast.LENGTH_SHORT).show();

                fauth.createUserWithEmailAndPassword(useremail,userpwd).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
