package com.example.admin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final int CAMERA_REQUEST_CODE = 102;
    EditText name,email,password;
    private Button btn,btn1;
    String image;
    private ImageView iv, selectedImage;
    Bitmap bitmap;
    private TextView tv;
    private ProgressBar progressBar;
    private final int GALLERY = 1;
    public static final int CAMERA_PERM_CODE=101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        askForPermissions();

        name = findViewById(R.id.idEdtName);
        email = findViewById(R.id.idEdtEmail);
        password = findViewById(R.id.idEdtPassword);

        btn = findViewById(R.id.btnUpload);
        btn1=findViewById(R.id.btnSelect);
        selectedImage = findViewById(R.id.imageView);
        tv = findViewById(R.id.message);
        progressBar = findViewById(R.id.progressBar);
        tv.setText("");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                askCameraPermission();
                /*Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Image"), GALLERY);*/
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImageUsingRetrofit(bitmap);
            }
        });
    }

    private void askCameraPermission() {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        }else {
            openCamera();
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == CAMERA_PERM_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //openCamera();
            }else{
                Toast.makeText(this, "Camera Permission is Required to Use camera", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openCamera() {

        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, CAMERA_REQUEST_CODE);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                     bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    iv.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Failed to select image!", Toast.LENGTH_SHORT).show();
                    tv.setText("Failed to select image!");
                    tv.setTextColor(Color.parseColor("#FF0000"));
                }
            }
        }*/
        if(requestCode == CAMERA_REQUEST_CODE){
             bitmap = (Bitmap) data.getExtras().get("data");
             selectedImage.setImageBitmap(bitmap);
        }
    }
    private void uploadImageUsingRetrofit(Bitmap bitmap){
        progressBar.setVisibility(View.VISIBLE);
        tv.setText("");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        image = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
        //String name = String.valueOf(Calendar.getInstance().getTimeInMillis());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyAPI.IMAGEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MyAPI myImageInterface = retrofit.create(MyAPI.class);
        Call<Model> call = myImageInterface.uploadImageApi(name.getText().toString(),email.getText().toString(),password.getText().toString(),image);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        name.setText("");
                        email.setText("");
                        password.setText("");

                        Toast.makeText(MainActivity.this, "Image Uploaded Successfully!!", Toast.LENGTH_SHORT).show();
                        tv.setText("Image Uploaded Successfully!!");
                        tv.setTextColor(Color.parseColor("#008000"));
                    } else {
                        tv.setText("No response from the server");
                        tv.setTextColor(Color.parseColor("#FF0000"));
                    }
                }else{
                    tv.setText("Response not successful "+response.toString());
                    tv.setTextColor(Color.parseColor("#FF0000"));
                    Toast.makeText(getApplicationContext(), "Response not successful "+response.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Error occurred!", Toast.LENGTH_SHORT).show();
                tv.setText("Error occurred during upload");
                tv.setTextColor(Color.parseColor("#FF0000"));
            }
        });
    }

    private void askForPermissions(){
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                        }
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

}