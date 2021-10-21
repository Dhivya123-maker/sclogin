package com.example.sclogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginActivity extends AppCompatActivity {
    EditText emailId, password;
    Button btnSignIn;
    TextView tvSignUp;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    Toast toast;
    GoogleSignInClient mGoogleSignInClient;
    private static int RC_SIGN_IN =100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);









        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId=findViewById(R.id.lemail);
        password=findViewById(R.id.lpwd);
        btnSignIn=findViewById(R.id.llogin);
        tvSignUp=findViewById(R.id.lregister);

        mAuthStateListener =  new FirebaseAuth.AuthStateListener() {




            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mfirebaseUser =mFirebaseAuth.getCurrentUser();
                if(mfirebaseUser != null ){
                    if (null != toast) {
                        toast.cancel();
                    }

               Toast.makeText(loginActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();


                    Intent i = new Intent(loginActivity.this, com.example.sclogin.HomeActivity.class);
                    startActivity(i);



                }
                else{
                    if (null != toast) {
                        toast.cancel();
                    }
                    Toast.makeText(loginActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
                    if (null != toast) {
                        toast.cancel();
                    }


                }

            }
        };
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailId.getText().toString();
                String pwd = password.getText().toString();
                if(email.isEmpty()){
                    emailId.setError("Please enter mail id");
                    emailId.requestFocus();
                }
                else if(pwd.isEmpty()){
                    password.setError("Please enter the password");
                    password.requestFocus();
                }
                else if(email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(loginActivity.this, "Fields are empty!", Toast.LENGTH_SHORT).show();


                }
                else if (!(email.isEmpty() && pwd.isEmpty())){
                    mFirebaseAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(loginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){

                                Toast.makeText(loginActivity.this, "Login Error, Please TryAgain After sometime!", Toast.LENGTH_SHORT).show();

                            }
                            else{
                                Intent intToHome =  new Intent(loginActivity.this, com.example.sclogin.HomeActivity.class);
                                startActivity(intToHome);
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(loginActivity.this, "Error Occurred!", Toast.LENGTH_SHORT).show();

                }
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intSignUp = new Intent(loginActivity.this,MainActivity.class);
                startActivity(intSignUp);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
