package com.codeaddict.repository.framework.navigation

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavOptions

interface INavigator {

    fun goTo(route: RouteDestination, args: Bundle? = null, navOptions: NavOptions? = slideAnim)
    fun goBack()
    fun setup(navController: NavController)

}