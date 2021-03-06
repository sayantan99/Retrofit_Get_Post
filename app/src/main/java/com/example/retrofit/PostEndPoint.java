package com.example.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PostEndPoint {


   /* @GET("posts")
    Call<List<post>> getPosts(); */ // Specifying id as path parameter

    @GET("posts/{id}")
    Call<post> getSinglePost(@Path("id") int id);

    @GET("posts")
    Call<List<post>> getPostByUserId(@Query("userId") int id);

    @POST("posts")
    Call<post> newpost(@Header ("Token") String token ,@Body post post);

}
