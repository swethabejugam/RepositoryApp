package com.swetha.repoapp.main

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager

import com.swetha.repoapp.R
import com.swetha.repoapp.injection.DaggerAppComponent
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    private lateinit var binding: com.swetha.repoapp.databinding.ActivityMainBinding

    @Inject
    lateinit var viewModel: MainActivityViewModel

    private var errorSnackbar: Snackbar? = null


    override fun onCreate(savedInstanceState: Bundle?) {

        DaggerAppComponent.create().inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.postList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

//        viewModel = ViewModelProviders.of(this, ViewModelFactory(this)).get(MainActivityViewModel::class.java)
        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })
        binding.model = viewModel
        binding.postList.adapter = viewModel.postAdapter
        binding.setLifecycleOwner(this)



    }


    private fun showError(@StringRes errorMessage: Int) {
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }
}
