package com.cs346.chronolog.data.model

data class Account(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val email: String,
    val avatar: String, // picture dir
    var isCurrentAccount: Boolean = false
) {
    val fullName: String = "$firstName $lastName"
}
