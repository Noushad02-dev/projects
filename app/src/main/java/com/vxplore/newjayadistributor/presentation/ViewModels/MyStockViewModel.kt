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
import com.vxplore.newjayadistributor.MyDataIds
import com.vxplore.newjayadistributor.Routes
import com.vxplore.newjayadistributor.model.CategoriesDataResponse
import com.vxplore.newjayadistributor.model.Datum
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
    private val productList = mutableStateListOf<Datum>()
    private val indexRouteId = mutableStateOf(0)
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
                navigation {
                    navigate(Routes.home.full)
                }
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
                        //productList()
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
}