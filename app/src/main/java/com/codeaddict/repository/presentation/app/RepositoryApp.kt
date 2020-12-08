package com.codeaddict.repository.presentation.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import leakcanary.LeakCanary

@HiltAndroidApp
class RepositoryApp  : Application()