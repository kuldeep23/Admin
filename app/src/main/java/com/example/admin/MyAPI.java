package com.example.admin;

import com.example.admin.Model;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface MyAPI {
    String IMAGEURL = "https://gatesadmin.000webhostapp.com/";
    @FormUrlEncoded
    @POST("file.php")
    Call<Model> uploadImageApi(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("image") String image
    );
}
