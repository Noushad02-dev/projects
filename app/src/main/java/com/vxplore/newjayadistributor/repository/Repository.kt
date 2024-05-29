package com.vxplore.newjayadistributor.repository

import com.vxplore.newjayadistributor.model.AddProductListDataResponse
import com.vxplore.newjayadistributor.model.AllProducts
import com.vxplore.newjayadistributor.model.CategoriesDataResponse
import com.vxplore.newjayadistributor.model.DashBoardData
import com.vxplore.newjayadistributor.model.DashBoardDataResponse
import com.vxplore.newjayadistributor.model.DueOrderDataResponse
import com.vxplore.newjayadistributor.model.FetchLocationDataResponse
import com.vxplore.newjayadistributor.model.GetOtpResponse
import com.vxplore.newjayadistributor.model.LocationDataResponse
import com.vxplore.newjayadistributor.model.LoginDataResponse
import com.vxplore.newjayadistributor.model.MyStockAllProducts
import com.vxplore.newjayadistributor.model.MyStockListDataResponse
import com.vxplore.newjayadistributor.model.OrderDetailsDataResponse
import com.vxplore.newjayadistributor.model.OrderHistoryDataResponse
import com.vxplore.newjayadistributor.model.OrderReceiveDataResponse
import com.vxplore.newjayadistributor.model.OrderStatusDataResponse
import com.vxplore.newjayadistributor.model.PDFDataResponse
import com.vxplore.newjayadistributor.model.ProductDataResponse
import com.vxplore.newjayadistributor.model.ResetDataResponse
import com.vxplore.newjayadistributor.model.ShowCartDataResponse
import com.vxplore.newjayadistributor.model.TrackOrderDataResponse
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

    fun setSelectedLocationId(selectedLocationId: String?)
    fun getSelectedLocationId() : String?
    fun setOrderReceivedId(orderReceivedId: String?)
    fun getOrderReceivedId() : String?
    fun setProductId(productId: String?)
    fun getProductId() : String?
    fun setMobileNo(mobileNo: String?)
    fun getMobileNo() : String?


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
    suspend fun orderReceive(
        userId: String,
        password: String,
    ): OrderReceiveDataResponse?
    suspend fun dueDelivery(
        userId: String,
        password: String,
    ): DueOrderDataResponse?
    suspend fun orderDetails(
        userId: String,
        order_id: String,
        password: String,
    ): OrderDetailsDataResponse?
    suspend fun confirmDispatch(
        password: String,
        order_id: String,
    ): ResetDataResponse?
    suspend fun confirmOrder(
        password: String,
        order_id: String,
    ): ResetDataResponse?
    suspend fun trackOrder(
        userId: String,
        password: String,
    ): TrackOrderDataResponse?
    suspend fun orderStatus(
        userId: String,
        order_id: String,
        password: String,
    ): OrderStatusDataResponse?

    suspend fun removeOrderProduct(
        userId: String,
        order_id: String,
        product_id: String,
        password: String,
    ): ResetDataResponse?
    suspend fun updateProduct(
        userId: String,
        order_id: String,
        product_id: String,
        password: String,
        quantity: String,
    ): ResetDataResponse?
    suspend fun addProductList(
        userId: String,
        order_id: String,
        password: String,
        search_text: String,
    ): AddProductListDataResponse?

    suspend fun addProduct(
        userId: String,
        order_id: String,
        password: String,
        product_id: String,
        unit: String,
        quantity: String,
        price_per_unit_string: String,
    ): ResetDataResponse?
    suspend fun orderHistory(
        userId: String,
        password: String,
    ): OrderHistoryDataResponse?
    suspend fun myStockList(
        userId: String,
        order_id: String,
        password: String,
    ): MyStockListDataResponse?
    suspend fun updateProduct(
        userId: String,
        password: String,
        products: List<MyStockAllProducts>
    ): ViewCartDataResponse?

    suspend fun pdf(
        userId: String,
        order_id: String,
        password: String,
    ): PDFDataResponse?

    suspend fun mobileVerify(mobile: String) : ResetDataResponse?
    suspend fun otpVerify(mobile: String,otp: String) : LoginDataResponse?
}