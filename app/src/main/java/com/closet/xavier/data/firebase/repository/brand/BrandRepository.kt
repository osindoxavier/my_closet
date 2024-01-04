package com.closet.xavier.data.firebase.repository.brand

import com.closet.xavier.data.firebase.model.base.Resource
import com.closet.xavier.data.firebase.model.brand.Brand

interface BrandRepository {

    suspend fun getBrands(): Resource<List<Brand>>
    suspend fun getBrandById(brandId: String): Resource<Brand?>
}