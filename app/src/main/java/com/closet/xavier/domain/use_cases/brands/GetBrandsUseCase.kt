package com.closet.xavier.domain.use_cases.brands

import com.closet.xavier.data.firebase.model.base.Resource
import com.closet.xavier.data.firebase.model.brand.Brand
import com.closet.xavier.data.firebase.repository.brand.BrandRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetBrandsUseCase @Inject constructor(private val repository: BrandRepository) {
    operator fun invoke(): Flow<Resource<List<Brand>>> = flow {
        emit(Resource.Loading())
        try {
            val brands = repository.getBrands()
            emit(brands)
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }
}