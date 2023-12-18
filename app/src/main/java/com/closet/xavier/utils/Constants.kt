package com.closet.xavier.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {

    const val GOOGLE_TOKEN_ID =
        "940958822463-oe3r94mq9gl5l1qkarrs4svdg9o65l38.apps.googleusercontent.com"
    const val PASSWORD_TEXT_LENGTH = 8
    const val USER_PREFERENCES = "user_preferences"
    val IS_FIRST_TIME_USER = booleanPreferencesKey("first_timer")
    val LOGGED_IN_USER_UID = stringPreferencesKey("user_uid")

    const val FIREBASE_USER_COLLECTION = "users"
    const val BRANDS_COLLECTION = "brands"

    const val PRODUCTS_COLLECTION = "products"
    const val NAME_PROPERTY = "name"

    const val PRODUCT_ID = "productId"

    const val MINIMIZED_MAX_LINES = 4
}