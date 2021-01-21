package com.example.retrofittrial.api_interfaces;

import com.example.retrofittrial.models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {

    @GET("getData")
    Call<List<Post>>getPosts();

    @POST("sendData")
    Call<Post> createPost(@Body Post post);
}
