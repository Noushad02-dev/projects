package com.vxplore.newjayadistributor.model
data class ProductDataResponse (
    val status: Boolean,
    val message: String,
    val data: List<Datum>
)

data class Datum (
    val product_id: String,
    val code: String,
    val name: String,
    val info: String,
    val unit: List<Unit>,
    val image: String
)

data class Unit (
    val id: String,
    val name: String
)

