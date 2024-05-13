package com.vxplore.newjayadistributor.model
data class CategoriesDataResponse (
    val status: Boolean,
    val message: String,
    val data: List<CategoriesData>
) {

    data class CategoriesData(
        val uid: String,
        val name: String,
    )

    companion object {
        val All = CategoriesData("", "All")
    }
}