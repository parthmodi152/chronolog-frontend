package com.cs346.chronolog.data.repository

import com.cs346.chronolog.data.local.LocalAccountsDataProvider
import com.cs346.chronolog.data.model.Account
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class AccountsRepositoryImpl : AccountsRepository {
    override fun getUserAccount(): Flow<Account> = flow {
        emit(LocalAccountsDataProvider.UserAccount)
    }
}
