package com.example.admin;

import android.content.Intent;
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
import androidx.appcompat.app.AppCompatActivity;
import com.github.dhaval2404.imagepicker.ImagePicker;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    String[] courses = { "Guest", "Delivery Boy",
            "Service Boy", "Milk-Man",
            "Maid", "Newspaper Boy","Others" };
    EditText name,email,password;
    private Button upload;
    String image;
    private ImageView selectedImage;
    Bitmap bitmap;
    private TextView tv;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.idEdtName);
        email = findViewById(R.id.idEdtEmail);
        password = findViewById(R.id.idEdtPassword);
        upload=findViewById(R.id.btnSelect);
        selectedImage = findViewById(R.id.imageView);
        tv = findViewById(R.id.message);
        progressBar = findViewById(R.id.progressBar);
        tv.setText("");


        selectedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(MainActivity.this)
                        .cameraOnly()
                        .compress(200)                          //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(300, 300)	//Final image resolution will be less than 1080 x 1080(Optional)
                        /*.crop()*/	    			            //Crop image(Optional), Check Customization for more option
                        .start();

            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImageUsingRetrofit(bitmap);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri uri = data.getData();
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            selectedImage.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void uploadImageUsingRetrofit(Bitmap bitmap){

        progressBar.setVisibility(View.VISIBLE);
        tv.setText("");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        image = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

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


}