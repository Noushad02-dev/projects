package com.vxplore.newjayadistributor.model
data class FetchLocationDataResponse (
    val status: Boolean,
    val message: String,
    val data: List<LocationDatum>
)

data class LocationDatum (
    val id: String,
    val name: String,
    val pincode: String,
    val state: String
)