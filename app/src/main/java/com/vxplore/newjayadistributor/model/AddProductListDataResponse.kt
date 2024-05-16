package com.vxplore.newjayadistributor.model

data class AddProductListDataResponse (
    val status: Boolean,
    val message: String,
    val data: AddProductListData
)

data class AddProductListData (
    val product_id: String,
    val code: String,
    val name: String,
    val info: String,
    val unit: List<AddProductListUnit>,
    val image: String
)

data class AddProductListUnit (
    val id: String,
    val name: String,
    val amount_string_pcs: String? = null,
    val amount_string_cb: String? = null
)