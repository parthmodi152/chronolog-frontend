package com.cs346.chronolog.data.local

import com.cs346.chronolog.data.model.Account

object LocalAccountsDataProvider {
    val UserAccount =
        Account(
            id = 1L,
            firstName = "Dhruv",
            lastName = "Agarwal",
            phoneNumber = "+14379320284",
            email = "dhruv.agarwal@uwaterloo.ca",
            avatar = "avatar_0",
            isCurrentAccount = true
        )
}
