package com.example.avmessenger;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    Button button;
    EditText email,password;
    FirebaseAuth auth;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";//it is email pattern only



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        auth= FirebaseAuth.getInstance();

        button=findViewById(R.id.logbutton);
        email=findViewById(R.id.editTextlogEmail);
        password=findViewById(R.id.edittext11);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             String Email =email.getText().toString();
             String Pass =password.getText().toString();

             if(TextUtils.isEmpty(Email))
             {
                 Toast.makeText(Login.this, "Enter the Email", Toast.LENGTH_SHORT).show();
             } else if (TextUtils.isEmpty(Pass)) {

                 Toast.makeText(Login.this, "Enter the password", Toast.LENGTH_SHORT).show();
             } else if (!Email.matches(emailPattern)) {
                 email.setError("give valid email address");
                 
             } else if (Pass.length()<6) {
                 password.setError("More than six character");
                 Toast.makeText(Login.this, "Password should be grater than six character", Toast.LENGTH_SHORT).show();
             }
             else{
                 //In this we have to check our Email and password is matched or not .

                 auth.signInWithEmailAndPassword(Email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {

                         if(task.isSuccessful())
                         {
                             try {
                                 // if the task is completed or successful after the login it go to Main screen.
                                 Intent intent=new Intent(Login.this, MainActivity.class);
                                 startActivity(intent);
                                 finish();// it is used because after the   login we go to main screen .if Finish() is not there  if we click on back button we go back to login screen and  app is not closed .
                             }
                             catch (Exception e)

                             {
                                 Toast.makeText(Login.this,e.getMessage(), Toast.LENGTH_SHORT).show();

                                 //if he use is not get to go on main screen after login it toast what a error was.

                             }



                         }
                         else {// if task not successful.
                             Toast.makeText(Login.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();


                         }

                     }
                 });


             }



            }
        });
    }
}