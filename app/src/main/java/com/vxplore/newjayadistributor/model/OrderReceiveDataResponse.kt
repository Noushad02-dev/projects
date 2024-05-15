package com.vxplore.newjayadistributor.model

data class OrderReceiveDataResponse (
    val status: Boolean,
    val message: String,
    val data: List<OrderReceiveDatum>
)

data class OrderReceiveDatum (
    val order_id: String,
    val order_amount_string: String,
    val store_name: String,
    val route: String,
    val count_order_item: Long,
    val order_date: String
)