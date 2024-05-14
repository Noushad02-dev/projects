package com.vxplore.newjayadistributor.repository

import android.util.Log
import com.vxplore.newjayadistributor.model.AllProducts
import com.vxplore.newjayadistributor.model.CategoriesDataResponse
import com.vxplore.newjayadistributor.model.DashBoardData
import com.vxplore.newjayadistributor.model.DashBoardDataResponse
import com.vxplore.newjayadistributor.model.FetchLocationDataResponse
import com.vxplore.newjayadistributor.model.GetOtpResponse
import com.vxplore.newjayadistributor.model.LocationDataResponse
import com.vxplore.newjayadistributor.model.LoginDataResponse
import com.vxplore.newjayadistributor.model.ProductDataResponse
import com.vxplore.newjayadistributor.model.ResetDataResponse
import com.vxplore.newjayadistributor.model.ShowCartDataResponse
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

    override fun setCartId(cartId: String?) {
       myPref.setCartId(cartId)
    }

    override fun getCartId(): String? {
        return myPref.getCartId()
    }

    override fun setLocationId(locationId: String?) {
        myPref.setLocationId(locationId)
    }

    override fun getLocationId(): String? {
       return myPref.getLocationId()
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

    override suspend fun showCart(userId: String, password: String): ShowCartDataResponse? {
        val response = apiHelper.showCart(userId,password)
        return if (response.isSuccessful){
            response.body()
        }else{
            null
        }
    }

    override suspend fun remove(user_id: String, cart_id: String): ResetDataResponse? {
        val response = apiHelper.remove(user_id, cart_id)
        return if (response.isSuccessful){
            response.body()
        }else{
            null
        }
    }

    override suspend fun fetchLocation(
        userId: String,
        password: String
    ): FetchLocationDataResponse? {
        val response = apiHelper.fetchLocation(userId,password)
        return if (response.isSuccessful){
            response.body()
        }else{
            null
        }
    }

    override suspend fun searchLocation(
        user_id: String,
        search_text: String,
        password: String
    ): LocationDataResponse? {
        val response = apiHelper.searchLocation(user_id, search_text, password)
        return if (response.isSuccessful){
            response.body()
        }else{
            null
        }
    }

    override suspend fun saveLocation(
        user_id: String,
        location_id: String,
        password: String
    ): ResetDataResponse? {
        val response = apiHelper.saveLocation(user_id, location_id, password)
        return if (response.isSuccessful){
            response.body()
        }else{
            null
        }
    }

    override suspend fun placeOrder(
        user_id: String,
        location_id: String,
        password: String
    ): ResetDataResponse? {
        val response = apiHelper.placeOrder(user_id, location_id, password)
        return if (response.isSuccessful){
            response.body()
        }else{
            null
        }
    }
}