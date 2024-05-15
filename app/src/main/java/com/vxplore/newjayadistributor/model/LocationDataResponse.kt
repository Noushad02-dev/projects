package com.vxplore.newjayadistributor.model
data class LocationDataResponse (
    val status: Boolean,
    val message: String,
    val data: List<LocData>
)

data class LocData (
    val location_id: String,
    val name: String,
    val pincode: String,
    val state: String,
    val selected: Boolean
)