package com.example.pointstracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class StudentsRegister extends AppCompatActivity{

    private EditText sname,semail,spassword,srepassword,sstudentid,sgaurdiancnic,spointnum;
    private Button RegBtn;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        spointnum = (EditText)findViewById(R.id.pointno);
        sname = (EditText)findViewById(R.id.name);
        spassword = (EditText)findViewById(R.id.password);
        srepassword = (EditText)findViewById(R.id.Repassword);
        sstudentid = (EditText)findViewById(R.id.id);
        sgaurdiancnic = (EditText)findViewById(R.id.gaurdiancnic);
        semail = (EditText)findViewById(R.id.email);
        RegBtn = (Button)findViewById(R.id.RegBtn);
        RegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(view.getId()){
                    case R.id.RegBtn:
                        register();
                    break;
                }

            }
        });
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
    }



    private void register(){

        progressDialog.setMessage("Registering ...");
        progressDialog.show();
        final String name = sname.getText().toString().trim();
        final String email = semail.getText().toString().trim();
        final String password = spassword.getText().toString().trim();
        final String Repassword = srepassword.getText().toString().trim();
        final String id = sstudentid.getText().toString().trim();
        final String gcnic =  sgaurdiancnic.getText().toString().trim();
        final String pointno = spointnum.getText().toString().trim();

        try {
            int num = Integer.parseInt(gcnic);
            Log.i("",num+"is a number");
        }catch (NumberFormatException e){
            Log.i("",gcnic+"is not a number");
        }

        if (pointno.isEmpty()) {
            spointnum.setError("Point number empty");
            spointnum.requestFocus();
            progressDialog.dismiss();
            return;
        }

        if(pointno.length() != 2)
        {
            spointnum.setError("Invalid Point number");
            spointnum.requestFocus();
            progressDialog.dismiss();
            return;
        }

        if (name.isEmpty()){
            sname.setError("Name Field is empty");
            sname.requestFocus();
            progressDialog.dismiss();
            return;
       }

        if (name.length() > 20){
            sname.setError("Name too Long");
            sname.requestFocus();
            progressDialog.dismiss();
            return;
        }

        char[] chars = email.toCharArray();
        System.out.println(chars.length);

for(int i = 0; i < chars.length; i++)
        {
            if(chars[i] == '@'){
                if (chars[i+1] == 'n' && chars[i+2] == 'u' && chars[i+3] == '.' && chars[i+4] == 'e' && chars[i+5] == 'd' && chars[i+6] == 'u' && chars[i+7] == '.' && chars[i+8] == 'p' && chars[i+9] == 'k'){
                }else{
                    semail.setError("Invalid Domain");
                    semail.requestFocus();
                    progressDialog.dismiss();
                    return;
                }
            }
        }

        if (email.isEmpty()){
            semail.setError("Email Field is empty");
            semail.requestFocus();
            progressDialog.dismiss();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            semail.setError("Please enter a valid email");
            semail.requestFocus();
            progressDialog.dismiss();
            return;
        }

        if (password.isEmpty()){
            spassword.setError("password Field is empty");
            spassword.requestFocus();
            progressDialog.dismiss();
            return;
        }

        if (password.length() != 8){
            spassword.setError("Invalid password entered");
            spassword.requestFocus();
            progressDialog.dismiss();
            return;
        }

        if(password.equals(Repassword))
        {
        }else{
            srepassword.setError("Password does not match");
            srepassword.requestFocus();
            progressDialog.dismiss();
            return;
        }


        if (id.isEmpty()){
            sstudentid.setError("ID Field is empty");
            sstudentid.requestFocus();
            progressDialog.dismiss();
            return;
        }

        if (id.length() != 8){
            sstudentid.setError("Invalid student ID entered");
            sstudentid.requestFocus();
            progressDialog.dismiss();
            return;
        }

        if (gcnic.isEmpty()){
            sgaurdiancnic.setError("Email Field is empty");
            sgaurdiancnic.requestFocus();
            progressDialog.dismiss();
            return;
        }

        if (gcnic.length() != 13){
            sgaurdiancnic.setError("Invalid Parent CNIC entered");
            sgaurdiancnic.requestFocus();
            progressDialog.dismiss();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    progressDialog.dismiss();
                    //store data in firebase
                    Students students=new Students(name,id,gcnic,email,pointno);
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    FirebaseDatabase.getInstance().getReference("Student Register")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(students).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(getApplicationContext(), "Registeration successfull" , Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(StudentsRegister.this,StudentsLogin.class));
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Registeration Unsuccessfull" , Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else
                {
                    Toast.makeText(StudentsRegister.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    }
