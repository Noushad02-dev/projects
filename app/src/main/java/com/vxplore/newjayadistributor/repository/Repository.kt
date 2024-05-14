package com.vxplore.newjayadistributor.repository

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

interface Repository {
    fun setIsLoggedIn(done : Boolean)
    fun getIsLoggedIn() : Boolean
    fun removeUser()
    fun saveUser(userId: String?)

    fun getUserId() : String?
    fun setEmailId(emailId: String?)

    fun getEmailId() : String?
    fun setName(name: String?)
    fun getName() : String?
    fun setPassCode(passCode: String?)
    fun getPassCode() : String?

    fun setCategory(category: String?)
    fun getCategory() : String?
    fun setCartId(cartId: String?)
    fun getCartId() : String?
    fun setLocationId(locationId: String?)
    fun getLocationId() : String?


    suspend fun login(email : String, password : String) : LoginDataResponse?
    suspend fun getOtp(email: String) : GetOtpResponse?
    suspend fun resetPassword(recoverUsername: String, otp: String, password: String) : ResetDataResponse?
    suspend fun dashBoard(user_id: String, password: String) : DashBoardDataResponse?
    suspend fun categories(password: String) : CategoriesDataResponse?
    suspend fun fetchProduct(category_id: String, search_text: String, password: String) : ProductDataResponse?
    suspend fun viewCart(
        userId: String,
        password: String,
        products: List<AllProducts>
    ): ViewCartDataResponse?
    suspend fun showCart(
        userId: String,
        password: String,
    ): ShowCartDataResponse?
    suspend fun remove(user_id: String, cart_id: String) : ResetDataResponse?
    suspend fun fetchLocation(
        userId: String,
        password: String,
    ): FetchLocationDataResponse?
    suspend fun searchLocation(user_id: String, search_text: String, password: String) : LocationDataResponse?
    suspend fun saveLocation(user_id: String, location_id: String, password: String) : ResetDataResponse?
    suspend fun placeOrder(user_id: String, location_id: String, password: String) : ResetDataResponse?
}