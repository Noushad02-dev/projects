package com.vxplore.newjayadistributor.model

data class LoginDataResponse (
    val status: Boolean,
    val message: String,
    val email: String,
    val name: String,
    val user_id: String,
    val password: String,
)