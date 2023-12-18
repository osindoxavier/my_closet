package com.closet.xavier.domain.use_cases.products

import com.closet.xavier.data.firebase.model.base.Resource
import com.closet.xavier.data.firebase.repository.product.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddProductToFavouriteUseCase @Inject constructor(private val repository: ProductRepository) {
    operator fun invoke(productId:String,userId:String): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        try {
            val response = repository.addProductToFavourite(productId = productId, userId = userId)
            emit(response)
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(errorResponse = e.localizedMessage))
        }
    }
}