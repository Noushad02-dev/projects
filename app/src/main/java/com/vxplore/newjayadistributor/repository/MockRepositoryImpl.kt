package com.vxplore.newjayadistributor.repository

import android.util.Log
import com.vxplore.newjayadistributor.model.AllProducts
import com.vxplore.newjayadistributor.model.CategoriesDataResponse
import com.vxplore.newjayadistributor.model.DashBoardData
import com.vxplore.newjayadistributor.model.DashBoardDataResponse
import com.vxplore.newjayadistributor.model.GetOtpResponse
import com.vxplore.newjayadistributor.model.LoginDataResponse
import com.vxplore.newjayadistributor.model.ProductDataResponse
import com.vxplore.newjayadistributor.model.ResetDataResponse
import com.vxplore.newjayadistributor.model.ViewCartDataResponse
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

    override fun saveUser(userId: String?) {
        myPref.setUserId(userId)
    }

    override fun getUserId(): String? {
        return myPref.getUserId()
    }

    override fun setEmailId(emailId: String?) {
        myPref.setEmailId(emailId)
    }

    override fun getEmailId(): String? {
        return myPref.getEmailId()
    }

    override fun setName(name: String?) {
        myPref.setName(name)
    }

    override fun getName(): String? {
        return myPref.getName()
    }
    override fun setPassCode(passCode: String?) {
        myPref.setPassCode(passCode)
    }

    override fun getPassCode(): String? {
        return myPref.getPassCode()
    }

    override fun setCategory(category: String?) {
        myPref.setCategory(category)
    }

    override fun getCategory(): String? {
        return myPref.getCategory()
    }

    override suspend fun login(email: String, password: String): LoginDataResponse? {
        val response = apiHelper.Login(email, password)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    override suspend fun getOtp(email: String): GetOtpResponse? {
        val response = apiHelper.getOtp(email)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
    override suspend fun resetPassword(
        recoverUsername: String,
        otp: String,
        password: String,
    ): ResetDataResponse? {
        val response = apiHelper.resetPassword(recoverUsername,otp,password)
        return if (response.isSuccessful){
            response.body()
        }else{
            null
        }
    }

    override suspend fun dashBoard(user_id: String, password: String): DashBoardDataResponse? {
        val response = apiHelper.dashBoard(user_id, password)
        return if (response.isSuccessful){
            response.body()
        }else{
            null
        }
    }

    override suspend fun categories(password: String): CategoriesDataResponse? {
        val response = apiHelper.categories(password)
        return if (response.isSuccessful){
            response.body()
        }else{
            null
        }
    }

    override suspend fun fetchProduct(
        category_id: String,
        search_text: String,
        password: String
    ): ProductDataResponse? {
        val response = apiHelper.fetchProduct(category_id, search_text, password)
        return if (response.isSuccessful){
            response.body()
        }else{
            null
        }
    }
    override suspend fun viewCart(
        userId: String,
        password: String,
        products: List<AllProducts>
    ): ViewCartDataResponse? {
        val response = apiHelper.viewCart(ApiInterface.ViewCartRequest(userId, password, products))
        return if (response.isSuccessful){
            response.body()
        }else{
            Log.e("Repository", "Error: ${response.code()}")
            null
        }
    }
}