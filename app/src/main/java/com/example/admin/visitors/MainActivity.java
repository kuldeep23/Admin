package com.example.admin.visitors;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.admin.Dashboard;
import com.example.admin.controller.Controller;
import com.example.admin.visitors.model.Model;
import com.example.admin.api.APISet;
import com.example.admin.R;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    AutoCompleteTextView visitortype;
    TextInputEditText visitorname, visitorphone, visitorflatno;
    private static final String[] VISITORTYPE = new String[] {
            "Guest", "Delivery Boy",
            "Service Boy", "Milk-Man",
            "Maid", "Newspaper Boy","Others" };
    EditText name,email,password;
    private Button upload;
    String image;
    private ImageView selectedImage;
    Bitmap bitmap;
    private ProgressBar progressBar;

    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        visitortype = findViewById(R.id.EditVisitorType);
        visitorname = findViewById(R.id.EditVisitorName);
        visitorphone = findViewById(R.id.EditMoblieNumber);
        visitorflatno = findViewById(R.id.EditVisitorFlatNumber);
        upload=findViewById(R.id.btnSelect);
        selectedImage = findViewById(R.id.imageView);
        progressBar = findViewById(R.id.progressBar);

        ArrayAdapter<String> visitorTypeAdapter = new ArrayAdapter<>(MainActivity.this,R.layout.item_list,VISITORTYPE);
        visitortype.setAdapter(visitorTypeAdapter);
        visitortype.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, visitortype.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });


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
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        image = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);


        Call<Model> call = Controller
                .getInstance()
                .getapi()
                .uploadImageApi(visitortype.getText().toString(),
                        visitorname.getText().toString(),
                        visitorphone.getText().toString(),
                        visitorflatno.getText().toString(),
                        image);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    textToSpeech = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int i) {
                            if(i != TextToSpeech.ERROR)
                                textToSpeech.setLanguage(Locale.US);
                                textToSpeech.speak("Entry Successfull. Do you want to enter more visitor? Then press enter more otherwise press okay", TextToSpeech.QUEUE_FLUSH, null);
                        }
                    });
                    if (response.body() != null) {

                        new MaterialAlertDialogBuilder(MainActivity.this, R.style.AlertDialogTheme)
                                .setTitle("Visitor In")
                                .setMessage("Visitor successfully enter in the society!!!")
                                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent goToNextActivity = new Intent(getApplicationContext(), Dashboard.class);
                                        startActivity(goToNextActivity);
                                        finish();
                                    }
                                })
                                .setNeutralButton("Enter More", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent goToNextActivity = new Intent(getApplicationContext(),MainActivity.class);
                                        startActivity(goToNextActivity);
                                        finish();
                                    }
                                })
                                .show();

                    } else {
                        Toast.makeText(MainActivity.this, "Image Uploaded Failed!!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Response not successful "+response.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Error occurred!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}