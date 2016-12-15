package com.fernamuruthi.mkuki.api;

import com.fernamuruthi.mkuki.api.model.Post;
import com.fernamuruthi.mkuki.api.model.PostResponse;
import com.fernamuruthi.mkuki.api.model.Posts;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by 001590 on 2016-11-24.
 */

public interface Endpoints {

    String URL = "/v1/";

    @Headers("Authorization: Bearer 7acb3b3eee12fd30076f158d51aafa55567a3179aa954dfcf741417f9ed9ddfe")
    @GET(URL+"posts")
    Call<Posts> posts();

    @Headers("Authorization: Bearer 7acb3b3eee12fd30076f158d51aafa55567a3179aa954dfcf741417f9ed9ddfe")
    @GET(URL+"posts")
    Call<Posts> posts(@Query("days_ago") int daysAgo);

    @Headers("Authorization: Bearer 7acb3b3eee12fd30076f158d51aafa55567a3179aa954dfcf741417f9ed9ddfe")
    @GET(URL+"posts")
    Call<Posts> posts(@Query("day") String day);

    @Headers("Authorization: Bearer 7acb3b3eee12fd30076f158d51aafa55567a3179aa954dfcf741417f9ed9ddfe")
    @GET(URL+"posts/{id}")
    Call<PostResponse> post(@Path("id") long id);
}
