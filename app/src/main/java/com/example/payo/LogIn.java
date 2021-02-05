package com.example.payo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LogIn extends AppCompatActivity {

    private EditText et_pass,et_email;
    private Button button;
    private TextView sign_up;
    private FirebaseAuth firebaseAuth;
    private TextInputLayout inputLayout_email,inputLayout_pass;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        init();

        firebaseAuth=FirebaseAuth.getInstance();
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sinup=new Intent(LogIn.this,SignUp.class);
                sinup.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(sinup);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(v);
            }

        });
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        finish();
        super.onBackPressed();
    }

    private void init() {
        et_email=(EditText)findViewById(R.id.et_mail);
        et_pass=(EditText)findViewById(R.id.et_pass);
        sign_up=findViewById(R.id.sign_up);
        button=(Button)findViewById(R.id.login);
        inputLayout_email=findViewById(R.id.email_error);
        inputLayout_pass=findViewById(R.id.pass_error);
    }


    public void login(final View v){
        final String email=et_email.getText().toString();
        String pass=et_pass.getText().toString();

        if(TextUtils.isEmpty(email)||TextUtils.isEmpty(pass)){
            inputLayout_email.setError("please enter email");
            inputLayout_pass.setError("please enter password");
            return;
        }
        {
            firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Intent intent = new Intent(LogIn.this,MainActivity.class);
                        startActivity(intent);
                        updateUI();
                    }
                    else if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                        //invalid pass
                        Toast.makeText(LogIn.this,"Wrong password entered",Toast.LENGTH_LONG).show();
                    }
                    else if(task.getException() instanceof FirebaseAuthInvalidUserException){
                        //Email not in used
                        Toast.makeText(LogIn.this,"Wrong Email entered",Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
    }
    private void updateUI() {
        FirebaseUser user =firebaseAuth.getCurrentUser();
        if(user==null) {
            Toast.makeText(this, "Not logged in", Toast.LENGTH_SHORT);
            //return;
        }
        else{

        }
    }

}
