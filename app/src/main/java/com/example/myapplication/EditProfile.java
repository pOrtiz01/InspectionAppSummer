package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class EditProfile extends AppCompatActivity {
    private Button backButton;
    private EditText nameInput;
    private EditText emailInput;
    private EditText phoneInput;
    private EditText usernameInput;
    private EditText passwordInput;
    private EditText passwordConfirmInput;
    private Spinner addressInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
    }
}