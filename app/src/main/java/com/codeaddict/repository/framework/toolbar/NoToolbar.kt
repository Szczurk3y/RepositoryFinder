package com.codeaddict.repository.framework.toolbar

import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.codeaddict.repository.R

class NoToolbar(
    private val activity: AppCompatActivity
) : IToolbar {

    override var allowGoBack: Boolean = false

    override fun initToolbar() {
        activity.setSupportActionBar(null)
    }
}
