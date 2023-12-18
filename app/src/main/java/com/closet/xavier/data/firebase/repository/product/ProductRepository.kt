package com.closet.xavier.data.firebase.repository.product

import com.closet.xavier.data.firebase.model.base.Resource
import com.closet.xavier.data.firebase.model.product.Product
import com.google.firebase.firestore.DocumentSnapshot

interface ProductRepository {
    suspend fun getProducts():Resource<List<Product>>
    suspend fun getProductById(productId:String):Resource<Product?>
    suspend fun addProductToFavourite(productId:String,userId:String):Resource<Boolean>

    suspend fun searchCollectionsByName(name: String): List<DocumentSnapshot>
}