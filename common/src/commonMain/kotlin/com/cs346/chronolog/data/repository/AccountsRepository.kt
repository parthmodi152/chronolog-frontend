package com.cs346.chronolog.data.repository

import com.cs346.chronolog.data.model.Account
import kotlinx.coroutines.flow.Flow

interface AccountsRepository {
    fun getUserAccount(): Flow<Account>
}
