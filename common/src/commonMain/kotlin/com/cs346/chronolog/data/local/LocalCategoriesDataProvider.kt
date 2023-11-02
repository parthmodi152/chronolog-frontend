package com.cs346.chronolog.data.local

import com.cs346.chronolog.data.model.Category

object LocalCategoriesDataProvider {
    val allCategory = listOf(
        Category(
            id = 0L,
            name = "Overview",
            sum = 1
        )
    )

    fun getCategoryById(id: Long): Category {
        return allCategory.first { it.id == id }
    }
}
