package com.kiq83.ktor.sample.account.route

import com.kiq83.ktor.sample.account.getLoggedAccountId
import com.kiq83.ktor.sample.account.model.AccountProfile
import com.kiq83.ktor.sample.account.persistence.AccountProfileDAO
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.put
import io.ktor.routing.route
import java.util.*

fun Route.account(accountProfileDAO: AccountProfileDAO) {

    route("account/{accountId}") {

        route("profile") {

            get {
                val requestAccountId = UUID.fromString(call.parameters["accountId"]!!)
                val loggedAccountId = call.getLoggedAccountId()
                if (requestAccountId != loggedAccountId) {
                    call.respond(HttpStatusCode.Forbidden, "")
                } else {
                    val profile = accountProfileDAO.getAccountProfile(requestAccountId)
                    if (profile != null) {
                        call.respond(HttpStatusCode.OK, profile)
                    } else {
                        call.respond(HttpStatusCode.NotFound, "Could not find profile for sent id")
                    }
                }
            }

            put {
                val requestAccountId = UUID.fromString(call.parameters["accountId"]!!)
                val loggedAccountId = call.getLoggedAccountId()
                if (requestAccountId != loggedAccountId) {
                    call.respond(HttpStatusCode.Forbidden, "")
                } else {

                    val profile = call.receive(AccountProfile::class)
                    accountProfileDAO.putAccountProfile(requestAccountId, profile)
                    call.respond(HttpStatusCode.OK)
                }
            }
        }
    }
}