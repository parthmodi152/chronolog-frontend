package com.cs346.chronolog.data.model

data class Note(
    val id: Long,
    val author: Account,
    val subject: String,
    val body: String,
    val category: Category,
    var isStarred: Boolean = false,
    val createdAt: String
)
