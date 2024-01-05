package com.closet.xavier.domain.use_cases.products

import android.util.Log
import com.closet.xavier.data.firebase.model.base.Resource
import com.closet.xavier.data.firebase.model.product.Product
import com.closet.xavier.data.firebase.repository.product.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(private val repository: ProductRepository) {
    companion object {
        private const val TAG = "GetProductById"
    }

    operator fun invoke(productId: String): Flow<Resource<Product?>> =
        repository.getProductById(productId = productId)
}