package com.kiq83.ktor.sample.account.service

import io.ktor.auth.Principal
import java.security.interfaces.RSAPublicKey
import java.time.Clock
import java.util.*
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.kiq83.ktor.sample.account.model.AccountId
import com.kiq83.ktor.sample.account.model.AuthToken
import java.security.interfaces.RSAPrivateKey
import com.auth0.jwt.exceptions.JWTCreationException

class AuthenticationTokenService(
    publicKey: RSAPublicKey,
    privateKey: RSAPrivateKey,
    private val clock: Clock
) {
    private val jwtAlgorithm = Algorithm.RSA256(publicKey, privateKey)
    val jwtVerifier =
        (JWT.require(jwtAlgorithm) as JWTVerifier.BaseVerification)
            .build { Date(clock.millis()) }

    fun generateAuthToken(accountId: AccountId): AuthToken {
        try {
            return JWT.create()
                .withIssuer("kiq83")
                .withSubject(accountId.toString())
                .withExpiresAt(Date.from(clock.instant().plusSeconds(3600L)))
                .sign(jwtAlgorithm)
        } catch (e: JWTCreationException) {
            //Invalid Signing configuration / Couldn't convert Claims.
            throw e
        }
    }
}

data class CustomPrincipal(val accountId: AccountId) : Principal