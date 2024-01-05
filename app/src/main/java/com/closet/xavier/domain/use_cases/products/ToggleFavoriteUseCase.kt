package com.closet.xavier.domain.use_cases.products

import com.closet.xavier.data.firebase.model.base.Resource
import com.closet.xavier.data.firebase.repository.product.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(private val repository: ProductRepository) {
    operator fun invoke(productId: String, currentUserId: String): Flow<Resource<Boolean>> = flow {
        try {
            repository.toggleFavorite(productId, currentUserId)
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error(errorResponse = e.localizedMessage))
        }
    }
}