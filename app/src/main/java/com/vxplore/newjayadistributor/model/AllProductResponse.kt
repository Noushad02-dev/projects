package com.vxplore.newjayadistributor.model

data class AllProductResponse (
    val user_id: String,
    val store_id: String,
    val products: List<AllProducts>
)

data class AllProducts (
    val id: String,
    val quantity: Int,
    val unit: String
)

