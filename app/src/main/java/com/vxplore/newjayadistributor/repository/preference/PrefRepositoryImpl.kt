package com.vxplore.newjayadistributor.repository.preference

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class PrefRepositoryImpl @Inject constructor(
    context : Context
): PrefRepository {
    private val isLoggedInKey="isLoggedInKey"
    private val userIdKey="userIdKey"
    private lateinit var myPref : SharedPreferences

    override fun setIsLoggedIn(isLoggedIn: Boolean) {
        myPref.edit().putBoolean(isLoggedInKey,isLoggedIn).apply()
    }

    override fun getIsLoggedIn(): Boolean {
        return  myPref.getBoolean(isLoggedInKey,false)
    }
    override fun deleteUserId() {
        myPref.edit().remove(userIdKey).apply()
    }
}