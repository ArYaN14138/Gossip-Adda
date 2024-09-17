package com.example.avmessenger;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import de.hdodenhof.circleimageview.CircleImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class registration2 extends AppCompatActivity {

    TextView login_button;
    EditText rg_Email,rg_password,rg_repassword,Signup_username;

    Button signup_button;

    CircleImageView rg_image;
    FirebaseAuth auth;
    String imageURI;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";//it is email pattern only

    FirebaseDatabase database;
    FirebaseStorage storage;







    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration2);
        Signup_username=findViewById(R.id.Signup_username);
        database=FirebaseDatabase.getInstance();
        storage=FirebaseStorage.getInstance();


        signup_button=findViewById(R.id.signup_button);
        rg_Email=findViewById(R.id.editText_signup_Email);
        rg_password=findViewById(R.id.edittext_signup_password);
        rg_repassword=findViewById(R.id.Signup_confirm_password);
        login_button=findViewById(R.id.textViewLogin);


        rg_image= findViewById(R.id.circular_img);//problem 1: @SuppressLint("WrongViewCast") remove








        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(registration2.this, Login.class);
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

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=Signup_username.getText().toString();
                String   email=rg_Email.getText().toString();
                String password= rg_password.getText().toString();
                String cpassword=rg_repassword.getText().toString();
                String status="Hey I'm using this application";

                if(TextUtils.isEmpty(name)||TextUtils.isEmpty(email)||TextUtils.isEmpty(password)||TextUtils.isEmpty(cpassword))
                {
                    Toast.makeText(registration2.this, "Please provide a valid information ", Toast.LENGTH_SHORT).show();

                } else if (!email.matches(emailPattern)) {
                    rg_Email.setError("type a valid mail");
                    
                }
                else if (password.length()<6) {
                    rg_password.setError("More than six character");
                   

            } else if (!password.equals(cpassword)) {
                    rg_password.setError("password dosen't match");
                }

                else {

                    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful())
                            {
                                String id= task.getResult().getUser().getUid();
                                DatabaseReference reference=database.getReference().child("user").child(id);
                                StorageReference storageReference=storage.getReference().child("upload").child(id);


                                if(imageURI!=null)
                                {
                                 storageReference.putFile(Uri.parse(imageURI)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                     @Override
                                     public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                       if(task.isSuccessful())
                                       {
                                           storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                               @Override
                                               public void onSuccess(Uri uri) {


                                                   imageURI= String.valueOf(Uri.parse(uri.toString()));

                                                   Users users=new Users(id,name,email,password,cpassword,imageURI,status);
                                                   reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                       @Override
                                                       public void onComplete(@NonNull Task<Void> task) {
                                                           if(task.isSuccessful())
                                                           {
                                                               Intent intent=new Intent(registration2.this, MainActivity.class);
                                                               startActivity(intent);
                                                               finish();
                                                           }
                                                           else{
                                                               Toast.makeText(registration2.this, "Error while creating the user", Toast.LENGTH_SHORT).show();
                                                           }
                                                       }
                                                   });








                                               }
                                           });


                                       }




                                     }
                                 });

                                }
                                else {
                                    String status="hey I am using this application";
                                    imageURI="https://firebasestorage.googleapis.com/v0/b/a-v-messenger-94ca5.appspot.com/o/men.png?alt=media&token=a61d7174-7a18-45e2-a6ee-9afd33c79b8a";
                               Users users=new Users(id,name,email,password,cpassword,imageURI,status);

                                    reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                Intent intent=new Intent(registration2.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                            else{
                                                Toast.makeText(registration2.this, "Error while creating the user", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }

                            }

                            else {
                                Toast.makeText(registration2.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }


                        }
                    });
                }
                    
                }
            });






        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==10)
        {
            if(data!=null)
            {
                imageURI= String.valueOf(data.getData());
                rg_image.setImageURI(Uri.parse(imageURI));
            }
        }
    }
}
