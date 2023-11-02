package com.cs346.chronolog.data.repository

import com.cs346.chronolog.data.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoriesRepository {
    fun getAllCategories(): Flow<List<Category>>
    fun getCategoryById(id: Long): Flow<Category>
}
