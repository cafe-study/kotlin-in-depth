package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.body
import kotlinx.html.h1

fun Application.configureRouting() {

    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        get("hello/{userName?}") {
            val userName = call.parameters["userName"] ?: "모르는 분"

            call.respondHtml {
                body {
                    h1 { +"Hello, $userName" }
                }
            }
        }
    }
}
