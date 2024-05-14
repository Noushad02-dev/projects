package com.vxplore.newjayadistributor

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        AppContext.init(this)
    }

    companion object{
        val products: Products by lazy { Products() }
        val cart: Cart by lazy { Cart() }
    }
}
object AppContext {
    private lateinit var _app: App
    val app: App get() = _app
    fun init(app: App) {
        _app = app
    }
}


data class Product(
    val id: String,
)
class Products{
    private val products = mutableMapOf<String,Product>()
    fun saveProducts(products: List<Product>){
        this.products.clear()
        this.products.putAll(
            products.associateBy { it.id }
        )
    }
    fun addProduct(product: Product){
        products[product.id] = product
    }
    fun removeProduct(product: Product){
        products.remove(product.id)
    }
    fun getProducts(): List<Product>{
        return products.values.toList()
    }
}

class Cart {
    private val productQuantity = mutableMapOf<String, Int>()
    fun add(id: String, quantity: Int, unit: String) {
        // Check if there is an existing quantity for the product ID
        val existingQuantity = productQuantity[id] ?: 0

        // Prompt user to confirm or choose an action based on existing quantity
        if (existingQuantity > 0) {
            // Handle the case where there is an existing quantity
            // You may prompt the user to confirm whether they want to replace or add to the existing quantity
            // Alternatively, you can directly update the quantity based on user input
            // For simplicity, we'll just update the quantity to the new value provided by the user
            productQuantity[id] = quantity
        } else {
            // If there is no existing quantity, simply add the new quantity
            productQuantity[id] = quantity.coerceAtLeast(0)
        }

        // Alternatively, you can prompt the user to confirm or choose an action when removing a product from the cart
    }

    fun getQuantity(id: String): Int {
        return productQuantity[id] ?: 0
    }

    fun get(id: String): Int{
        return productQuantity[id]?:0
    }

    fun remove(id: String){
        productQuantity.remove(id)
    }

    fun get(): Map<String,Int>{
        return productQuantity
    }

    fun clear(){
        productQuantity.clear()
    }
}