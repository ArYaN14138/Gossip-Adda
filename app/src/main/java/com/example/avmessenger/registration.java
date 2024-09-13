package com.example.avmessenger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class registration extends AppCompatActivity {

    TextView login_button;
    EditText rg_Email,rg_password,rg_repassword;
    Button signup_button;
    CircleImageView rg_image;
    FirebaseAuth auth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration2);

        signup_button=findViewById(R.id.signup_button);
        rg_Email=findViewById(R.id.editText_signup_Email);
        rg_password=findViewById(R.id.edittext_signup_password);
        rg_repassword=findViewById(R.id.Signup_confirm_password);
        login_button=findViewById(R.id.textViewLogin);
        rg_image= findViewById(R.id.circular_img);








        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(registration.this, Login.class);
                startActivity(intent);
                finish();

            }
        });
        rg_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),10);
            }
        });




        }
    }
