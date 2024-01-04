package com.closet.xavier.data.firebase.repository.product

import com.closet.xavier.data.firebase.model.base.Resource
import com.closet.xavier.data.firebase.model.product.Product
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(): Flow<Resource<List<Product>?>>
    fun getProductById(productId:String):Flow<Resource<Product?>>
    suspend fun toggleFavorite(productId: String, userId: String)
    suspend fun searchCollectionsByName(name: String): List<DocumentSnapshot>
}