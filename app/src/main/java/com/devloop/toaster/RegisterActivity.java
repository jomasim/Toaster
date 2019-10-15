package com.devloop.toaster;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.Objects;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.sign_up)
    CircularProgressButton circularButton;
    @BindView(R.id.username)
    EditText usernameField;
    @BindView(R.id.email)
    EditText emailField;
    @BindView(R.id.phone)
    EditText phoneField;
    @BindView(R.id.password)
    EditText passwordField;
    @BindView(R.id.confirm_password)
    EditText confirmPasswordField;
    private FirebaseAuth baseAuth;
    String email, password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.register);
        ButterKnife.bind(this);
        // initialize firebase auth;
        baseAuth = FirebaseAuth.getInstance();
    }

    @OnClick(R.id.sign_up)
    public void submit() {
        email = emailField.getText().toString();
        password = passwordField.getText().toString();

        //start progress
        circularButton.startAnimation();
        signUpUser();
    }

    public void signUpUser() {
        baseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        circularButton.revertAnimation();
                        Toast.makeText(this, "Hello, registration was successful!",
                                Toast.LENGTH_LONG).show();
                    } else {
                        circularButton.revertAnimation();
                        Toast.makeText(this, String.valueOf(Objects.requireNonNull(
                                task.getException()).getMessage()), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
