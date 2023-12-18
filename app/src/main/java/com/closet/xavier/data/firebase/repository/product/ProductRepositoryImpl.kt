package com.closet.xavier.data.firebase.repository.product

import com.closet.xavier.data.firebase.model.base.Resource
import com.closet.xavier.data.firebase.model.product.Product
import com.closet.xavier.utils.Constants
import com.closet.xavier.utils.await
import com.google.firebase.FirebaseException
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore) :
    ProductRepository {

    private val collectionRef = firestore.collection(Constants.PRODUCTS_COLLECTION)
    override suspend fun getProducts(): Resource<List<Product>> = withContext(
        Dispatchers.IO
    ) {
        return@withContext try {
            val snapshot =
                collectionRef.orderBy(Constants.NAME_PROPERTY, Query.Direction.ASCENDING).get()
                    .await().map { document ->
                        document.toObject(Product::class.java)

                    }
            Resource.Success(data = snapshot)
        } catch (e: FirebaseException) {
            Resource.Error(errorResponse = e.localizedMessage)
        }
    }

    override suspend fun getProductById(productId: String): Resource<Product?> =
        withContext(
            Dispatchers.IO
        ) {
            return@withContext try {
                val documentSnapshot = collectionRef.document(productId).get().await()
                if (documentSnapshot.exists()) {
                    val result = documentSnapshot.toObject(Product::class.java)
                    Resource.Success(result)
                } else {
                    Resource.Error(errorResponse = "Brand doesn't exist!")
                }
            } catch (e: FirebaseException) {
                Resource.Error(errorResponse = e.localizedMessage)
            }
        }

    override suspend fun addProductToFavourite(
        productId: String,
        userId: String
    ): Resource<Boolean> = withContext(
        Dispatchers.IO
    ) {
        return@withContext try {
            val productSnapshot = collectionRef.document(productId).get().await()
            if (productSnapshot.exists()) {
                val favoritesList = productSnapshot.get("favorites") as? MutableList<String>
                if (favoritesList != null) {
                    if (favoritesList.contains(userId)) {
                        favoritesList.remove(userId)
                    } else {
                        favoritesList.add(userId)
                    }
                    // Update the favorites field in the product document
                    collectionRef.document(productId).update("favorites", favoritesList).await()
                }
            }
            Resource.Success(true)
        } catch (e: FirebaseException) {
            Resource.Error(errorResponse = e.localizedMessage)
        }

    }

    override suspend fun searchCollectionsByName(name: String): List<DocumentSnapshot> =
        withContext(
            Dispatchers.IO
        ) {
            val brandQuery = firestore
                .collection(Constants.BRANDS_COLLECTION)
                .whereEqualTo("name", name)
                .get()
                .await()

            val productQuery = firestore
                .collection(Constants.PRODUCTS_COLLECTION)
                .whereEqualTo("name", name)
                .get()
                .await()

            val brandResults = brandQuery.documents
            val productResults = productQuery.documents

            return@withContext brandResults + productResults
        }
}