package com.codeaddict.repository.framework.toolbar

import android.graphics.Color
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

@Suppress("DEPRECATION")
class NoToolbar(
    private val activity: AppCompatActivity
) : IToolbar {

    override fun initToolbar() {
        activity.setSupportActionBar(null)
    }
}
