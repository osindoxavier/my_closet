package com.closet.xavier.domain.use_cases.products

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.closet.xavier.data.firebase.repository.product.ProductRepository
import com.closet.xavier.data.firebase.repository.product.ProductRepositoryImpl
import com.closet.xavier.utils.Constants
import com.closet.xavier.utils.await
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchCollectionUseCaseTest {

    private lateinit var repository: ProductRepository
    private lateinit var searchCollectionUseCase: SearchCollectionUseCase
    private lateinit var firestore: FirebaseFirestore

    @Before
    fun setUp() {
        firestore = FirebaseFirestore.getInstance()
        repository = ProductRepositoryImpl(firestore = firestore)
        searchCollectionUseCase = SearchCollectionUseCase(repository = repository)

    }

    @Test
    fun testSearchCollectionsByName() = runBlocking {


        // Add test data to the "brand" collection
        firestore.collection(Constants.BRANDS_COLLECTION).document("brand1")
            .set(mapOf("name" to "Brand 1"))
            .await()

        // Add test data to the "product" collection
        firestore.collection(Constants.BRANDS_COLLECTION).document("product1")
            .set(mapOf("name" to "Product 1"))
            .await()

        val brandResults = searchCollectionUseCase("Brand 1")
        val productResults = searchCollectionUseCase("Product 1")


        // Assert that the results contain the expected documents
        assertTrue(brandResults.isNotEmpty())
        assertTrue(brandResults[0].id == "brand1")
        assertTrue(productResults.isNotEmpty())
        assertTrue(productResults[0].id == "product1")
    }
}