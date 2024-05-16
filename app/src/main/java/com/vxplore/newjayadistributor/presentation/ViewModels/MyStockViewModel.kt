package com.vxplore.newjayadistributor.presentation.ViewModels

import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.debduttapanda.j3lib.InterCom
import com.debduttapanda.j3lib.SoftInputMode
import com.debduttapanda.j3lib.WirelessViewModel
import com.debduttapanda.j3lib.models.EventBusDescription
import com.debduttapanda.j3lib.models.Route
import com.vxplore.newjayadistributor.App
import com.vxplore.newjayadistributor.MyDataIds
import com.vxplore.newjayadistributor.Routes
import com.vxplore.newjayadistributor.model.AllProducts
import com.vxplore.newjayadistributor.model.CategoriesDataResponse
import com.vxplore.newjayadistributor.model.Datum
import com.vxplore.newjayadistributor.model.MyStockAllProducts
import com.vxplore.newjayadistributor.model.MyStockDatum
import com.vxplore.newjayadistributor.repository.ApiInterface
import com.vxplore.newjayadistributor.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MyStockViewModel @Inject constructor(
    private val repo: Repository
):WirelessViewModel(){
    private val selectedCategoryTabId = mutableStateOf("")
    private val productQty = mutableStateOf("")
    private val loadingState = mutableStateOf(false)
    private val selectedBrandTabId = mutableStateOf(0)
    private val selectedText = mutableStateOf("CB")
    private val searchProductQuery = mutableStateOf("")
    private val categories = mutableStateListOf<CategoriesDataResponse.CategoriesData>()
    private val password = mutableStateOf("")
    private val categoryId = mutableStateOf("")
    private val productList = mutableStateListOf<MyStockDatum>()
    private val indexRouteId = mutableStateOf(0)
    private val userId = mutableStateOf("")
    override fun eventBusDescription(): EventBusDescription? {
        return null
    }

    override fun interCom(message: InterCom) {
    }

    override fun onBack() {
    }

    override fun onNotification(id: Any?, arg: Any?) {
        when(id){
            MyDataIds.back->{
                popBackStack()
            }
            MyDataIds.productQty->{
                productQty.value = arg as String
            }
            MyDataIds.stockUpdate->{
                updateProduct()
            }
            MyDataIds.brandChange -> {
                val brandIndex = arg as Int
                val selectedCategoryUid = if (brandIndex in categories.indices) {
                    selectedBrandTabId.value = brandIndex
                    categories[brandIndex].uid
                } else {
                    // Handle index out of bounds or invalid index case
                    ""
                }
                repo.setCategory(selectedCategoryUid)
                Log.d("BrandChange", "Selected Brand ID: $selectedCategoryUid")
                val cat = repo.getCategory()
                Log.d("BrandChange", "Selected Brand ID: $cat")
                onBrandChange()
                productList()
            }
        }
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }
    init {
        mapData(
            MyDataIds.selectedCategoryId to selectedCategoryTabId,
            MyDataIds.productQty to productQty,
            MyDataIds.loadingState to loadingState,

            MyDataIds.selectedCategoryId to selectedCategoryTabId,
            MyDataIds.productQty to productQty,
            MyDataIds.productSearch to searchProductQuery,
            MyDataIds.categoryList to categories,
            MyDataIds.productList to productList,
            MyDataIds.brandChange to selectedBrandTabId,
        )
        setSoftInputMode(SoftInputMode.adjustPan)
        categoryList()
        productList()
    }

    private fun categoryList() {
        //loadingState.value = true
        password.value = repo.getPassCode()!!
        viewModelScope.launch {
            try {
                val response = repo.categories(password.value)
                withContext(Dispatchers.Main) {
                    categories.clear()
                    if (!categories.any { it.uid == CategoriesDataResponse.All.uid }) {
                        categories.add(CategoriesDataResponse.All)
                    }
                    if (response != null) {
                        categories.addAll(response.data)
                        val selectedCategoryId = selectedCategoryTabId.value
                        val matchingCategory = response.data.find { it.uid == selectedCategoryId }
                        val categoryId = matchingCategory?.uid ?: ""
                        repo.setCategory(categoryId)
                        Log.d("dcdscx", categoryId)
                        productList()
                    }
                }
            } catch (e: Exception) {
                // Todo
            }
        }
    }


    private fun onBrandChange() {
        val selectedBrandIndex = selectedBrandTabId.value
        if (selectedBrandIndex in categories.indices) {
            val selectedBrand = categories[selectedBrandIndex]
            val productBrandId = selectedBrand.uid ?: ""
            categoryId.value = productBrandId
        }
    }

    private fun productList(){
        loadingState.value = true
        userId.value = repo.getUserId()!!
        password.value = repo.getPassCode()!!
        Log.d("vhndf",password.value)
        categoryId.value = repo.getCategory()!!
        Log.d("vbgfb",categoryId.value)
        viewModelScope.launch {
            val response=
                repo.myStockList(
                    userId.value,
                    categoryId.value,
                    password.value
                )
            //val response = repo.fetchProduct(categoryId.value,searchProductQuery.value,password.value)
            if (response?.status == true){
                productList.clear()
                productList.addAll(response.data)
                loadingState.value = false

            }
        }
    }
    private fun updateProduct() {
        loadingState.value = true
        viewModelScope.launch(Dispatchers.IO) {
            userId.value = repo.getUserId() ?: ""
            password.value = repo.getPassCode() ?: ""
            try {
                val productsInCart = App.cart.get().filter { (_, quantity) ->
                    quantity > 0
                }.map { (productId, quantity) ->
                    MyStockAllProducts(productId, quantity)
                }

                if (productsInCart.isNotEmpty()) {
                    val viewCartRequest =
                        ApiInterface.UpdateProductRequest(userId.value, password.value,productsInCart)

                    val response =
                        repo.updateProduct(userId.value, password.value, viewCartRequest.products)

                    Log.d("bhcxbh", response.toString())

                    if (response?.status == true) {
                        toast(response.message)
                        navigation {
                            navigate(Routes.home.full)
                        }
                    }
                } else {
                    toast("Please select at least one item to add to the cart")
                    Log.d(
                        "bhcxbh",
                        "No products with quantity greater than 0 to send to the server"
                    )
                }
            } catch (e: Exception) {
                //handleNoConnectivity()
                Log.d("fgffg", "${e.message}")
            } finally {
                loadingState.value = false
            }
        }
    }
}