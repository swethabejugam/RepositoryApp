package com.swetha.repoapp.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.swetha.repoapp.model.Post

class PostViewModel: ViewModel() {
    private val postTitle = MutableLiveData<String>()
    private val postBody = MutableLiveData<String>()

    fun bind(post: Post){
        postTitle.value = post.name
        postBody.value = post.description
    }

    fun getPostTitle():MutableLiveData<String>{
        return postTitle
    }

    fun getPostBody():MutableLiveData<String>{
        return postBody
    }
}