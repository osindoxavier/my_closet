package com.closet.xavier.data.firebase.repository.brand

import com.closet.xavier.data.firebase.model.base.Resource
import com.closet.xavier.data.firebase.model.brand.Brand
import com.closet.xavier.utils.Constants
import com.closet.xavier.utils.await
import com.google.firebase.FirebaseException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BrandRepositoryImpl @Inject constructor(firestore: FirebaseFirestore) : BrandRepository {

    private val collectionRef = firestore.collection(Constants.BRANDS_COLLECTION)
    override suspend fun getBrands(): Resource<List<Brand>> = withContext(
        Dispatchers.IO
    ) {
        return@withContext try {
            val snapshot =
                collectionRef.orderBy(Constants.NAME_PROPERTY, Query.Direction.ASCENDING).get()
                    .await().map { document ->
                        document.toObject(Brand::class.java)

                    }
            Resource.Success(data = snapshot)
        } catch (e: FirebaseException) {
            Resource.Error(errorResponse = e.localizedMessage)
        }
    }

    override suspend fun getBrandById(brandId: String): Resource<Brand?> =
        withContext(
            Dispatchers.IO
        ) {
            return@withContext try {
                val documentSnapshot = collectionRef.document(brandId).get().await()
                if (documentSnapshot.exists()) {
                    val result = documentSnapshot.toObject(Brand::class.java)
                    Resource.Success(result)
                } else {
                    Resource.Error(errorResponse = "Brand doesn't exist!")
                }
            } catch (e: FirebaseException) {
                Resource.Error(errorResponse = e.localizedMessage)
            }
        }
}
