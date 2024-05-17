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
import com.vxplore.newjayadistributor.repository.ApiInterface
import com.vxplore.newjayadistributor.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PlaceOrderViewModel @Inject constructor(
    private val repo: Repository
): WirelessViewModel() {
    private val selectedCategoryTabId = mutableStateOf("")
    private val selectedBrandTabId = mutableStateOf(0)
    private val productQty = mutableStateOf("")
    private val loadingState = mutableStateOf(false)
    private val selectedText = mutableStateOf("unit_cb")
    private val searchProductQuery = mutableStateOf("")
    private val categories = mutableStateListOf<CategoriesDataResponse.CategoriesData>()
    private val password = mutableStateOf("")
    private val categoryId = mutableStateOf("")
    private val productList = mutableStateListOf<Datum>()
    private val indexRouteId = mutableStateOf(0)
    private val userId = mutableStateOf("")
    private val lostInternet = mutableStateOf(false)


    fun setSelectedText(text: String, id: String) {
        selectedText.value = id
        Log.d("hcvf",selectedText.value)
    }
    fun setSelectedCategory(id: String) {
        selectedCategoryTabId.value = id
        repo.setCategory(selectedCategoryTabId.value)
        Log.d("hcvf", selectedCategoryTabId.value)
    }


    override fun eventBusDescription(): EventBusDescription? {
        return null
    }

    override fun interCom(message: InterCom) {}

    override fun onBack() {}

    override fun onNotification(id: Any?, arg: Any?) {
        when(id) {
            MyDataIds.back -> {
                popBackStack()
            }
            MyDataIds.productQty -> {
                productQty.value = arg as String
            }
            MyDataIds.viewCart -> {
                viewCart()
            }
            MyDataIds.productSearch -> {
                searchProductQuery.value = arg as String
                productList()
            }
            MyDataIds.categoryChange -> {
                selectedCategoryTabId.value = arg as String
                //onCategoryChange()
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

            MyDataIds.tryagain -> {
                lostInternet.value = false
                categoryList()
                productList()
            }

        }
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {}

    init {
        mapData(
            MyDataIds.selectedCategoryId to selectedCategoryTabId,
            MyDataIds.productQty to productQty,
            MyDataIds.loadingState to loadingState,
            MyDataIds.productSearch to searchProductQuery,
            MyDataIds.categoryList to categories,
            MyDataIds.productList to productList,
            MyDataIds.brandChange to selectedBrandTabId,
            MyDataIds.lostInternet to lostInternet,
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
                handleNoConnectivity()
            }
        }
    }

    private fun productList(){
        loadingState.value = true
        password.value = repo.getPassCode()!!
        Log.d("vhndf",password.value)
        categoryId.value = repo.getCategory()!!
        Log.d("vbgfb",categoryId.value)
        viewModelScope.launch {
            try {
                val response=
                    repo.fetchProduct(
                        categoryId.value,
                        searchProductQuery.value,
                        password.value
                    )
                //val response = repo.fetchProduct(categoryId.value,searchProductQuery.value,password.value)
                if (response?.status == true){
                    productList.clear()
                    productList.addAll(response.data)
                    loadingState.value = false

                }
            }catch (e: Exception) {
                handleNoConnectivity()
                Log.d("fgffg", "${e.message}")
            } finally {
                loadingState.value = false
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
    private fun onCategoryChange() {
        Log.d("SelectedCategoryId", selectedCategoryTabId.value)
        filterProducts()
    }

    private fun filterProducts() {
        productList.clear()
        productList.addAll(productList.filter { productFilter(it) })
    }

    private fun productFilter(p: Datum): Boolean {
        if (selectedBrandTabId.value < 0) {
            return false
        }
        val currentBrand = categories.getOrNull(selectedBrandTabId.value)

        val brandWiseAllowance =
            (currentBrand == CategoriesDataResponse.All) || (p.product_id != currentBrand?.uid)
        if (!brandWiseAllowance) {
            return false
        }



        val queryLowerCase = searchProductQuery.value.toLowerCase()

        if (queryLowerCase.isBlank()) {
            return true
        }

        val productAttributes =
            listOf(p.product_id, p.name, p.code, p.info, p.unit,p.image)
        return productAttributes.any { attribute ->
            attribute.toString().toLowerCase().contains(queryLowerCase)
        }
    }

    private fun viewCart() {
        loadingState.value = true
        viewModelScope.launch(Dispatchers.IO) {
            userId.value = repo.getUserId() ?: ""
            password.value = repo.getPassCode() ?: ""
            try {
                val productsInCart = App.cart.get().filter { (_, quantity) ->
                    quantity > 0
                }.map { (productId, quantity) ->
                    AllProducts(productId, quantity, selectedText.value)
                }

                if (productsInCart.isNotEmpty()) {
                    val viewCartRequest =
                        ApiInterface.ViewCartRequest(userId.value, password.value, productsInCart)

                    val response =
                        repo.viewCart(userId.value, password.value, viewCartRequest.products)

                    Log.d("bhcxbh", response.toString())

                    if (response?.status == true) {
                        toast(response.message)
                        navigation {
                            navigate(Routes.viewCart.full)
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
                handleNoConnectivity()
                Log.d("fgffg", "${e.message}")
            } finally {
                loadingState.value = false
            }
        }
    }
    private suspend fun handleNoConnectivity() {
        withContext(Dispatchers.Main) {
            lostInternet.value = true
        }
    }

}
