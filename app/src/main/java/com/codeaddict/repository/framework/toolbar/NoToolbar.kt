package com.codeaddict.repository.framework.toolbar

import android.view.View
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class NoToolbar(
    private val activity: AppCompatActivity
) : IToolbar {

    override var allowGoBack: Boolean = false

    override fun initToolbar() {
        activity.setSupportActionBar(null)
    }

    override fun makeFullScreen(@ColorRes statusBarColor: Int) {
        val window = activity.window

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = ContextCompat.getColor(activity, statusBarColor)
    }
}
