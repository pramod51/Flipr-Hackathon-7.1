package com.example.payo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    private Button signup;
    public EditText enroll,cnfpass,fnaame,lname,phone,address;
    public   EditText email,pass;
    private TextView signin;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private TextInputLayout fnameinputLayout,emailinputLayout,passinputLayout,cnfpassinputLayout,phoneinputLayout;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    //"(?=.*[a-zA-Z])" +      //any letter
                    //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,}" +               //at least 6 characters
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //getSupportActionBar().setTitle("Sign Up");
        init();
        mAuth=FirebaseAuth.getInstance();
        updateUI();
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sin=new Intent(SignUp.this,LogIn.class);
                sin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(sin);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignupFun(v);
            }
        });

    }


    public void SignupFun(View v){
        final String nemail=email.getText().toString();
        String npass=pass.getText().toString();
        String ncnfpass=cnfpass.getText().toString();

        if(validateEmail(nemail)&&validatePassword(npass,ncnfpass)) {
            mAuth.createUserWithEmailAndPassword(nemail,npass).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        updateUI();
                        HashMap<String, String> map=new HashMap<>();
                        map.put("Name",fnaame.getText().toString()+lname.getText().toString());
                        map.put("Email",email.getText().toString());
                        map.put("UserId",mAuth.getUid());
                        map.put("Address",address.getText().toString());
                        map.put("Phone","+91"+phone.getText().toString());
                        DatabaseReference mref=FirebaseDatabase.getInstance().getReference();
                        mref.child(mAuth.getCurrentUser().getUid()).setValue(map);
                        startActivity(new Intent(SignUp.this,MainActivity.class));

                    }
                    else if(task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(SignUp.this,"Email Already Used",Toast.LENGTH_SHORT).show();

                    }

                }
            });
        }
    }


    private void init() {
        fnaame=findViewById(R.id.first_name);
        phone=findViewById(R.id.phone);
        address=findViewById(R.id.address);
        lname=findViewById(R.id.last_name);
        signup=findViewById(R.id.signup_button);
        email=findViewById(R.id.email);
        pass=findViewById(R.id.pass);
        cnfpass=findViewById(R.id.cnf_pass);
        signin=findViewById(R.id.login);
        fnameinputLayout=findViewById(R.id.first_name_error);
        emailinputLayout=findViewById(R.id.email_error);
        passinputLayout=findViewById(R.id.pass_error);
        phoneinputLayout=findViewById(R.id.phone_error);
    }

    private void updateUI() {
        FirebaseUser user =mAuth.getCurrentUser();
        if(user==null) {
            //Toast.makeText(this, "Not logged in", Toast.LENGTH_SHORT);

        }
        else if(user!=null){
            startActivity(new Intent(SignUp.this,MainActivity.class));
        }
    }

    private boolean validateEmail(String email) {

        if (fnaame.getText().toString().isEmpty()) {
            fnameinputLayout.setError("Field can't be empty");
            return false;
        }
        else if (email.isEmpty()) {
            emailinputLayout.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailinputLayout.setError("Please enter a valid email address");
            return false;
        }
        else if (phone.getText().toString().length()<10&&phone.getText().toString().length()>10) {
            phoneinputLayout.setError("Enter Valid Phone No");
            return false;
        }
        else {
            emailinputLayout.setError(null);
            fnameinputLayout.setError(null);
            phoneinputLayout.setError(null);
            return true;
        }
    }

    private boolean validatePassword(String pass, String cnfpass) {
        if (pass.isEmpty()) {
            passinputLayout.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(pass).matches()) {
            passinputLayout.setError("Minimum 6 character required");
            return false;
        } else if (!cnfpass.equals(pass)) {
            cnfpassinputLayout.setError("Password not matched");
            return false;
        } else {
            //passinputLayout.setError(null);
            //cnfpassinputLayout.setError(null);
            return true;
        }
    }



}