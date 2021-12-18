package com.tp.sharefile.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class UserStorage @Inject constructor(context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences(SETTINGS_PREF, Context.MODE_PRIVATE)

    var loginName: String?
        set(value) = preferences.edit { putString(LOGIN_PREF, value) }
        get() = preferences.getString(LOGIN_PREF, null)

    companion object {
        const val SETTINGS_PREF = "settingsPref"
        const val LOGIN_PREF = "loginPref"
    }
}