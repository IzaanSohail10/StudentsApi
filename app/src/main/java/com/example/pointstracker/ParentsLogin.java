package com.example.pointstracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ParentsLogin extends AppCompatActivity {

    private EditText Code;
    private TextView Info;
    private Button Login;
    private int counter = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_parents_login);
        getSupportActionBar().hide();

        Code = (EditText) findViewById(R.id.ID);
        Info = (TextView) findViewById(R.id.Info);
        Login = (Button)findViewById(R.id.SignBtn);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(Code.getText().toString());
            }
        });

    }
    private void validate(String userName){
        if((userName.equals("parent"))){
            Intent intent = new Intent(ParentsLogin.this,ChooseLogin.class);
            startActivity(intent);
        }else{
            counter--;

            Info.setText("Number of attempts remaining: " + String.valueOf(counter));

            if(counter == 0){
                Login.setEnabled(false);
            }
        }

    }
}
