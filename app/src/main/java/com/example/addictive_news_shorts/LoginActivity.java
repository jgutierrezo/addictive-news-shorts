package com.example.addictive_news_shorts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.addictive_news_shorts.news.newsActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    EditText uemail,upwd;
    Button btnlogin;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uemail=findViewById(R.id.email);
        upwd=findViewById(R.id.pwd);
        btnlogin=findViewById(R.id.loginbtn);

        firebaseAuth=FirebaseAuth.getInstance();

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uemail.getText().toString().isEmpty()){
                    uemail.setError("Email Required");
                    return;
                }
                if(upwd.getText().toString().isEmpty()){
                    upwd.setError("Password Required");
                    return;
                }
                btnlogin.setText("Loading");
                btnlogin.setClickable(false);
                firebaseAuth.signInWithEmailAndPassword(uemail.getText().toString(),upwd.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Intent replyIntent = new Intent();
                        setResult(RESULT_OK, replyIntent);
                        btnlogin.setText("Login");
                        btnlogin.setClickable(true);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        btnlogin.setText("Login");
                        btnlogin.setClickable(true);
                    }
                });

            }
        });
    }

    public void goToRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    protected void onStart(){
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            //startActivity(new Intent(getApplicationContext(), newsActivity.class));
            //Toast.makeText(LoginActivity.this,"Logged in",Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
