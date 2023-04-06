package my.securegates.admin.api;

import my.securegates.admin.visitors.model.AllVisitorListModel;
import my.securegates.admin.visitors.model.Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface APISet {

    @FormUrlEncoded
    @POST("jh_visitors.php")
    Call<Model> uploadImageApi(
            @Field("Visitor_Type") String visitortype,
            @Field("Visitor_Name") String visitorname,
            @Field("Visitor_Mobile") String visitormobile,
            @Field("Visitor_Flat_No") String visitorflat,
            @Field("Visitor_Image") String visitorimage
    );

    @FormUrlEncoded
    @POST("visitor_out.php")
    Call<AllVisitorListModel> visitorOut(
            @Field("visitor_id") String visitorid
    );

    @GET("all_visitors_list.php")
    Call<List<AllVisitorListModel>> getVisitorList();
}
