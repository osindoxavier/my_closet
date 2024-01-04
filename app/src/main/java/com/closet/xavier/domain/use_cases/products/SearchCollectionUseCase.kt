package com.closet.xavier.domain.use_cases.products

import com.closet.xavier.data.firebase.repository.product.ProductRepository
import com.google.firebase.firestore.DocumentSnapshot
import javax.inject.Inject

class SearchCollectionUseCase @Inject constructor(private val repository: ProductRepository) {
    suspend operator fun invoke(name: String): List<DocumentSnapshot> {
        return repository.searchCollectionsByName(name = name)
    }
}