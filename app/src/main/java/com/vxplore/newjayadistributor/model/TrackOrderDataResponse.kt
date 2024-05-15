package com.vxplore.newjayadistributor.model

data class TrackOrderDataResponse (
    val status: Boolean,
    val message: String,
    val data: List<TrackOrderDatum>
)

data class TrackOrderDatum (
    val order_id: String,
    val order_amount_string: String,
    val count_order_item: Long,
    val order_date: String
)