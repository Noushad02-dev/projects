package com.vxplore.newjayadistributor.model

data class MyStockListDataResponse (
    val status: Boolean,
    val message: String,
    val data: List<MyStockDatum>
)

data class MyStockDatum (
    val product_id: String,
    val unit: String,
    val pcs_quantity: String,
    val product_name: String,
    val code: String,
    val info: String,
    val cb_quantity_string: String,
    val image: String
)