package com.vxplore.newjayadistributor.repository.preference

interface PrefRepository {
    fun setIsLoggedIn(isLoggedIn: Boolean)
    fun getIsLoggedIn(): Boolean
    fun deleteUserId()
    fun setUserId(userId: String?)

    fun getUserId(): String?
    fun setEmailId(emailId: String?)

    fun getEmailId(): String?
    fun setName(emailId: String?)

    fun getName(): String?
    fun setPassCode(passCode: String?)

    fun getPassCode(): String?

    fun setCategory(category: String?)
    fun getCategory() : String?
}