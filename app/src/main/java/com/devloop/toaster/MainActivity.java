package com.devloop.toaster;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth baseAuth;
    @BindView(R.id.sign_in)
    CircularProgressButton circularButton;
    @BindView(R.id.email)
    EditText emailField;
    @BindView(R.id.password)
    EditText passwordField;
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Initialize Firebase Auth
        baseAuth = FirebaseAuth.getInstance();
    }

    @OnClick(R.id.sign_in)
    public void submit() {
        email = emailField.getText().toString();
        password = passwordField.getText().toString();
        circularButton.startAnimation();
        signInUser();
    }

    @OnClick(R.id.sign_up)
    public void redirectSignUp() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    public void signInUser() {
        baseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                circularButton.revertAnimation();
                Intent intent = new Intent(this, Home.class);
                startActivity(intent);
                Log.d("result", Objects.requireNonNull(task.getResult()).toString());
            } else {
                circularButton.revertAnimation();
                Toast.makeText(this, String.valueOf(Objects.requireNonNull(
                        task.getException()).getMessage()), Toast.LENGTH_LONG).show();
            }
        });
    }

}
