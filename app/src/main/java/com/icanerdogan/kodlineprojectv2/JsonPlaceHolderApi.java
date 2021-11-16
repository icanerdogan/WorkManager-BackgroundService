package com.icanerdogan.kodlineprojectv2;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {
    @POST("posts")
    Call<Post> createPostApi(@Body Post post);
}
