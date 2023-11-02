package com.cs346.chronolog.data.repository

import com.cs346.chronolog.data.local.LocalCategoriesDataProvider
import com.cs346.chronolog.data.model.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CategoriesRepositoryImpl : CategoriesRepository {
    override fun getAllCategories(): Flow<List<Category>> = flow {
        emit(LocalCategoriesDataProvider.allCategory)
    }

    override fun getCategoryById(id: Long): Flow<Category> = flow {
        emit(LocalCategoriesDataProvider.getCategoryById(id))
    }
}
