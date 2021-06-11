package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class SecurityQuestions extends AppCompatActivity {

    private Button HomePage;
    private Button confirmButton;
    private Button changePasswordButton;
    private Button confirmEmailButton;

    private EditText passwordInput;
    private EditText confirmPasswordInput;
    private EditText securityAnswer;
    private EditText emailInput;

    private TextView securityQuestion;
    private TextView incorrectAnswer;
    private TextView passwordsDontMatch;
    private TextView emailDoesNotExist;

    private String passwordInputVar;
    private String confirmPasswordInputVar;
    private String answerInputVar;
    private String emailInputVar;

    private String currentAnswer;
    private String currentQuestion;


    Connection connect;
    String ConnectionResult = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_security_questions);
        HomePage=(Button) findViewById(R.id.backButtonSecurity);
        HomePage.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                returnLogin();
            }
        });
        confirmButton=(Button) findViewById(R.id.confirmButtonSecurityPage);
        confirmButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                checkAnswer();

            }
        });
        changePasswordButton=(Button) findViewById(R.id.confirmButtonSecurityPage);
        changePasswordButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                checkPassword();
            }
        });
        confirmEmailButton=(Button) findViewById(R.id.confirmButtonSecurityPage);
        confirmEmailButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                confirmEmail();
            }
        });



        passwordInput=(EditText) findViewById(R.id.inputSecurity);
        confirmPasswordInput=(EditText) findViewById(R.id.inputSecurity) ;
        securityAnswer=(EditText) findViewById(R.id.inputSecurity);
        emailInput=(EditText) findViewById(R.id.inputSecurity);

        passwordsDontMatch=(TextView) findViewById(R.id.errorMessageSecurity);
        incorrectAnswer=(TextView) findViewById(R.id.errorMessageSecurity);
        securityQuestion=(TextView) findViewById(R.id.securityQuestionTextView);
        emailDoesNotExist=(TextView) findViewById(R.id.errorMessageSecurity);

        //Stage 1
        securityQuestion.setVisibility(View.INVISIBLE);
        emailDoesNotExist.setText("Email Does Not Exist");
        emailDoesNotExist.setVisibility(View.INVISIBLE);


    }
    public void getData(){

    }
    public void checkPassword(){

    }
    public void confirmEmail(){
        emailInputVar=emailInput.getText().toString();
    }
    public void checkAnswer(){
        answerInputVar=securityAnswer.getText().toString();

    }
    public void returnLogin(){
        errorStateHelper.reset();
        Intent intent= new Intent(this, com.example.myapplication.Login.class);
        startActivity(intent);
    }


}