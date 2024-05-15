package com.vxplore.newjayadistributor.model
data class OrderDetailsDataResponse (
    val status: Boolean,
    val message: String,
    val data: List<OrderDetailsDatum>
)

data class OrderDetailsDatum (
    val order_id: String,
    val order_amount_string: String,
    val taxable_amount_string: String,
    val taxes_amount_string: String,
    val discount_amount_string: String,
    val total_amount_string: String,
    val store_name: String,
    val route: String,
    val count_order_item: Long,
    val order_date: String,
    val ordered_products: List<OrderedProduct>
)

data class OrderedProduct (
    val product_id: String,
    val price_string: String,
    val quantity: Long,
    val unit: String,
    val product: OrderDetailsProduct,
    val sub_total_amount_string: String
)

data class OrderDetailsProduct (
    val code: String,
    val name: String,
    val weight_string: String
)