package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.usage.StorageStats;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.Model.Scholarship;
import com.example.myapplication.Model.Users;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.UUID;

public class EditProfile extends AppCompatActivity {
    private Button btnSaveChange;
    private EditText nama, email, alamat, phone;
    private ImageView imgProfile;
    private String strNama, strEmail, strAlamat, strPhone;

    public Uri databaseUri;
    private String destinationUri = UUID.randomUUID().toString() + ".jpg";
    public StorageTask storageTask;
    private DatabaseReference reference;
    private StorageReference storageReference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        storageReference = FirebaseStorage.getInstance().getReference("imageProfile");

        btnSaveChange = findViewById(R.id.btnSaveChange);
        nama = findViewById(R.id.nama);
        alamat = findViewById(R.id.alamat);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        imgProfile = findViewById(R.id.imgProfile);

        Glide.with(EditProfile.this).load(GlobalVariable.user.getProfImage()).into(imgProfile);
        nama.setText(GlobalVariable.user.getNama());
        alamat.setText(GlobalVariable.user.getAlamat());
        email.setText(GlobalVariable.user.getEmail());
        phone.setText(GlobalVariable.user.getPhone());



        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        btnSaveChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strNama = nama.getText().toString();
                strAlamat = alamat.getText().toString();
                strEmail = email.getText().toString();
                strPhone = phone.getText().toString();
                updateData(strNama, strEmail, strAlamat, strPhone);
            }
        });

    }
    private void updateData(String nama, String email, String alamat, String phone){
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        String userId = firebaseUser.getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("nama", nama);
        hashMap.put("email", email);
        hashMap.put("alamat", alamat);
        hashMap.put("phone", phone);

        reference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(EditProfile.this, "Saving Data", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(EditProfile.this, "Failed Data", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    private String getFilesExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    private void uploadImage() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading Image...");
        progressDialog.show();

        if (databaseUri != null) {
            final StorageReference mStorage = storageReference.child(System.currentTimeMillis()
                    + "."
                    + getFilesExtension(databaseUri));
            storageTask = mStorage.putFile(databaseUri);
            storageTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        task.getException();
                    }
                    return mStorage.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        String myUrl = downloadUri.toString();
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid());
                        HashMap<String, Object> hashMap = new HashMap<>();

                        hashMap.put("profImage", "" + myUrl);

                        reference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        GlobalVariable.user = snapshot.getValue(Users.class);
                                        GlobalVariable.user.setProfImage(myUrl);
                                        progressDialog.dismiss();
                                        Toast.makeText(EditProfile.this, "Complete", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        });
                    } else {
                        Toast.makeText(EditProfile.this, "Failed To Upload Image", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(EditProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(EditProfile.this, "Image Not Found", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }

    private void selectImage() {
        final CharSequence[] items = {"Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile.this);
        builder.setTitle("Select Image");
        builder.setItems(items, (dialog, item) -> {
            if (items[item].equals("Gallery")) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 200);
            } else if (items[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK && data != null) {
            final Uri urlImage = data.getData();
            UCrop.of(urlImage, Uri.fromFile(new File(getCacheDir(), destinationUri)))
                    .withAspectRatio(1, 1)
                    .start(this);
        } else if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            Uri resultUri = UCrop.getOutput(data);
            databaseUri = resultUri;

            imgProfile.setImageURI(databaseUri);
            imgProfile.setBackgroundResource(0);
            uploadImage();
        } else {
            Toast.makeText(this, "Failed to take picture", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}