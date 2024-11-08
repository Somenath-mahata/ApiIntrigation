package com.example.apiintrigation.retrofit


import com.example.apiintrigation.model.Post
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("posts")
    fun getPosts(): Call<ArrayList<Post>>
}