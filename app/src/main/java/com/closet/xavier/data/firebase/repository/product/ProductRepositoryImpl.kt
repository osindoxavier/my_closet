package com.closet.xavier.data.firebase.repository.product

import android.util.Log
import com.closet.xavier.data.firebase.model.base.Resource
import com.closet.xavier.data.firebase.model.product.Product
import com.closet.xavier.utils.Constants
import com.closet.xavier.utils.await
import com.google.firebase.FirebaseException
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.log

class ProductRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore) :
    ProductRepository {

    companion object {
        private const val TAG = "ProductRepositoryImpl"
    }

    private val collectionRef = firestore.collection(Constants.PRODUCTS_COLLECTION)

    override fun getProducts(): Flow<Resource<List<Product>?>> = callbackFlow {
        val listenerRegistration =
            collectionRef.orderBy(Constants.NAME_PROPERTY, Query.Direction.ASCENDING)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        close(error)
                    } else {
                        // Ensure non-null list before emitting
                        val products =
                            snapshot?.documents?.mapNotNull { it.toObject(Product::class.java) }
                        trySend(Resource.Success(data = products))
                    }
                }

        awaitClose { listenerRegistration.remove() }
    }.flowOn(Dispatchers.IO)

    override fun getProductById(productId: String): Flow<Resource<Product?>> = callbackFlow {
        val listenerRegistration =
            collectionRef.document(productId).addSnapshotListener { snapshot, error ->
                if (error != null) {
                    error.printStackTrace()
                    Log.e(TAG, "getProductById: ${error.localizedMessage}", error)
                    trySend(Resource.Error(errorResponse = error.localizedMessage))
                    close(error)
                } else {
                    trySend(Resource.Loading())
                    if (snapshot?.exists() == true) {
                        val product = snapshot.toObject(Product::class.java)
                        trySend(Resource.Success(product))
                    }


                }

            }
        awaitClose { listenerRegistration.remove() }
    }.flowOn(Dispatchers.IO)

    override suspend fun toggleFavorite(productId: String, userId: String): Unit =
        withContext(Dispatchers.IO) {
            val documentSnapshot = collectionRef.document(productId)
            documentSnapshot.get().addOnSuccessListener { document ->
                val product = document.toObject(Product::class.java)
                if (product != null) {
                    val updatedFavorites = if (product.favorites?.contains(userId) == true) {
                        // Remove user ID
                        product.favorites.minus(userId)
                    } else {
                        // Add user ID
                        product.favorites.orEmpty().plus(userId)
                    }

                    collectionRef.document(productId).update("favorites", updatedFavorites)
                        .addOnSuccessListener {
                            // Handle successful update
                            Log.d(TAG, "toggleProductFavorite: updated")
                        }.addOnFailureListener { e ->
                            e.printStackTrace()
                            Log.e(TAG, "toggleFavorite: ${e.localizedMessage}", e)
                            // Handle error
                        }
                } else {
                    // Handle product not found
                }
            }.addOnFailureListener { e ->
                // Handle error
                e.printStackTrace()
                Log.e(TAG, "toggleFavorite: ${e.localizedMessage}", e)
            }
        }

    override suspend fun searchCollectionsByName(name: String): List<DocumentSnapshot> =
        withContext(
            Dispatchers.IO
        ) {
            val brandQuery =
                firestore.collection(Constants.BRANDS_COLLECTION).whereEqualTo("name", name).get()
                    .await()

            val productQuery =
                firestore.collection(Constants.PRODUCTS_COLLECTION).whereEqualTo("name", name).get()
                    .await()

            val brandResults = brandQuery.documents
            val productResults = productQuery.documents

            return@withContext brandResults + productResults
        }
}