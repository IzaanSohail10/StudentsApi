package com.example.pointstracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class StudentsLogin extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private Button Register;
    private int counter = 3;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_students_login);
        getSupportActionBar().hide();

        Name = (EditText) findViewById(R.id.ID);
        Password = (EditText) findViewById(R.id.password);
        Info = (TextView) findViewById(R.id.Info);
        Login = (Button)findViewById(R.id.SignBtn);
        Register = (Button)findViewById(R.id.RegBtn);

        Info.setText("No. of attempts remaining: 3");

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null){
            finish();
            startActivity(new Intent(StudentsLogin.this,MapsActivity.class));
        }

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(Name.getText().toString(), Password.getText().toString());
            }
        });
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StudentsLogin.this,StudentsRegister.class);
                startActivity(intent);
            }
        });
        FirebaseApp.initializeApp(this);


        }

        private void validate(String userName, String userPassword){
            final String name = Name.getText().toString().trim();
            final String email = Password.getText().toString().trim();

            progressDialog.setMessage("Verifying ...");
            progressDialog.show();

            firebaseAuth.signInWithEmailAndPassword(name,email )
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(getApplicationContext(), "Login successfull" , Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(StudentsLogin.this,MapsActivity.class));
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Invalid Email or Password" , Toast.LENGTH_SHORT).show();
                                counter--;

                                Info.setText("Number of attempts remaining: " + String.valueOf(counter));

                                if(counter == 0){
                                    Login.setEnabled(false);
                                }
                            }

                            // ...
                        }
                    });
    }
}
