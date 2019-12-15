package com.kiq83.ktor.sample.account

import com.kiq83.ktor.sample.account.persistence.InMemoryAccountProfileDAO
import com.kiq83.ktor.sample.account.service.AuthenticationService
import com.kiq83.ktor.sample.account.service.AuthenticationTokenService
import com.typesafe.config.ConfigFactory
import java.nio.file.Files
import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.X509EncodedKeySpec
import java.time.Clock
import java.security.spec.PKCS8EncodedKeySpec
import java.nio.file.Paths

object Configuration {

    val clock = Clock.systemUTC()!!

    private val rootConfig = ConfigFactory.load()

    private val serverConfig = rootConfig.getConfig("ktor-server")
    val httpPort = serverConfig.getInt("port")

    val authenticationTokenService = AuthenticationTokenService(
        retrieveRSAPublicKey(),
        retrieveRSAPrivateKey(),
        clock
    )

    val authenticationService = AuthenticationService(authenticationTokenService)

    val accountProfileDAO = InMemoryAccountProfileDAO()
}

// Key generation made following: https://stackoverflow.com/questions/11410770/load-rsa-public-key-from-file
private fun retrieveRSAPublicKey(): RSAPublicKey {
    val keyBytes = Files.readAllBytes(
        Paths.get("ktor-sample-account-handling/src/main/resources/public_key.der"))

    val spec = X509EncodedKeySpec(keyBytes)
    val kf = KeyFactory.getInstance("RSA")
    return kf.generatePublic(spec) as RSAPublicKey
}

private fun retrieveRSAPrivateKey(): RSAPrivateKey {
    val keyBytes = Files.readAllBytes(
        Paths.get("ktor-sample-account-handling/src/main/resources/private_key.der"))

    val spec = PKCS8EncodedKeySpec(keyBytes)
    val kf = KeyFactory.getInstance("RSA")
    return kf.generatePrivate(spec) as RSAPrivateKey
}