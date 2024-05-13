package com.vxplore.newjayadistributor.model

data class DashBoardDataResponse (
    val status: Boolean,
    val message: String,
    val data: DashBoardData
)

data class DashBoardData (
    val order_received: DueDelivery,
    val my_stock: DueDelivery,
    val due_delivery: DueDelivery,
    val track_order: DueDelivery
)

data class DueDelivery (
    val count: Long,
    val amount_string: String
)