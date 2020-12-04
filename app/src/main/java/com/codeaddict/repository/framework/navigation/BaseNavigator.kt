package com.codeaddict.repository.framework.navigation

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavOptions

class BaseNavigator : INavigator {

    // Nav controller must be setup inside an activity
    lateinit var navController: NavController

    override fun goTo(route: RouteDestination, args: Bundle?, navOptions: NavOptions?) {
        navController.navigate(route.destination, args, navOptions)
    }

    override fun goBack() {
        navController.popBackStack()
    }

    override fun setup(navController: NavController) {
        this.navController = navController
    }
}