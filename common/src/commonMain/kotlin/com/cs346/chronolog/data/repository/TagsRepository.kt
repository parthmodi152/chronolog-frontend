package com.cs346.chronolog.data.repository

import com.cs346.chronolog.data.model.Tag
import kotlinx.coroutines.flow.Flow

interface TagsRepository {
    fun getAllTags(): Flow<List<Tag>>
    fun getTagById(id: Long): Flow<Tag>
}
