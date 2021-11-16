package com.icanerdogan.kodlineprojectv2;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RefreshDatabase extends Worker {
    String content = "";
    JsonPlaceHolderApi jsonPlaceHolderApi;
    Context myContext;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RefreshDatabase(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.myContext = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        createPost();
        return Result.success();
    }

    private void createPost(){
        Post post = new Post(23, "Requst Deneme","String deneme yazısı");

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<Post> call = jsonPlaceHolderApi.createPostApi(post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if (!response.isSuccessful()){
                    Toast.makeText(myContext.getApplicationContext(), "Hata", Toast.LENGTH_SHORT).show();
                }

                Post postResponse = response.body();
                content += "Code: "+response.code() + "\n";
                content += "ID: "+postResponse.getId() + "\n";
                content += "User ID: "+postResponse.getUserId() + "\n";
                content += "Title: "+postResponse.getTitle() + "\n";
                content += "Text: "+postResponse.getText() + "\n";

                System.out.println(content);
                System.out.println("POST PAYLAŞILDI :)");

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.i("POST RESPONSE 1","Error");
            }
        });
    }
}
