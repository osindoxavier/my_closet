package com.closet.xavier.data.firebase.model.authentication

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class UserProfile(
    val uid: String="",
    val email: String="",
    val phone: String="",
    val firstName: String="",
    val middleName: String="",
    val lastName: String="",
    @ServerTimestamp
    val createAt: Date? = null
)
