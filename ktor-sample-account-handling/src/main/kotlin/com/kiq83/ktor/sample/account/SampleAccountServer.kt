package com.kiq83.ktor.sample.account

import com.kiq83.ktor.sample.account.persistence.AccountProfileDAO
import com.kiq83.ktor.sample.account.route.account
import com.kiq83.ktor.sample.account.route.auth
import com.kiq83.ktor.sample.account.service.AuthenticationService
import com.kiq83.ktor.sample.account.service.AuthenticationTokenService
import com.kiq83.ktor.sample.account.service.CustomPrincipal
import io.ktor.application.ApplicationCall
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.jackson
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.auth.Authentication
import io.ktor.auth.authenticate
import io.ktor.auth.authentication
import io.ktor.auth.jwt.jwt
import io.ktor.features.DefaultHeaders
import io.ktor.routing.route
import java.util.*

class SampleAccountServer(
    private val httpPort: Int,
    private val tokenService: AuthenticationTokenService,
    private val authenticationService: AuthenticationService,
    private val accountProfileDAO: AccountProfileDAO
) {

    fun start() {
        embeddedServer(Netty, httpPort) {

            install(DefaultHeaders)
            install(ContentNegotiation) {
                jackson {}
            }
            install(Authentication) {
                jwt {
                    realm = "sample ktor"
                    verifier(tokenService.jwtVerifier)
                    validate { credential ->
                        CustomPrincipal(UUID.fromString(credential.payload.subject))
                    }
                }
            }

            routing {
                route("api") {
                    auth(authenticationService)

                    authenticate {
                        account(accountProfileDAO)
                    }
                }
            }
        }.start(wait = true)
    }
}

fun ApplicationCall.getLoggedAccountId() =
    authentication.principal<CustomPrincipal>()?.accountId!!