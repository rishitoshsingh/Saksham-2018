package com.bdcoe.saksham

import android.app.Application
import com.onesignal.OneSignal

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        OneSignal
                .startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .init()

    }
}