package com.kiq83.ktor.sample.account.persistence

import com.kiq83.ktor.sample.account.model.AccountId
import com.kiq83.ktor.sample.account.model.AccountProfile

interface AccountProfileDAO {

    fun putAccountProfile(accountId: AccountId, accountProfile: AccountProfile)

    fun getAccountProfile(accountId: AccountId): AccountProfile?
}