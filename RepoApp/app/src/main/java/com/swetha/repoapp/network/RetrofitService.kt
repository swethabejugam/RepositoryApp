package com.swetha.repoapp.network

import com.swetha.repoapp.model.Post
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitService {
@GET("/orgs/square/repos")
suspend fun getPosts(): Response<List<Post>>
}