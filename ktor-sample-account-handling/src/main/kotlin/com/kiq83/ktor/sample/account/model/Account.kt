package com.kiq83.ktor.sample.account.model

import java.util.*

typealias AccountId = UUID
typealias AuthToken = String

data class AccountProfile(
    val firstName: String,
    val lastName: String,
    val age: Int
)