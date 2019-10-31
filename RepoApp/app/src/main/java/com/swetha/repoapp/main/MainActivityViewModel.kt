package com.swetha.repoapp.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import android.view.View
import com.swetha.repoapp.R
import com.swetha.repoapp.adapters.PostAdapter
import com.swetha.repoapp.model.Post
import com.swetha.repoapp.network.RetrofitFactory
import kotlinx.coroutines.*
import retrofit2.HttpException
import javax.inject.Inject

class MainActivityViewModel @Inject constructor() : ViewModel() {
    lateinit var list: List<Post>
    val postAdapter: PostAdapter = PostAdapter()

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadPosts() }
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    init {
        loadPosts()
    }


    private fun onRetrievePostListStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrievePostListFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrievePostListSuccess(postList: List<Post>) {
        postAdapter.updatePostList(postList)
    }

    private fun onRetrievePostListError() {
        errorMessage.value = R.string.post_error
    }

    fun loadPosts() {
        onRetrievePostListStart()
        val service = RetrofitFactory.makeRetrofitService()

        viewModelJob =coroutineScope.launch {
            val response = service.getPosts()
            try {
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Log.e("success", response.body().toString())
                        response.body()?.let {

                            onRetrievePostListFinish()
                            onRetrievePostListSuccess(it)
                            list = it
                        }

                    } else {
                        reqFail()
                        Log.e("Exception", " Error network operation failed with ${response.code()}")

                    }
                }
            } catch (e: HttpException) {
                reqFail()
                Log.e("REQUEST", "Exception ${e.message}")
            } catch (e: Throwable) {
                reqFail()
                Log.e("REQUEST", "Ooops: Something else went wrong")
            }

        }

    }

    fun reqFail() {
        onRetrievePostListError()
        onRetrievePostListFinish()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()

    }
}

