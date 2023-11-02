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
            altEmail = "dhruv3056@gmail.com",
            avatar = "avatar_0",
            isCurrentAccount = true
        )
    val allUserContactAccounts = listOf(
        UserAccount,
        Account(
            id = 2L,
            firstName = "Parth",
            lastName = "Modi",
            phoneNumber = "+16470357258",
            email = "pmodi@uwaterloo.ca",
            altEmail = "pmodi@uwaterloo.ca",
            avatar = "avatar_1"
        ),
        Account(
            id = 3L,
            firstName = "Sean",
            lastName = "Devine",
            phoneNumber = "+17289312618",
            email = "sean.devine@uwaterloo.ca",
            altEmail = "sean.devine@uwaterloo.ca",
            avatar = "avatar_2"
        ),
        Account(
            id = 4L,
            firstName = "Ahmed",
            lastName = "Mushtaha",
            phoneNumber = "+8613018283868",
            email = "amushtaha@uwaterloo.ca",
            altEmail = "amushtaha@uwaterloo.ca",
            avatar = "avatar_3"
        )
    )

    fun getContactAccountById(accountId: Long): Account {
        return allUserContactAccounts.first { it.id == accountId }
    }
}
