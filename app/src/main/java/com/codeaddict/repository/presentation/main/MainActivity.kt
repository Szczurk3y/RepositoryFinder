package com.codeaddict.repository.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.codeaddict.repository.R
import com.codeaddict.repository.framework.navigation.INavigator
import com.codeaddict.repository.framework.toolbar.NoToolbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    @Inject lateinit var navigator: INavigator

    @Inject lateinit var toolbar: NoToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_content_host_fragment) as NavHostFragment
        val navController: NavController = navHostFragment.navController

        navigator.apply {
            setup(navController)
        }

        toolbar.apply {
            initToolbar()
        }
    }
}