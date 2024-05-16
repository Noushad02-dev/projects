package com.vxplore.newjayadistributor.model

data class MyStockAllProductResponse (
    val user_id: String,
    val password: String,
    val products: List<MyStockAllProducts>
)

data class MyStockAllProducts (
    val id: String,
    val quantity: Int,
)