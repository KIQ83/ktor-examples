package com.kiq83.ktor.sample.account.persistence

import com.kiq83.ktor.sample.account.model.AccountId
import com.kiq83.ktor.sample.account.model.AccountProfile

class InMemoryAccountProfileDAO() : AccountProfileDAO {

    private val profileMap = mutableMapOf<AccountId, AccountProfile>()

    override fun putAccountProfile(accountId: AccountId, accountProfile: AccountProfile) {
        profileMap[accountId] = accountProfile
    }

    override fun getAccountProfile(accountId: AccountId): AccountProfile? {
        return profileMap[accountId]
    }

}