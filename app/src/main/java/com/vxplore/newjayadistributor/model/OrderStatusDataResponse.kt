package com.vxplore.newjayadistributor.model
data class OrderStatusDataResponse (
    val status: Boolean,
    val message: String,
    val data: List<OrderStatusDatum>
)

data class OrderStatusDatum (
    val order_id: String,
    val order_amount_string: String,
    val count_order_item: Long,
    val order_date: String,
    val track_order: List<OrderStatusTrackOrder>
)

data class OrderStatusTrackOrder (
    val name: String,
    val status: Boolean,
    val datetime: String? = null
)