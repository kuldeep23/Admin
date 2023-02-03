package com.example.admin;

import com.example.admin.Model;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface MyAPI {
    String IMAGEURL = "https://gatesadmin.000webhostapp.com/";
    @FormUrlEncoded
    @POST("jh_visitors.php")
    Call<Model> uploadImageApi(
            @Field("Visitor_Type") String visitortype,
            @Field("Visitor_Name") String visitorname,
            @Field("Visitor_Mobile") String visitormobile,
            @Field("Visitor_Flat_No") String visitorflat,
            @Field("Visitor_Image") String visitorimage
    );
}
