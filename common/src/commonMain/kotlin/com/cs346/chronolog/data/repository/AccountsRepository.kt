package com.cs346.chronolog.data.repository

import com.cs346.chronolog.data.model.Account
import kotlinx.coroutines.flow.Flow

interface AccountsRepository {
    fun getUserAccount(): Flow<Account>
    fun getAllContact(): Flow<List<Account>>
    fun getContactAccountById(uid: Long): Flow<Account>
}
