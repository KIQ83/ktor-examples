package com.kiq83.ktor.sample.account

import com.kiq83.ktor.sample.account.Configuration.accountProfileDAO
import com.kiq83.ktor.sample.account.Configuration.authenticationService
import com.kiq83.ktor.sample.account.Configuration.authenticationTokenService
import com.kiq83.ktor.sample.account.Configuration.httpPort

fun main() {
    println("Initiating Account Server")

    SampleAccountServer(
        httpPort = httpPort,
        tokenService = authenticationTokenService,
        authenticationService = authenticationService,
        accountProfileDAO = accountProfileDAO
    ).start()
}