package com.vxplore.newjayadistributor.repository

import com.vxplore.newjayadistributor.repository.preference.PrefRepository
import javax.inject.Inject

class MockRepositoryImpl @Inject constructor(
    private val myPref: PrefRepository,
    private val apiHelper: ApiInterface,
) : Repository {

    override fun setIsLoggedIn(done: Boolean) {
        myPref.setIsLoggedIn(done)
    }
    override fun getIsLoggedIn(): Boolean {
        return myPref.getIsLoggedIn()
    }
    override fun removeUser() {
        myPref.deleteUserId()
    }
}