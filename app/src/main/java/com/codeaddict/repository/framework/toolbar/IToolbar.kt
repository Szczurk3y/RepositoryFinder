package com.codeaddict.repository.framework.toolbar

import androidx.annotation.ColorRes
import androidx.annotation.IdRes

interface IToolbar {

    fun initToolbar()
    fun makeFullScreen(@ColorRes statusBarColor: Int)

    var allowGoBack: Boolean

}