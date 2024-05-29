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
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {
    @FormUrlEncoded
    @POST("distributor/login")
    suspend fun Login(
        @Field("email") email : String,
        @Field("password") password : String,
    ): Response<LoginDataResponse>
    @FormUrlEncoded
    @POST("distributor/forgot_password")
    suspend fun getOtp(
        @Field("email") email : String,
    ): Response<GetOtpResponse>
    @FormUrlEncoded
    @POST("distributor/reset_password")
    suspend fun resetPassword(
        @Field("email") recoverUsername: String,
        @Field("otp") otp: String,
        @Field("password") password: String,
    ): Response<ResetDataResponse>
    @GET("distributor/dashboardData")
    suspend fun dashBoard(
        @Query("user_id") user_id : String,
        @Query("password") password : String,
    ): Response<DashBoardDataResponse>
    @GET("distributor/productCategories")
    suspend fun categories(
        @Query("password") password : String,
    ): Response<CategoriesDataResponse>
    @FormUrlEncoded
    @POST("distributor/productList")
    suspend fun fetchProduct(
        @Field("category_id") category_id: String,
        @Field("search_text") search_text: String,
        @Field("password") password: String,
    ): Response<ProductDataResponse>
    @POST("distributor/addProductsToCart")
    suspend fun viewCart(
        @Body request: ViewCartRequest
    ): Response<ViewCartDataResponse>
    data class ViewCartRequest(
        @Field("user_id") val user_id: String,
        @Field("password") val password: String,
        @Field("products") val products: List<AllProducts>
    )
    @GET("distributor/showCartProducts")
    suspend fun showCart(
        @Query("user_id") user_id : String,
        @Query("password") password : String,
    ): Response<ShowCartDataResponse>
    @FormUrlEncoded
    @POST("distributor/remove_cart_product")
    suspend fun remove(
        @Field("user_id") user_id : String,
        @Field("cart_id") cart_id : String,
    ): Response<ResetDataResponse>
    @GET("distributor/fetchSaveOrderLocations")
    suspend fun fetchLocation(
        @Query("user_id") user_id : String,
        @Query("password") password : String,
    ): Response<FetchLocationDataResponse>
    @FormUrlEncoded
    @POST("distributor/orderDropLocations")
    suspend fun searchLocation(
        @Field("user_id") user_id: String,
        @Field("search_text") search_text: String,
        @Field("password") password: String,
    ): Response<LocationDataResponse>
    @FormUrlEncoded
    @POST("distributor/saveOrderLocation")
    suspend fun saveLocation(
        @Field("user_id") user_id: String,
        @Field("location_id") location_id: String,
        @Field("password") password: String,
    ): Response<ResetDataResponse>
    @FormUrlEncoded
    @POST("distributor/placeOrder")
    suspend fun placeOrder(
        @Field("user_id") user_id: String,
        @Field("location_id") location_id: String,
        @Field("password") password: String,
    ): Response<ResetDataResponse>
    @GET("distributor/receivedOrderList")
    suspend fun orderReceive(
        @Query("user_id") user_id : String,
        @Query("password") password : String,
    ): Response<OrderReceiveDataResponse>
    @GET("distributor/dueDelivery")
    suspend fun dueDelivery(
        @Query("user_id") user_id : String,
        @Query("password") password : String,
    ): Response<DueOrderDataResponse>

    @GET("distributor/orderDetails")
    suspend fun orderDetails(
        @Query("user_id") user_id : String,
        @Query("order_id") order_id: String,
        @Query("password") password : String,
    ): Response<OrderDetailsDataResponse>
    @FormUrlEncoded
    @POST("distributor/confirmDispatch")
    suspend fun confirmDispatch(
        @Field("password") password: String,
        @Field("order_id") order_id: String,
    ): Response<ResetDataResponse>
    @FormUrlEncoded
    @POST("distributor/confirmOrder")
    suspend fun confirmOrder(
        @Field("password") password: String,
        @Field("order_id") order_id: String,
    ): Response<ResetDataResponse>

    @GET("distributor/trackOrders")
    suspend fun trackOrder(
        @Query("user_id") user_id : String,
        @Query("password") password : String,
    ): Response<TrackOrderDataResponse>
    @GET("distributor/trackOrderDetails")
    suspend fun orderStatus(
        @Query("user_id") user_id : String,
        @Query("order_id") order_id: String,
        @Query("password") password : String,
    ): Response<OrderStatusDataResponse>

    @FormUrlEncoded
    @POST("distributor/removeOrderProduct")
    suspend fun removeOrderProduct(
        @Field("user_id") user_id: String,
        @Field("order_id") order_id: String,
        @Field("product_id") product_id: String,
        @Field("password") password: String,
    ): Response<ResetDataResponse>

    @FormUrlEncoded
    @POST("distributor/updateOrderProduct")
    suspend fun updateProduct(
        @Field("user_id") user_id: String,
        @Field("order_id") order_id: String,
        @Field("product_id") product_id: String,
        @Field("password") password: String,
        @Field("quantity") quantity: String,
    ): Response<ResetDataResponse>
    @FormUrlEncoded
    @POST("distributor/listAddMoreProduct")
    suspend fun addProductList(
        @Field("user_id") user_id: String,
        @Field("order_id") order_id: String,
        @Field("password") password: String,
        @Field("search_text") search_text: String,
    ): Response<AddProductListDataResponse>
    @FormUrlEncoded
    @POST("distributor/addMoreProduct")
    suspend fun addProduct(
        @Field("user_id") user_id: String,
        @Field("order_id") order_id: String,
        @Field("password") password: String,
        @Field("product_id") product_id: String,
        @Field("unit") unit: String,
        @Field("quantity") quantity: String,
        @Field("price_per_unit_string") price_per_unit_string: String,
    ): Response<ResetDataResponse>

    @GET("distributor/dispatchOrderList")
    suspend fun orderHistory(
        @Query("user_id") user_id : String,
        @Query("password") password : String,
    ): Response<OrderHistoryDataResponse>
    @GET("distributor/myStock")
    suspend fun myStockList(
        @Query("user_id") user_id : String,
        @Query("category_id") order_id: String,
        @Query("password") password : String,
    ): Response<MyStockListDataResponse>
    @POST("distributor/updateStock")
    suspend fun updateProduct(
        @Body request: UpdateProductRequest
    ): Response<ViewCartDataResponse>
    data class UpdateProductRequest(
        @Field("user_id") val user_id: String,
        @Field("password") val password: String,
        @Field("products") val products: List<MyStockAllProducts>
    )
    @GET("distributor/downloadOrderDetails")
    suspend fun pdf(
        @Query("user_id") user_id: String,
        @Query("order_id") order_id : String,
        @Query("password") password : String,
    ): Response<PDFDataResponse>

    @GET("distributor/send_otp")
    suspend fun mobileVerify(
        @Query("mobile") mobile: String,
    ): Response<ResetDataResponse>
    @GET("distributor/verify_otp")
    suspend fun otpVerify(
        @Query("mobile") mobile: String,
        @Query("otp") otp: String,
    ): Response<LoginDataResponse>
}