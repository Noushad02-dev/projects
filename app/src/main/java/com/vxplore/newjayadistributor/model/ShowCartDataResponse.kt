package com.vxplore.newjayadistributor.model

data class ShowCartDataResponse (
    val status: Boolean,
    val message: String,
    val cart_products: List<CartProduct>,
    val previous_outstanding_amount_string: String,
    val taxable_amount_string: String,
    val tax_amount_string: String,
    val discount_amount_string: String,
    val current_total_amount_string: String
)

data class CartProduct (
    val cart_id: String,
    val product_id: String,
    val quantity: Long,
    val unit: String,
    val product: Product,
    val amount_per_unit_string: String,
    val amount_string: String
)

data class Product (
    val code: String,
    val name: String,
    val info: String,
    val image: String
)