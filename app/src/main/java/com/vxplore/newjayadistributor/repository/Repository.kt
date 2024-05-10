package com.vxplore.newjayadistributor.repository

interface Repository {
    fun setIsLoggedIn(done : Boolean)
    fun getIsLoggedIn() : Boolean
    fun removeUser()
}