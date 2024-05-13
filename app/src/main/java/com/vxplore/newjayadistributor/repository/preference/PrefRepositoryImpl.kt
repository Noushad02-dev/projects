package com.vxplore.newjayadistributor.repository.preference

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class PrefRepositoryImpl @Inject constructor(
    context : Context
): PrefRepository {
    private val isLoggedInKey="isLoggedInKey"
    private val userIdKey="userIdKey"
    private val emailIdKey = "emailIdKey"
    private val nameKey = "nameKey"
    private val PassCode = "PassCode"
    private val Category = "Category"
    private val myPref: SharedPreferences = context.getSharedPreferences("myPref", Context.MODE_PRIVATE)


    override fun setIsLoggedIn(isLoggedIn: Boolean) {
        myPref.edit().putBoolean(isLoggedInKey,isLoggedIn).apply()
    }

    override fun getIsLoggedIn(): Boolean {
        return  myPref.getBoolean(isLoggedInKey,true)
    }
    override fun deleteUserId() {
        myPref.edit().remove(userIdKey).apply()
    }

    override fun setUserId(userId: String?) {
        myPref.edit().putString(userIdKey, userId.toString()).apply()
    }

    override fun getUserId(): String? {
        return myPref.getString(userIdKey, "")
    }

    override fun setEmailId(emailId: String?) {
        myPref.edit().putString(emailIdKey,emailId.toString()).apply()
    }

    override fun getEmailId(): String? {
        return myPref.getString(emailIdKey,"")
    }

    override fun setName(name: String?) {
        myPref.edit().putString(nameKey,name.toString()).apply()
    }

    override fun getName(): String? {
        return myPref.getString(nameKey,"")
    }

    override fun setPassCode(passCode: String?) {
        myPref.edit().putString(PassCode,passCode.toString()).apply()
    }

    override fun getPassCode(): String? {
        return myPref.getString(PassCode,"")
    }

    override fun setCategory(category: String?) {
        myPref.edit().putString(Category,category.toString()).apply()
    }

    override fun getCategory(): String? {
        return myPref.getString(Category,"")
    }
}