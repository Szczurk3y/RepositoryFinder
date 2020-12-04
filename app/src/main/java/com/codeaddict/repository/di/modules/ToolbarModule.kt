package com.codeaddict.repository.di.modules

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import com.codeaddict.repository.framework.toolbar.NoToolbar
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object ToolbarModule {

    @Provides
    @ActivityScoped
    fun provideToolbar(
        activity: Activity
    ): NoToolbar = NoToolbar(activity as AppCompatActivity)

}