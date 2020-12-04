package com.codeaddict.repository.framework.navigation

import androidx.annotation.IdRes
import androidx.navigation.NavOptions
import com.codeaddict.repository.R

sealed class RouteDestination(@IdRes val destination: Int) {
    sealed class Content(@IdRes destination: Int): RouteDestination(destination) {
        object ListFragment : Content(R.id.listFragment)
        object DetailsFragment : Content(R.id.detailsFragment)
    }
}

val slideAnim: NavOptions
    get() = NavOptions.Builder()
        .setEnterAnim(R.anim.slide_in_right)
        .setExitAnim(R.anim.slide_out_left)
        .setPopEnterAnim(R.anim.slide_in_left)
        .setPopExitAnim(R.anim.slide_out_right)
        .build()