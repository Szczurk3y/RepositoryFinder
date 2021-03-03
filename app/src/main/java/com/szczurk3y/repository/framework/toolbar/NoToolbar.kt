package com.szczurk3y.repository.framework.toolbar

import androidx.appcompat.app.AppCompatActivity

class NoToolbar(
    private val activity: AppCompatActivity
) : IToolbar {

    override fun initToolbar() {
        activity.setSupportActionBar(null)
    }
}
