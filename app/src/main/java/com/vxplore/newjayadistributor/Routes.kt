package com.vxplore.newjayadistributor

import com.debduttapanda.j3lib.models.Route

object Routes {
    val splash = Route("splash")
    val login = Route("login")
    val home = Route("home")
    val orderReceive = Route("orderReceive")
    val orderDetails = Route("orderDetails")
    val trackOrder = Route("trackOrder")
    val orderStatus = Route("orderStatus")
    val dueDelivery = Route("dueDelivery")
    val dueDeliveryDetails = Route("dueDeliveryDetails")
    val myStock = Route("myStock")
}