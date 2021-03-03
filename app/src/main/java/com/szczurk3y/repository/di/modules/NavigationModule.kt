package com.szczurk3y.repository.di.modules

import com.szczurk3y.repository.framework.navigation.BaseNavigator
import com.szczurk3y.repository.framework.navigation.INavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object NavigationModule {

    @Provides
    @ActivityScoped
    fun provideNavController(): INavigator = BaseNavigator()

}