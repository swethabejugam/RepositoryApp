package com.swetha.repoapp.injection

import android.content.Context
import com.swetha.repoapp.main.MainActivity
import com.swetha.repoapp.main.MainActivityViewModel
import dagger.BindsInstance
import dagger.Component
import dagger.Subcomponent
import javax.inject.Singleton

@Component
interface AppComponent {

    fun inject(activity: MainActivity)

}
