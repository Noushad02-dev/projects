package com.vxplore.newjayadistributor.repository.preference

interface PrefRepository {
    fun setIsLoggedIn(isLoggedIn: Boolean)
    fun getIsLoggedIn(): Boolean
    fun deleteUserId()
}