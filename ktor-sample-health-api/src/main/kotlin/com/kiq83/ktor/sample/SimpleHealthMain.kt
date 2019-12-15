package com.kiq83.ktor.sample

import io.ktor.application.call
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {

    embeddedServer(Netty, 8383) {
        routing {
            get("/health") {
                call.respondText("I'm a healthy server")
            }
        }
    }.start(wait = true)

}